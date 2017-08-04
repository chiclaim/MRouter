package com.chiclaim.modularization.router;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/24.
 */

public interface Constant {

    String KEY_MODULE_NAME = "moduleName";
    String LIB_NAME = "Modularization";
    String ROUTE_INIT_CLASS_PACKAGE = "com.chiclaim.modularization.router";
    String ROUTE_INIT_CLASS = "RouterInit";
    String ROUTE_INIT_MODULE_CLASS_PREFIX = "Router_InitClass_";
    String ROUTE_INIT_CLASS_METHOD = "init";

    String ROUTE_INIT_MODULE_CLASS_AUTOWIRE_SUFFIX = "_Autowire";



    String ACTIVITY = "android.app.Activity";
    String FRAGMENT = "android.app.Fragment";
    String FRAGMENT_V4 = "android.support.v4.app.Fragment";
    String PARCELABLE = "android.os.Parcelable";
    String SERIALIZABLE = "java.io.Serializable";


}
