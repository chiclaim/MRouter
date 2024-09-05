package com.chiclaim.modularization.sample;

import android.app.Application;
import android.widget.Toast;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.NavigationInterceptor;
import com.chiclaim.modularization.router.RouteOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * <p>
 * Created by kumu on 2017/7/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final List<NavigationInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new NavigationInterceptor() {
            @Override
            public boolean onIntercept(String targetRoute) {
                Toast.makeText(getApplicationContext(), "拦截", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        final RouteOption option = new RouteOption();
        option.interceptors = interceptors;
        MRouter.getInstance().init(this, option);
    }
}
