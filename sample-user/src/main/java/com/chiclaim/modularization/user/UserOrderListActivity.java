package com.chiclaim.modularization.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chiclaim.modularization.business.user.Address;
import com.chiclaim.modularization.business.user.User;
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

    @Autowire(name = "boolean")
    boolean isAdult;

    @Autowire(name = "short")
    short height;

    @Autowire(name = "float")
    float salary;

    @Autowire(name = "double")
    double salary2;

    @Autowire(name = "long")
    long liveDays;

    @Autowire(name = "extra")
    String extra;

    @Autowire(name = "user")
    User user; //serializable

    @Autowire(name = "address")
    Address address; //parcelable


//    @Autowire(name = "listString")
//    List<String> stringList;

//    @Autowire(name = "arrayString")
//    String[] stringArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);
        setContentView(R.layout.activity_user_order_list);
        Log.e("UserOrderListActivity", "String username=" + username);
        Log.e("UserOrderListActivity", "char paramChar=" + paramChar);
        Log.e("UserOrderListActivity", "byte param=" + paramByte);
        Log.e("UserOrderListActivity", "int age=" + age);

        Log.e("UserOrderListActivity", "boolean isAdult=" + isAdult);
        Log.e("UserOrderListActivity", "short height=" + height);
        Log.e("UserOrderListActivity", "float salary=" + salary);
        Log.e("UserOrderListActivity", "double salary2=" + salary2);
        Log.e("UserOrderListActivity", "long liveDays=" + liveDays);

        Log.e("UserOrderListActivity", "Serializable user=" + user);
        Log.e("UserOrderListActivity", "Parcelable address=" + address);

        Log.e("UserOrderListActivity", "Extras extra=" + extra);

    }

}
