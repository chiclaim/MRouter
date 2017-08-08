package com.chiclaim.modularization;

import android.app.Application;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Components;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/25.
 */
@Components({"app", "sampleuser", "sampleorder"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MRouter.getInstance().init(this);
    }
}
