package com.chiclaim.modularization.router.compiler.utils;

import com.chiclaim.modularization.router.annotation.Constant;
import com.chiclaim.modularization.router.compiler.TypeKind;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.chiclaim.modularization.router.compiler.TypeKind.BYTE_ARRAY;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/27.
 */

public class ProcessorUtils {

    public static String filterModuleName(String moduleName) {
        return moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
    }


    public static boolean isInActivity(Elements elements, Types types, TypeElement enclosingElement) {
        TypeMirror typeActivity = elements.getTypeElement(Constant.ACTIVITY).asType();
        return types.isSubtype(enclosingElement.asType(), typeActivity);
    }

    public static boolean isInFragment(Elements elements, Types types, TypeElement enclosingElement) {
        TypeMirror typeFragmentV4 = elements.getTypeElement(Constant.FRAGMENT_V4).asType();
        TypeMirror typeFragment = elements.getTypeElement(Constant.FRAGMENT).asType();
        return types.isSubtype(enclosingElement.asType(), typeFragmentV4) ||
                types.isSubtype(enclosingElement.asType(), typeFragment);
    }

    public static String getStatementByElementType(TypeKind kind) {
        switch (kind) {
            case BOOLEAN:
                return "getBooleanExtra($S, false)";
            case BOOLEAN_ARRAY:
                return "getBooleanArrayExtra($S)";
            case BYTE:
                return "getByteExtra($S, (byte)0)";
            case BYTE_ARRAY:
                return "getByteArrayExtra($S)";
            case CHAR:
                return "getCharExtra($S, (char) 0)";
            case CHAR_ARRAY:
                return "getCharArrayExtra($S)";
            case SHORT:
                return "getShortExtra($S, (short) 0)";
            case SHORT_ARRAY:
                return "getShortArrayExtra($S)";
            case INT:
                return "getIntExtra($S, 0)";
            case INT_ARRAY:
                return "getIntArrayExtra($S)";
            case INT_LIST:
                return "getIntegerArrayListExtra($S)";
            case LONG:
                return "getLongExtra($S, 0)";
            case LONG_ARRAY:
                return "getLongArrayExtra($S)";
            case FLOAT:
                return "getFloatExtra($S, 0)";
            case FLOAT_ARRAY:
                return "getFloatArrayExtra($S)";
            case DOUBLE:
                return "getDoubleExtra($S, 0)";
            case DOUBLE_ARRAY:
                return "getDoubleArrayExtra($S)";
            case STRING:
                return "getStringExtra($S)";
            case STRING_ARRAY:
                return "getStringArrayExtra($S)";
            case STRING_LIST:
                return "getStringArrayListExtra($S)";
            case SERIALIZABLE:
                return "getSerializableExtra($S)";
            case PARCELABLE:
                return "getParcelableExtra($S)";
        }
        return null;
    }

    public static TypeKind getElementType(Element element, Types types, Elements elements) {
        TypeMirror typeMirror = element.asType();
        System.out.println(typeMirror.toString());
        switch (typeMirror.toString()) {
            case "byte":
            case "java.lang.Byte":
                return TypeKind.BYTE;
            case "byte[]":
                return TypeKind.BYTE_ARRAY;
            case "char":
            case "java.lang.Character":
                return TypeKind.CHAR;
            case "char[]":
                return TypeKind.CHAR_ARRAY;
            case "short":
            case "java.lang.Short":
                return TypeKind.SHORT;
            case "short[]":
                return TypeKind.SHORT_ARRAY;
            case "int":
            case "java.lang.Integer":
                return TypeKind.INT;
            case "int[]":
                return TypeKind.INT_ARRAY;
            case "java.util.List<java.lang.Integer>":
            case "java.util.ArrayList<java.lang.Integer>":
                return TypeKind.INT_LIST;
            case "long":
            case "java.lang.Long":
                return TypeKind.LONG;
            case "long[]":
                return TypeKind.LONG_ARRAY;
            case "float":
            case "java.lang.Float":
                return TypeKind.FLOAT;
            case "float[]":
                return TypeKind.FLOAT_ARRAY;
            case "double":
            case "java.lang.Double":
                return TypeKind.DOUBLE;
            case "double[]":
                return TypeKind.DOUBLE_ARRAY;
            case "boolean":
            case "java.lang.Boolean":
                return TypeKind.BOOLEAN;
            case "boolean[]":
                return TypeKind.BOOLEAN_ARRAY;
            case "java.lang.String":
                return TypeKind.STRING;
            case "java.lang.String[]":
                return TypeKind.STRING_ARRAY;
            case "java.util.List<java.lang.String>":
            case "java.util.ArrayList<java.lang.String>":
                return TypeKind.STRING_LIST;
            default:
                TypeMirror typeParcelable = elements.getTypeElement(Constant.PARCELABLE).asType();
                if (types.isSubtype(typeMirror, typeParcelable)) {
                    return TypeKind.PARCELABLE;
                }
                TypeMirror typeSerializable = elements.getTypeElement(Constant.SERIALIZABLE).asType();
                if (types.isSubtype(typeMirror, typeSerializable)) {
                    return TypeKind.SERIALIZABLE;
                }
                return TypeKind.UNKNOWN;
        }
    }

    public static TypeName getTypeName(TypeElement enclosingElement) {
        TypeName targetType = TypeName.get(enclosingElement.asType());
        if (targetType instanceof ParameterizedTypeName) {
            targetType = ((ParameterizedTypeName) targetType).rawType;
        }
        return targetType;
    }
}
