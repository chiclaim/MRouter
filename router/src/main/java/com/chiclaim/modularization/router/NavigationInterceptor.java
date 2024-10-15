package com.chiclaim.modularization.router;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 跳转拦截器
 */
public interface NavigationInterceptor {

    /**
     * 拦截方法（可以在此方法中实现自己的跳转，如使用 FlutterBoost 跳转 Flutter 页面）
     *
     * @return 如果返回true，表示拦截
     */
    boolean onIntercept(@NonNull Context context, @NonNull NavigationInfo navigationInfo);

}
