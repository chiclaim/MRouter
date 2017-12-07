package com.chiclaim.modularization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chiclaim.modularization.router.MRouter;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/11/15.
 */

public class ActivityManagerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_layout);
    }


    public void finishRange(View view) {
        //startActivity(new Intent(this, AActivity.class));
        MRouter.getInstance().build(RouterPaths.ACTIVITY_A).navigation(this);

    }
}
