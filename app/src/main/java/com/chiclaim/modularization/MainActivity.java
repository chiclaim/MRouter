package com.chiclaim.modularization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;

@Route(path = "xxx/main")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        MRouter.getInstance()
                .build("user/order/list")
                .putString("param","this is from MainActivity")
                .navigation(this);
    }
}
