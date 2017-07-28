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

    @Autowire(name = "charArray")
    char[] charArray;

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


    @Autowire(name = "addressList")
    List<Address> addressList; //List<Parcelable>

    @Autowire(name = "addressArray")
    Address[] addressArray; //Parcelable[]

//    @Autowire(name = "activity")
//    Activity activity; //do not support type

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);
        setContentView(R.layout.activity_user_order_list);
        printParameters();
    }


    private void printParameters() {
        Log.e("UserOrderListActivity", "String username=" + username);
        for (String str : stringArray) {
            Log.e("UserOrderListActivity", "    String Array item =" + str);
        }

        for (String str : stringList) {
            Log.e("UserOrderListActivity", "    String List item =" + str);
        }

        for (String str : stringArrayList) {
            Log.e("UserOrderListActivity", "    String ArrayList item =" + str);
        }

        Log.e("UserOrderListActivity", "char paramChar=" + paramChar);

        for (char c : charArray) {
            Log.e("UserOrderListActivity", "    char array item =" + c);
        }

        Log.e("UserOrderListActivity", "byte param=" + paramByte);

        for (byte c : byteArray) {
            Log.e("UserOrderListActivity", "    byte array item =" + c);
        }

        Log.e("UserOrderListActivity", "int age=" + age);

        for (int c : intArray) {
            Log.e("UserOrderListActivity", "    int array item =" + c);
        }

        for (int c : intArrayList) {
            Log.e("UserOrderListActivity", "    int arrayList item =" + c);
        }

        Log.e("UserOrderListActivity", "boolean isAdult=" + isAdult);

        for (boolean c : booleanArray) {
            Log.e("UserOrderListActivity", "    boolean array item =" + c);
        }

        Log.e("UserOrderListActivity", "short height=" + height);

        for (short c : shortArray) {
            Log.e("UserOrderListActivity", "    short array item =" + c);
        }

        Log.e("UserOrderListActivity", "float salary=" + salary);

        for (float c : floatArray) {
            Log.e("UserOrderListActivity", "    float array item =" + c);
        }

        Log.e("UserOrderListActivity", "double salary2=" + salary2);

        for (double c : doubleArray) {
            Log.e("UserOrderListActivity", "    double array item =" + c);
        }

        Log.e("UserOrderListActivity", "long liveDays=" + liveDays);

        for (long c : longArray) {
            Log.e("UserOrderListActivity", "    long array item =" + c);
        }

        Log.e("UserOrderListActivity", "Serializable user=" + user);

        Log.e("UserOrderListActivity", "Parcelable address=" + address);
        for (Address c : addressArray) {
            Log.e("UserOrderListActivity", "    Parcelable array item =" + c);
        }
        for (Address c : addressList) {
            Log.e("UserOrderListActivity", "    Parcelable arrayList item =" + c);
        }

        Log.e("UserOrderListActivity", "Extras extra=" + extra);
    }

}
