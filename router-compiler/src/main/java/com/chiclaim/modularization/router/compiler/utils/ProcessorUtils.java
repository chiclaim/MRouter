package com.chiclaim.modularization.router.compiler.utils;

import com.chiclaim.modularization.router.annotation.Constant;
import com.chiclaim.modularization.router.compiler.TypeKind;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor7;
import javax.lang.model.util.Types;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/27.
 */

public class ProcessorUtils {

    public static String filterModuleName(String moduleName) {
        return moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
    }


    public static boolean isInParcelable(Elements elements, Types types, TypeMirror typeMirror) {
        TypeMirror typeParcelable = elements.getTypeElement(Constant.PARCELABLE).asType();
        return types.isSubtype(typeMirror, typeParcelable);
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

    public static String getAssignStatementByTypeKind(TypeKind kind, boolean isActivity) {
        switch (kind) {
            case BOOLEAN:
                return isActivity ? "getIntent().getBooleanExtra($S, false)" : "getArguments().getBoolean($S, false)";
            case BOOLEAN_ARRAY:
                return isActivity ? "getIntent().getBooleanArrayExtra($S)" : "getArguments().getBooleanArray($S)";
            case BYTE:
                return isActivity ? "getIntent().getByteExtra($S, (byte)0)" : "getArguments().getByte($S, (byte)0)";
            case BYTE_ARRAY:
                return isActivity ? "getIntent().getByteArrayExtra($S)" : "getArguments().getByteArray($S)";
            case CHAR:
                return isActivity ? "getIntent().getCharExtra($S, (char) 0)" : "getArguments().getChar($S, (char) 0)";
            case CHAR_ARRAY:
                return isActivity ? "getIntent().getCharArrayExtra($S)" : "getArguments().getCharArray($S)";
            case SHORT:
                return isActivity ? "getIntent().getShortExtra($S, (short) 0)" : "getArguments().getShort($S, (short) 0)";
            case SHORT_ARRAY:
                return isActivity ? "getIntent().getShortArrayExtra($S)" : "getArguments().getShortArray($S)";
            case INT:
                return isActivity ? "getIntent().getIntExtra($S, 0)" : "getArguments().getInt($S, 0)";
            case INT_ARRAY:
                return isActivity ? "getIntent().getIntArrayExtra($S)" : "getArguments().getIntArray($S)";
            case INT_LIST:
                return isActivity ? "getIntent().getIntegerArrayListExtra($S)" : "getArguments().getIntegerArrayList($S)";
            case LONG:
                return isActivity ? "getIntent().getLongExtra($S, 0)" : "getArguments().getLong($S, 0)";
            case LONG_ARRAY:
                return isActivity ? "getIntent().getLongArrayExtra($S)" : "getArguments().getLongArray($S)";
            case FLOAT:
                return isActivity ? "getIntent().getFloatExtra($S, 0)" : "getArguments().getFloat($S, 0)";
            case FLOAT_ARRAY:
                return isActivity ? "getIntent().getFloatArrayExtra($S)" : "getArguments().getFloatArray($S)";
            case DOUBLE:
                return isActivity ? "getIntent().getDoubleExtra($S, 0)" : "getArguments().getDouble($S, 0)";
            case DOUBLE_ARRAY:
                return isActivity ? "getIntent().getDoubleArrayExtra($S)" : "getArguments().getDoubleArray($S)";
            case STRING:
                return isActivity ? "getIntent().getStringExtra($S)" : "getArguments().getString($S)";
            case STRING_ARRAY:
                return isActivity ? "getIntent().getStringArrayExtra($S)" : "getArguments().getStringArray($S)";
            case STRING_LIST:
                return isActivity ? "getIntent().getStringArrayListExtra($S)" : "getArguments().getStringArrayList($S)";
            case SERIALIZABLE:
                return isActivity ? "getIntent().getSerializableExtra($S)" : "getArguments().getSerializable($S)";
            case PARCELABLE:
                return isActivity ? "getIntent().getParcelableExtra($S)" : "getArguments().getParcelable($S)";
            case PARCELABLE_ARRAY:
                return isActivity ? "getIntent().getParcelableArrayExtra($S)" : "getArguments().getParcelableArray($S)";
            case PARCELABLE_LIST:
                return isActivity ? "getIntent().getParcelableArrayListExtra($S)" : "getArguments().getParcelableArrayList($S)";
        }
        return null;
    }

    public static TypeKind getElementType(Element element, Types types, Elements elements) {
        TypeMirror typeMirror = element.asType();
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
                //Parcelable
                TypeMirror typeParcelable = elements.getTypeElement(Constant.PARCELABLE).asType();
                if (types.isSubtype(typeMirror, typeParcelable)) {
                    return TypeKind.PARCELABLE;
                }

                //List<Parcelable>
                if (typeMirror.toString().startsWith("java.util.List")
                        || typeMirror.toString().startsWith("java.util.ArrayList")) {
                    TypeMirror genericTypeMirror = typeMirror.accept(new SimpleTypeVisitor7<TypeMirror, Void>() {
                        @Override
                        public TypeMirror visitDeclared(DeclaredType declaredType, Void param) {
                            if (declaredType.getTypeArguments() != null && declaredType.getTypeArguments().size() > 0) {
                                return (declaredType.getTypeArguments().get(0));
                            }
                            return null;
                        }
                    }, null);
                    if (genericTypeMirror != null && ProcessorUtils.isInParcelable(elements, types, genericTypeMirror)) {
                        return TypeKind.PARCELABLE_LIST;
                    }
                }

                //Parcelable[]
                if (typeMirror.getKind() == javax.lang.model.type.TypeKind.ARRAY) {
                    ArrayType arrayType = (ArrayType) typeMirror;
                    TypeMirror arrayMirrorType = arrayType.getComponentType();
                    if (ProcessorUtils.isInParcelable(elements, types, arrayMirrorType)) {
                        return TypeKind.PARCELABLE_ARRAY;
                    }
                }

                //Serializable
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
