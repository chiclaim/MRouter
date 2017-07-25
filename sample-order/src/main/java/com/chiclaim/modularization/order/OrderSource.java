package com.chiclaim.modularization.order;

import android.util.Log;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

public class OrderSource implements IOrderSource{


    public void getOrderList(String orderId) {
        Log.e("OrderSource", "get order list by orderId");
    }
}
