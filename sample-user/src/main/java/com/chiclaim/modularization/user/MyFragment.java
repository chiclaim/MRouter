package com.chiclaim.modularization.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chiclaim.modularization.business.user.Address;
import com.chiclaim.modularization.business.user.User;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowire;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/29.
 */

public class MyFragment extends Fragment {


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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    TextView textView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text_parameters);
        printParameters();
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
