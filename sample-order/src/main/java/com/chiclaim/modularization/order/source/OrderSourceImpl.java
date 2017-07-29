package com.chiclaim.modularization.order.source;

import com.chiclaim.modularization.business.order.IOrderSource;
import com.chiclaim.modularization.business.order.bean.Order;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/29.
 */
@Route(path = "/source/order")
public class OrderSourceImpl implements IOrderSource {
    @Override
    public Order getOrderDetail(String orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderName("Android从入门到放弃");
        order.setOrderPrice(100.9);
        return order;
    }
}
