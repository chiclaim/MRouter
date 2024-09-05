package com.chiclaim.modularization.router;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;

/**
 * Description
 * <p>
 * Created by kumu on 2017/7/24.
 */

public class MRouter {

    // ApplicationContext
    @SuppressLint("StaticFieldLeak")
    private static volatile MRouter instance;

    private Context context;

    private boolean isInitialized;

    protected RouteOption option;

    private MRouter() {
    }

    public static MRouter getInstance() {
        if (instance == null) {
            synchronized (MRouter.class) {
                if (instance == null) {
                    instance = new MRouter();
                }
            }
        }
        return instance;
    }

    public void init(Context context, RouteOption option) {
        this.context = context.getApplicationContext();
        if (isInitialized) return;
        isInitialized = true;
        this.option = option;
        registerActivityLifecycleCallbacks(this.context);
        RouterInit.init();
    }

    public Router build(String path) {
        return new Router(path);
    }

    public Router build(Uri uri) {
        return new Router(uri);
    }


    public void inject(Object target) {
        String qualifiedName = target.getClass().getName();
        String generateClass = qualifiedName + Constant.ROUTE_INIT_MODULE_CLASS_AUTOWIRE_SUFFIX;
        try {
            Class.forName(generateClass).getConstructor(target.getClass()).newInstance(target);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return context;
    }

    private void registerActivityLifecycleCallbacks(Context context) {
        ((Application) context).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                RouteStackManager.get().add(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                RouteStackManager.get().remove(activity);
            }
        });

    }

}
