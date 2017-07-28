package com.chiclaim.modularization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chiclaim.modularization.business.user.Address;
import com.chiclaim.modularization.business.user.User;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

import java.util.ArrayList;

@Route(path = "xxx/main")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = new User();
        user.setAge(90);
        user.setGender(1);
        user.setName("kitty");

        Address address = new Address();
        address.setCity("HangZhou");
        address.setProvince("ZheJiang");

        Bundle extras = new Bundle();
        extras.putString("extra", "from extras");


        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Java");
        stringList.add("C#");
        stringList.add("Kotlin");

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("American");
        stringArrayList.add("China");
        stringArrayList.add("England");

        ArrayList<Integer> intArrayList = new ArrayList<>();
        intArrayList.add(100);
        intArrayList.add(101);
        intArrayList.add(102);

        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(10011);
        intList.add(10111);
        intList.add(10211);

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(new Address("JiangXi", "ShangRao", null));
        addressList.add(new Address("ZheJiang", "NingBo", null));

        Address[] addressArray = new Address[]{
                new Address("Beijing", "Beijing", null),
                new Address("Shanghai", "Shanghai", null),
                new Address("Guangzhou", "Guangzhou", null)
        };

        MRouter.getInstance()
                .build("user/order/list")
                .putSerializable("user", user)
                .putParcelable("address", address)
                .putParcelableList("addressList", addressList)
                .putParcelableArray("addressArray", addressArray)
                .putString("param", "chiclaim")
                .putStringArray("stringArray", new String[]{"a", "b", "c"})
                .putStringList("stringArrayList", stringList)
                .putStringList("stringList", stringArrayList)
                .putByte("byte", (byte) 2)
                .putByteArray("byteArray", new byte[]{1, 2, 3, 4, 5})
                .putInt("age", 33)
                .putIntArray("intArray", new int[]{10, 11, 12, 13})
                .putIntList("intList", intList)
                .putIntList("intArrayList", intArrayList)
                .putChar("chara", 'c')
                .putCharArray("charArray", "chiclaim".toCharArray())
                .putShort("short", (short) 1000000)
                .putShortArray("shortArray", new short[]{(short) 10.9, (short) 11.9})
                .putDouble("double", 1200000)
                .putDoubleArray("doubleArray", new double[]{1232, 9999, 8789, 3.1415926})
                .putLong("long", 999999999)
                .putLongArray("longArray", new long[]{1000, 2000, 3000})
                .putFloat("float", 333)
                .putFloatArray("floatArray", new float[]{12.9f, 234.9f})
                .putBoolean("boolean", true)
                .putBooleanArray("booleanArray", new boolean[]{true, false, true})
                .putExtras(extras)
                .navigation(this);

        // TODO: 2017/7/27 传递列表数据 List<Integer> int[] array ...
    }
}
