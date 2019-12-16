package com.chiclaim.router.plugin.handler;

import com.chiclaim.router.plugin.bean.AnnotationBean;

public interface IFieldVisitorHandler {

    /**
     * 访问字段上的注解
     * @param desc eg. Lcom/chiclaim/router/annotation/Restore
     * @param visible
     */
    void visitFieldAnnotation(String desc, boolean visible, AnnotationBean annotationBean);

    /**
     * 访问注解中的成员
     * @param name
     * @param value
     */
    void visitFieldAnnotationMember(String name, Object value,AnnotationBean annotationBean);

}
