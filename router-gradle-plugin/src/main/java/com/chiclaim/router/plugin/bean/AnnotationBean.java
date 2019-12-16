package com.chiclaim.router.plugin.bean;

public class AnnotationBean {

    // eg. Lcom/chiclaim/router/annotation/Restore;
    private String annotationType;

    // 注解所在的类
    // eg. com/chiclaim/router/xxx/Activity
    private String className;

    // 注解所在字段的名称
    private String fieldName;

    // 注解所在字段的类型
    private String fieldType;

    public AnnotationBean(String annotationType, String className, String fieldName, String fieldType) {
        this.annotationType = annotationType;
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getAnnotationType() {
        return annotationType;
    }

    public String getClassName() {
        return className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }
}
