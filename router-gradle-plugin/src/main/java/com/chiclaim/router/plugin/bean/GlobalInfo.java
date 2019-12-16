package com.chiclaim.router.plugin.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GlobalInfo {

    private List<String> routerComponents = new ArrayList<>(16);

    private File routerInitTransformFile;

    private RouterConfig routerConfig;

    public boolean hasAttentionInfo() {
        return routerInitTransformFile != null && !routerComponents.isEmpty();
    }


    public List<String> getRouterComponents() {
        return routerComponents;
    }

    public File getRouterInitTransformFile() {
        return routerInitTransformFile;
    }

    public void setRouterInitTransformFile(File routerInitTransformFile) {
        this.routerInitTransformFile = routerInitTransformFile;
    }

    public RouterConfig getRouterConfig() {
        return routerConfig;
    }

    public void setRouterConfig(RouterConfig routerConfig) {
        this.routerConfig = routerConfig;
    }
}
