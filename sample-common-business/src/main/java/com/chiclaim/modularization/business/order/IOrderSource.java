package com.chiclaim.modularization.business.order;

import com.chiclaim.modularization.business.order.bean.Order;
import com.chiclaim.modularization.router.IProvider;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/29.
 */

public interface IOrderSource extends IProvider {


    Order getOrderDetail(String orderId);


}
