package com.chiclaim.modularization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chiclaim.modularization.business.user.Address;
import com.chiclaim.modularization.business.user.User;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

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
        extras.putString("extra","from extras");

        MRouter.getInstance()
                .build("user/order/list")
                .putSerializable("user", user)
                .putParcelable("address", address)
                .putString("param", "chiclaim")
                .putByte("byte", (byte) 2)
                .putInt("age", 33)
                .putChar("chara", 'c')
                .putShort("short", (short) 1000000)
                .putDouble("double", 1200000)
                .putLong("long", 999999999)
                .putFloat("float", 333)
                .putBoolean("boolean", true)
                .putExtras(extras)
                .navigation(this);
    }
}
