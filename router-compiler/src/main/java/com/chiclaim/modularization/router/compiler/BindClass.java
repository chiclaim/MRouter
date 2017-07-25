package com.chiclaim.modularization.router.compiler;

import com.chiclaim.modularization.router.annotation.Route;

import javax.lang.model.element.Element;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/25.
 */

class BindClass {


    private String className;
    private String value;

    static BindClass createWhenApplyClass(Element element) {
        BindClass bindClass = new BindClass();
        bindClass.className = element.asType().toString();
        bindClass.value = element.getAnnotation(Route.class).path();
        //bindClass.bindingClassName = ClassName.get(Constant.ROUTE_INIT_CLASS_PACKAGE, Constant.ROUTE_INIT_CLASS);
        return bindClass;
    }


    String getClassName() {
        return className;
    }

    String getValue() {
        return value;
    }

}
