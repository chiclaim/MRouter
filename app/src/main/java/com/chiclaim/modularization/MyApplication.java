package com.chiclaim.modularization;

import android.app.Application;

import com.chiclaim.modularization.router.annotation.Components;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/25.
 */
@Components({"sampleorder", "sampleuser"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Router_InitClass.init();
    }
}
