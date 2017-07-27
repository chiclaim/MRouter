package com.chiclaim.modularization.router.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
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
    private final boolean needCast;

    private AutowireField(String name, TypeName type, String value, String assignStatement, boolean needCast) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.assignStatement = assignStatement;
        this.needCast = needCast;
    }

    static AutowireField create(String name, TypeName type, String value, String assignStatement, boolean needCast) {
        return new AutowireField(name, type, value, assignStatement, needCast);
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

    public boolean isNeedCast() {
        return needCast;
    }

    ClassName getRawType() {
        if (type instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName) type).rawType;
        }
        return (ClassName) type;
    }
}
