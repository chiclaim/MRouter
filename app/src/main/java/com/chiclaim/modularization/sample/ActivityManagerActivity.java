package com.chiclaim.modularization.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chiclaim.modularization.R;
import com.chiclaim.modularization.business.RouterPaths;
import com.chiclaim.modularization.router.MRouter;

/**
 * Description：
 * <p>
 * Created by kumu on 2017/11/15.
 */

public class ActivityManagerActivity extends BaseActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_layout);
    }


    public void finishRange(View view) {
        //startActivity(new Intent(this, AActivity.class));
        MRouter.getInstance().build(RouterPaths.ACTIVITY_A).navigate(this);

    }


    public void getCurrentActivity(View view) {
        Toast.makeText(this, "CurrentActivity:" + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}
