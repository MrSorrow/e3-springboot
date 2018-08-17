package guo.ping.e3mall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import guo.ping.e3mall.cart.service.CartService;
import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.manager.mapper.TbItemMapper;
import guo.ping.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Override
    public E3Result addCart(Long userId, Long itemId, int num) {
        Boolean hasItem = redisTemplate.opsForHash().hasKey(REDIS_CART_PRE + ":" + userId, itemId+"");
        if (hasItem) {
            // 商品存在，数量相加
            TbItem tbItem = (TbItem) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId+"");
            tbItem.setNum(tbItem.getNum() + num);
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", tbItem);
            return E3Result.ok();
        } else {
            // 商品不存在，查询数据库添加商品
            TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
            item.setNum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", item);
            return E3Result.ok();
        }
    }

    @Override
    public E3Result mergeCart(Long userId, List<TbItem> cookieItemList) {
        for (TbItem tbItem : cookieItemList) {
            addCart(userId, tbItem.getId(), tbItem.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(Long userId) {
        List<Object> results = redisTemplate.opsForHash().values(REDIS_CART_PRE + ":" + userId);
        List<TbItem> tbItems = new ArrayList<>();
        for (Object result : results) {
            tbItems.add((TbItem) result);
        }
        return tbItems;
    }

    @Override
    public E3Result updateCartNum(Long userId, Long itemId, int num) {
        TbItem tbItem = (TbItem) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId+"");
        tbItem.setNum(tbItem.getNum() + num);
        redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", tbItem);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(Long userId, Long itemId) {
        redisTemplate.opsForHash().delete(REDIS_CART_PRE + ":" + userId, itemId+"");
        return E3Result.ok();
    }

    /**
     * 清除购物车
     * @param userId
     * @return
     */
    @Override
    public E3Result clearCartList(Long userId) {
        redisTemplate.delete(REDIS_CART_PRE + ":" + userId);
        return E3Result.ok();
    }
}
