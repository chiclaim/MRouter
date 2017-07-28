package com.chiclaim.modularization.router.compiler;

import com.squareup.javapoet.TypeName;

/**
 * 保存字段相关信息
 * Created by kumu
 */

class AutowireField {

    private final String name;
    private final TypeName type;
    private final String value;
    private final String assignStatement;
    private final TypeKind typeKind;

    private AutowireField(String name, TypeName type, String value, String assignStatement, TypeKind typeKind) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.assignStatement = assignStatement;
        this.typeKind = typeKind;
    }

    static AutowireField create(String name, TypeName type, String value, String assignStatement, TypeKind typeKind) {
        return new AutowireField(name, type, value, assignStatement, typeKind);
    }

    public String getName() {
        return name;
    }

    public TypeName getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getAssignStatement() {
        return assignStatement;
    }

    public TypeKind getTypeKind() {
        return typeKind;
    }

}
