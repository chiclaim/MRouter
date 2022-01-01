package com.chiclaim.modularization.router.compiler;

import com.squareup.javapoet.TypeName;

/**
 * 保存字段相关信息
 * Created by kumu
 */

class AutowireField {

    private final String fieldName;
    private final TypeName fieldType;
    private final String keyName;
    private final String assignStatement;
    private final FieldTypeKind typeKind;

    private AutowireField(String fieldName, TypeName fieldType, String keyName, String assignStatement, FieldTypeKind typeKind) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.keyName = keyName;
        this.assignStatement = assignStatement;
        this.typeKind = typeKind;
    }

    static AutowireField create(String fieldName, TypeName fieldType, String keyName, String assignStatement, FieldTypeKind typeKind) {
        return new AutowireField(fieldName, fieldType, keyName, assignStatement, typeKind);
    }

    public String getFieldName() {
        return fieldName;
    }

    public TypeName getFieldType() {
        return fieldType;
    }

    public String getKeyName() {
        if (keyName == null || "".equals(keyName.trim())) {
            return fieldName;
        }
        return keyName;
    }

    public String getAssignStatement() {
        return assignStatement;
    }

    public FieldTypeKind getTypeKind() {
        return typeKind;
    }

}
