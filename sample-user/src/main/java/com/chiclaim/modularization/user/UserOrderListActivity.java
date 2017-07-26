package com.chiclaim.modularization.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.chiclaim.modularization.router.annotation.Route;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

@Route(path = "user/order/list")
public class UserOrderListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);
        String param = getIntent().getStringExtra("param");
        if (!TextUtils.isEmpty(param)) {
            Toast.makeText(this, param + "", Toast.LENGTH_SHORT).show();
        }

    }
}
