package com.chiclaim.modularization.router.compiler;

public enum FieldTypeKind {
    BOOLEAN,
    BOOLEAN_ARRAY,

    BYTE,
    BYTE_ARRAY,

    CHAR,
    CHAR_ARRAY,

    SHORT,
    SHORT_ARRAY,

    INT,
    INT_ARRAY,
    INT_LIST,

    LONG,
    LONG_ARRAY,

    FLOAT,
    FLOAT_ARRAY,

    DOUBLE,
    DOUBLE_ARRAY,

    STRING,
    STRING_ARRAY,
    STRING_LIST,

    PARCELABLE,
    PARCELABLE_ARRAY,
    PARCELABLE_LIST,

    SERIALIZABLE,

    PROVIDER,

    FRAGMENT,

    FRAGMENT_V4,

    UNKNOWN
}