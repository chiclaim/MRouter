package com.chiclaim.router.plugin.handler;

import com.chiclaim.router.plugin.bean.AnnotationBean;

public interface IFieldVisitorHandler {

    /**
     * 访问字段上的注解
     * @param desc eg. Lcom/chiclaim/router/annotation/Restore
     * @param visible 是否可见
     * @param annotationBean 注解信息封装的bean
     */
    void visitFieldAnnotation(String desc, boolean visible, AnnotationBean annotationBean);

    /**
     * 访问注解中的成员
     * @param name 成员名
     * @param value 成员值
     * @param annotationBean 注解信息封装的bean
     */
    void visitFieldAnnotationMember(String name, Object value,AnnotationBean annotationBean);

}
