package com.chiclaim.modularization.router;

import androidx.annotation.Keep;

import java.util.HashMap;

/**
 * Description 路由管理器
 * <p>
 * Created by kumu on 2017/7/24.
 */
@Keep
public class RouteManager {


    private final HashMap<String, Class<?>> routes;


    private RouteManager() {
        routes = new HashMap<>();
    }

    private static RouteManager instance;

    public static synchronized RouteManager getInstance() {
        if (instance == null) {
            instance = new RouteManager();
        }
        return instance;
    }


    void addRoute(String path, Class<?> clazz) {
        if (routes.containsKey(path))
            throw new IllegalArgumentException("Duplicate path found: '" + path + "'");
        routes.put(path, clazz);
    }


    public Class<?> getRoute(String path) {
        return routes.get(path);
    }

}
