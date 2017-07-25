package com.chiclaim.modularization;

import android.app.Application;

import com.chiclaim.modularization.router.RouterInit;
import com.chiclaim.modularization.router.annotation.Components;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/25.
 */
@Components({"app", "sampleuser"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
         RouterInit.init();
    }
}
