package com.chiclaim.router.plugin.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GlobalInfo {

    private final List<String> routerComponents = new ArrayList<>(16);

    private final List<File> routerInitTransformFiles = new ArrayList<>(16);

    private RouterConfig routerConfig;

    public boolean hasAttentionInfo() {
        return !routerInitTransformFiles.isEmpty() && !routerComponents.isEmpty();
    }


    public List<String> getRouterComponents() {
        return routerComponents;
    }

    public List<File> getRouterInitTransformFiles() {
        return routerInitTransformFiles;
    }

    public RouterConfig getRouterConfig() {
        return routerConfig;
    }

    public void setRouterConfig(RouterConfig routerConfig) {
        this.routerConfig = routerConfig;
    }
}
