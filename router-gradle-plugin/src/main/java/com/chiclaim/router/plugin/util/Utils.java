package com.chiclaim.router.plugin.util;

import com.chiclaim.router.plugin.bean.GlobalInfo;

public class Utils {

    private Utils() {
    }

    public static String dot2slash(String str) {
        if (str == null) return null;
        return str.replaceAll("\\.", "/");
    }


    public static boolean isRouterInitClass(GlobalInfo globalInfo, String classSimpleName) {
        return classSimpleName.equals(globalInfo.getRouterConfig().getRouterInitClass());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
