package com.chiclaim.modularization.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chiclaim.modularization.business.user.Address;
import com.chiclaim.modularization.business.user.User;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowire;
import com.chiclaim.modularization.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

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

    @Autowire(name = "byteArray")
    byte[] byteArray;

    @Autowire(name = "age")
    int age;

    @Autowire(name = "intArray")
    int[] intArray;

    @Autowire(name = "intList")
    List<Integer> intList;

    @Autowire(name = "intArrayList")
    ArrayList<Integer> intArrayList;

    @Autowire(name = "chara")
    char paramChar;

    @Autowire(name = "chars")
    char[] paramChars;

    @Autowire(name = "boolean")
    boolean isAdult;

    @Autowire(name = "booleanArray")
    boolean[] booleanArray;

    @Autowire(name = "short")
    short height;

    @Autowire(name = "shortArray")
    short[] shortArray;

    @Autowire(name = "float")
    float salary;

    @Autowire(name = "floatArray")
    float[] floatArray;

    @Autowire(name = "double")
    double salary2;

    @Autowire(name = "doubleArray")
    double[] doubleArray;

    @Autowire(name = "long")
    long liveDays;

    @Autowire(name = "longArray")
    long[] longArray;

    @Autowire(name = "extra")
    String extra;

    @Autowire(name = "stringArray")
    String[] stringArray;

    @Autowire(name = "stringList")
    List<String> stringList;

    @Autowire(name = "stringArrayList")
    ArrayList<String> stringArrayList;

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
