package com.chiclaim.modularization.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Toast;

import com.chiclaim.modularization.R;
import com.chiclaim.modularization.business.RouterPaths;
import com.chiclaim.modularization.business.order.IOrderSource;
import com.chiclaim.modularization.business.user.bean.Address;
import com.chiclaim.modularization.business.user.bean.User;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.SimpleNavigationCallback;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.user.UserInfoFragment;

import java.util.ArrayList;

@Route(path = RouterPaths.ACTIVITY_MAIN)
public class MainActivity extends BaseActivity {

    UserInfoFragment userInfoFragment;

    @Autowired(name = "/source/order")
    IOrderSource orderSource;

    @Autowired(name = "/user/login")
    Fragment loginFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MRouter.getInstance().inject(this);
    }

    public void addFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (userInfoFragment == null) {
            userInfoFragment = new UserInfoFragment();
        }
        if (userInfoFragment.isAdded()) {
            transaction.show(userInfoFragment);
        } else {
            Bundle bundle = assembleBundle();
            userInfoFragment.setArguments(bundle);
            transaction.addToBackStack("");
            transaction.add(R.id.container, userInfoFragment);
        }
        transaction.commit();
    }

    public Bundle assembleBundle() {
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putParcelable("address", address);
        bundle.putParcelableArrayList("addressList", addressList);
        bundle.putParcelableArray("addressArray", addressArray);
        bundle.putString("username", "chiclaim");
        bundle.putStringArray("stringArray", new String[]{"a", "b", "c"});
        bundle.putStringArrayList("stringArrayList", stringList);
        bundle.putStringArrayList("stringList", stringArrayList);
        bundle.putByte("byte", (byte) 2);
        bundle.putByteArray("byteArray", new byte[]{1, 2, 3, 4, 5});
        bundle.putInt("age", 33);
        bundle.putIntArray("intArray", new int[]{10, 11, 12, 13});
        bundle.putIntegerArrayList("intList", intList);
        bundle.putIntegerArrayList("intArrayList", intArrayList);
        bundle.putChar("chara", 'c');
        bundle.putCharArray("charArray", "chiclaim".toCharArray());
        bundle.putShort("short", (short) 1000000);
        bundle.putShortArray("shortArray", new short[]{(short) 10.9, (short) 11.9});
        bundle.putDouble("double", 1200000);
        bundle.putDoubleArray("doubleArray", new double[]{1232, 9999, 8789, 3.1415926});
        bundle.putLong("long", 999999999);
        bundle.putLongArray("longArray", new long[]{1000, 2000, 3000});
        bundle.putFloat("float", 333);
        bundle.putFloatArray("floatArray", new float[]{12.9f, 234.9f});
        bundle.putBoolean("boolean", true);
        bundle.putBooleanArray("booleanArray", new boolean[]{true, false, true});

        return bundle;
    }


    public void goOrderList(View view) {
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
                .build(RouterPaths.ORDER_LIST_ACTIVITY)
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
                .navigateCallback(new SimpleNavigationCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "跳转成功 from callback", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMiss() {
                        Toast.makeText(MainActivity.this, "找不到路由", Toast.LENGTH_SHORT).show();
                    }
                })
                .navigate(this);
    }


    public void activityManage(View view) {
        startActivity(new Intent(this, ActivityManagerActivity.class));
    }


    /**
     * User模块获取Order模块数据
     */
    public void getOrderId(View view) {
        if (orderSource != null) {
            Toast.makeText(this, orderSource.getOrderDetail("010101").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getFragmentInOtherModule(View view) {
//        Fragment loginFragment = MRouter.getInstance().build("/user/login").find();
//        Fragment loginFragment = getFragmentPutArguments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("");
//        transaction.add(R.id.container, loginFragment);
        transaction.add(R.id.container, loginFragment);

        transaction.commit();
    }

    public void startWithAnimation(View view) {
        MRouter.getInstance().build("/app/NoAutowiredActivity")
                .overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top)
                .navigate(this);
    }

    private Fragment getFragmentPutArguments() {
        Bundle bundle = assembleBundle();
        return MRouter.getInstance()
                .build("/user/info")
                .putExtras(bundle)
                .find();
    }


    public void startWithUri(View view){
        // use for scheme
        // path="/manager/target"
        MRouter.getInstance().build(Uri.parse("https://www.baidu.com/manager/target"))
                .navigate(this);
    }


}
