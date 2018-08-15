package guo.ping.e3mall.sso.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.manager.mapper.TbUserMapper;
import guo.ping.e3mall.pojo.TbUser;
import guo.ping.e3mall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    /**
     * 根据数据类型校验数据
     * @param param
     * @param type
     * @return
     */
    @Override
    public E3Result checkData(String param, Integer type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("param", param);
        paramMap.put("type", type);
        List<TbUser> tbUsers = userMapper.selectByKey(paramMap);
        if (tbUsers == null || tbUsers.isEmpty()) {
            return E3Result.ok(true);
        } else {
            return E3Result.ok(false);
        }
    }

    @Override
    public E3Result register(TbUser tbUser) {
        // 校验数据
        if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())) {
            return E3Result.build(400, "用户名或密码不能为空");
        }
        E3Result result = checkData(tbUser.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return E3Result.build(400, "用户名重复");
        }
        if (tbUser.getPhone() != null) {
            result = checkData(tbUser.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return E3Result.build(400, "手机号重复");
            }
        }
        if (tbUser.getEmail() != null) {
            result = checkData(tbUser.getEmail(), 3);
            if (!(boolean) result.getData()) {
                return E3Result.build(400, "邮箱重复");
            }
        }
        // 插入数据
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        userMapper.insert(tbUser);

        return E3Result.ok();

    }

    @Override
    public E3Result login(String username, String password) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        List<TbUser> users = userMapper.selectUserByNameOrPwd(paramMap);

        if (users == null || users.isEmpty()) {
            return E3Result.build(400, "该用户不存在");
        }

        TbUser tbUser = users.get(0);
        // 校验密码
        if (!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return E3Result.build(400, "密码错误");
        }

        // 登录成功
        String token = UUID.randomUUID().toString();
        tbUser.setPassword(null);
        redisTemplate.opsForValue().set(REDIS_SESSION_KEY + ":" + token, tbUser);
        redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);

        return E3Result.ok(token);

    }

    @Override
    public E3Result getUserByToken(String token) {
        TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(REDIS_SESSION_KEY + ":" + token);
        if (tbUser == null) {
            return E3Result.build(201, "用户登录信息已经过期！");
        }
        redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);
        return E3Result.ok(tbUser);
    }
}
