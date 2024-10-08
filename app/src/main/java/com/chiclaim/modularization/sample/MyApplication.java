package com.chiclaim.modularization.sample;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.NavigationInfo;
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
            public boolean onIntercept(@NonNull Context context, @NonNull NavigationInfo navigationInfo) {
                Toast.makeText(getApplicationContext(), "拦截"+navigationInfo.routePath, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        final RouteOption option = new RouteOption();
        option.interceptors = interceptors;
        MRouter.getInstance().init(this, option);
    }
}
