package com.chiclaim.modularization.router;

/**
 * 跳转拦截器
 */
public interface NavigationInterceptor {

    /**
     * 拦截方法（可以在此方法中实现自己的跳转，如使用 FlutterBoost 跳转 Flutter 页面）
     *
     * @param targetRoute  跳转的路由
     * @return 如果返回true，表示拦截
     */
    boolean onIntercept(String targetRoute);

}
