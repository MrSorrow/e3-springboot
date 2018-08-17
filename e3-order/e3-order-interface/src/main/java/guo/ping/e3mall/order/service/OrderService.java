package guo.ping.e3mall.order.service;

import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.order.pojo.OrderInfo;

public interface OrderService {
    E3Result createOrder(OrderInfo orderInfo);
}
