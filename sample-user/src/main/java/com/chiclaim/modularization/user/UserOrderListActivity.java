package com.chiclaim.modularization.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowire;
import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

@Route(path = "user/order/list")
public class UserOrderListActivity extends BaseActivity {


    @Autowire(name = "param")
    String username;

    @Autowire(name = "byte")
    byte paramByte;

    @Autowire(name = "age")
    int age;

    @Autowire(name = "chara")
    char paramChar;

    @Autowire(name = "user")
    User user; //serializable

    @Autowire(name = "address")
    Address address;

//    @Autowire(name = "listString")
//    List<String> stringList;

//    @Autowire(name = "arrayString")
//    String[] stringArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);
        setContentView(R.layout.activity_user_order_list);
        Log.e("UserOrderListActivity", "username=" + username);
        Log.e("UserOrderListActivity", "paramByte=" + paramByte);
        Log.e("UserOrderListActivity", "age=" + age);
        Log.e("UserOrderListActivity", "user=" + user);
        Log.e("UserOrderListActivity", "address=" + address);
    }

}
