package com.chiclaim.modularization;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chiclaim.modularization.utils.RouterActivityManager;

/**
 * Description：
 *
 * Created by kumu on 2017/11/23.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterActivityManager.get().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RouterActivityManager.get().finishActivity(this);

    }
}
