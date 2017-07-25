package com.chiclaim.modularization.router;

import java.util.HashMap;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

public class RouteManager {


    private HashMap<String, Class> routes = new HashMap<>();


    private RouteManager() {
    }

    private static RouteManager instance;

    public static synchronized RouteManager getInstance() {
        if (instance == null) {
            instance = new RouteManager();
        }
        return instance;
    }


    public void addRoute(String path, Class clazz) {
        routes.put(path, clazz);
    }


    public Class getRoute(String path) {
        return routes.get(path);
    }

}
