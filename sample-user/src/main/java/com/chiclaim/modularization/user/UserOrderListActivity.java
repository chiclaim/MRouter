package com.chiclaim.modularization.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chiclaim.modularization.business.order.IOrderSource;
import com.chiclaim.modularization.business.user.bean.Address;
import com.chiclaim.modularization.business.user.bean.User;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
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


    @Autowired(name = "param")
    String username;

    @Autowired(name = "byte")
    byte paramByte;

    @Autowired(name = "byteArray")
    byte[] byteArray;

    @Autowired(name = "age")
    int age;

    @Autowired(name = "intArray")
    int[] intArray;

    @Autowired(name = "intList")
    List<Integer> intList;

    @Autowired(name = "intArrayList")
    ArrayList<Integer> intArrayList;

    @Autowired(name = "chara")
    char paramChar;

    @Autowired(name = "charArray")
    char[] charArray;

    @Autowired(name = "boolean")
    boolean isAdult;

    @Autowired(name = "booleanArray")
    boolean[] booleanArray;

    @Autowired(name = "short")
    short height;

    @Autowired(name = "shortArray")
    short[] shortArray;

    @Autowired(name = "float")
    float salary;

    @Autowired(name = "floatArray")
    float[] floatArray;

    @Autowired(name = "double")
    double salary2;

    @Autowired(name = "doubleArray")
    double[] doubleArray;

    @Autowired(name = "long")
    long liveDays;

    @Autowired(name = "longArray")
    long[] longArray;

    @Autowired(name = "extra")
    String extra;

    @Autowired(name = "stringArray")
    String[] stringArray;

    @Autowired(name = "stringList")
    List<String> stringList;

    @Autowired(name = "stringArrayList")
    ArrayList<String> stringArrayList;

    @Autowired(name = "user")
    User user; //serializable

    @Autowired(name = "address")
    Address address; //parcelable


    @Autowired(name = "addressList")
    List<Address> addressList; //List<Parcelable>

    @Autowired(name = "addressArray")
    Address[] addressArray; //Parcelable[]

//    @Autowire(name = "activity")
//    Activity activity; //do not support type

    private TextView textView;

    @Autowired(name = "/source/order")
    IOrderSource orderSource;


    @Autowired(name = "/source/order")
    IOrderSource orderSource2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);
        setContentView(R.layout.activity_user_order_list);
        textView = (TextView) findViewById(R.id.text_parameters);
        printParameters();

    }


    /**
     * User模块获取Order模块数据
     *
     * @param view
     */
    public void getOrderId(View view) {
        if (orderSource != null) {
            Toast.makeText(this, orderSource.getOrderDetail("010101").toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void printParameters() {
        Log.e("UserOrderListActivity", "String username=" + username);
        textView.append("String username=" + username);
        for (String str : stringArray) {
            Log.e("UserOrderListActivity", "    String Array item =" + str);
            textView.append("\n    String Array item =" + str);
        }

        for (String str : stringList) {
            Log.e("UserOrderListActivity", "    String List item =" + str);
            textView.append("\n    String List item =" + str);
        }

        for (String str : stringArrayList) {
            Log.e("UserOrderListActivity", "    String ArrayList item =" + str);
            textView.append("\n    String ArrayList item =" + str);
        }

        Log.e("UserOrderListActivity", "char paramChar=" + paramChar);
        textView.append("\nchar paramChar=" + paramChar);
        for (char c : charArray) {
            Log.e("UserOrderListActivity", "    char array item =" + c);
            textView.append("\n    char array item =" + c);
        }

        Log.e("UserOrderListActivity", "byte param=" + paramByte);
        textView.append("\nbyte param=" + paramByte);
        for (byte c : byteArray) {
            Log.e("UserOrderListActivity", "    byte array item =" + c);
            textView.append("\n    byte array item =" + c);
        }

        Log.e("UserOrderListActivity", "int age=" + age);
        textView.append("\nint age=" + age);
        for (int c : intArray) {
            Log.e("UserOrderListActivity", "    int array item =" + c);
            textView.append("\n    int array item =" + c);
        }

        for (int c : intArrayList) {
            Log.e("UserOrderListActivity", "    int arrayList item =" + c);
            textView.append("\n    int arrayList item =" + c);
        }

        Log.e("UserOrderListActivity", "boolean isAdult=" + isAdult);
        textView.append("\nboolean isAdult=" + isAdult);
        for (boolean c : booleanArray) {
            Log.e("UserOrderListActivity", "    boolean array item =" + c);
            textView.append("\n    boolean array item =" + c);
        }

        Log.e("UserOrderListActivity", "short height=" + height);
        textView.append("\nshort height=" + height);
        for (short c : shortArray) {
            Log.e("UserOrderListActivity", "    short array item =" + c);
            textView.append("\n    short array item =" + c);
        }

        Log.e("UserOrderListActivity", "float salary=" + salary);
        textView.append("\nfloat salary=" + salary);
        for (float c : floatArray) {
            Log.e("UserOrderListActivity", "    float array item =" + c);
            textView.append("\n    float array item =" + c);
        }

        Log.e("UserOrderListActivity", "double salary2=" + salary2);
        textView.append("\ndouble salary2=" + salary2);
        for (double c : doubleArray) {
            Log.e("UserOrderListActivity", "    double array item =" + c);
            textView.append("\n    double array item =" + c);
        }

        Log.e("UserOrderListActivity", "long liveDays=" + liveDays);
        textView.append("\nlong liveDays=" + liveDays);
        for (long c : longArray) {
            Log.e("UserOrderListActivity", "    long array item =" + c);
            textView.append("\n    long array item =" + c);
        }

        Log.e("UserOrderListActivity", "Serializable user=" + user);
        textView.append("\nSerializable user=" + user);
        Log.e("UserOrderListActivity", "Parcelable address=" + address);
        for (Address c : addressArray) {
            Log.e("UserOrderListActivity", "    Parcelable array item =" + c);
            textView.append("\n    Parcelable array item =" + c);
        }
        for (Address c : addressList) {
            Log.e("UserOrderListActivity", "    Parcelable arrayList item =" + c);
            textView.append("\n    Parcelable arrayList item =" + c);
        }

        Log.e("UserOrderListActivity", "Extras extra=" + extra);
    }

}
