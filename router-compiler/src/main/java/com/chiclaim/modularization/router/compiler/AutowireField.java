package com.chiclaim.modularization.router.compiler;

import com.squareup.javapoet.TypeName;

/**
 * 保存字段相关信息
 * Created by kumu
 */

class AutowireField {

    private final String fieldName;
    private final TypeName fieldType;
    private final String annotationValue;
    private final String assignStatement;
    private final TypeKind typeKind;

    private AutowireField(String name, TypeName type, String value, String assignStatement, TypeKind typeKind) {
        this.fieldName = name;
        this.fieldType = type;
        this.annotationValue = value;
        this.assignStatement = assignStatement;
        this.typeKind = typeKind;
    }

    static AutowireField create(String name, TypeName type, String value, String assignStatement, TypeKind typeKind) {
        return new AutowireField(name, type, value, assignStatement, typeKind);
    }

    public String getFieldName() {
        return fieldName;
    }

    public TypeName getFieldType() {
        return fieldType;
    }

    public String getAnnotationValue() {
        return annotationValue;
    }

    public String getAssignStatement() {
        return assignStatement;
    }

    public TypeKind getTypeKind() {
        return typeKind;
    }

}
