package guo.ping.e3mall.cart.service;

import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.pojo.TbItem;

import java.util.List;

public interface CartService {
    E3Result addCart(Long userId, Long itemId, int num);
    E3Result mergeCart(Long userId, List<TbItem> cookieItemList);
    List<TbItem> getCartList(Long userId);
    E3Result updateCartNum(Long userId, Long itemId, int num);
    E3Result deleteCartItem(Long userId, Long itemId);
    E3Result clearCartList(Long userId);
}
