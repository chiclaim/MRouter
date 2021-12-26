package com.chiclaim.modularization.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chiclaim.modularization.router.RouteStackManager;

/**
 * Description：
 *
 * Created by kumu on 2017/11/23.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //RouteStackManager.get().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //RouteStackManager.get().finishActivity(this);

    }
}
