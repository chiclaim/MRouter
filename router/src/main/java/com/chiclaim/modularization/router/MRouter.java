package com.chiclaim.modularization.router;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

public class MRouter {

    private static MRouter instance;


    public static synchronized MRouter getInstance() {
        if (instance == null) {
            instance = new MRouter();
        }
        return instance;
    }

    public void init(Application application) {

    }

    public Router build(String path) {
        return new Router(path);
    }


    public void inject(Object target) {
        String qualifiedName = target.getClass().getName();
        String generateClass = qualifiedName + Constant.ROUTE_INIT_MODULE_CLASS_AUTOWIRE_SUFFIX;
        try {
            Class.forName(generateClass).getConstructor(target.getClass()).newInstance(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
