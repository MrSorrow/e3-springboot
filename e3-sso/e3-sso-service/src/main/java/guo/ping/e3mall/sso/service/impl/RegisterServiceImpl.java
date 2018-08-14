package guo.ping.e3mall.sso.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.manager.mapper.TbUserMapper;
import guo.ping.e3mall.pojo.TbUser;
import guo.ping.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

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
}
