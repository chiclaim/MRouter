package com.chiclaim.modularization.router;

import android.annotation.SuppressLint;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * Description
 *
 * Created by kumu on 2017/7/24.
 */

public class MRouter {

    // ApplicationContext
    @SuppressLint("StaticFieldLeak")
    private static MRouter instance;

    private Context context;

    private boolean isInitialized;

    private MRouter() {
    }

    public static synchronized MRouter getInstance() {
        if (instance == null) {
            instance = new MRouter();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        if (isInitialized) {
            return;
        }
        RouterInit.init();
    }

    public Router build(String path) {
        return new Router(path);
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

}
