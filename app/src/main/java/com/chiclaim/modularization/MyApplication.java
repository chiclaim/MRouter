package com.chiclaim.modularization;

import android.app.Application;
import com.chiclaim.modularization.router.MRouter;

/**
 * Description：
 *
 * Created by kumu on 2017/7/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MRouter.getInstance().init(this);
    }
}
