package com.chiclaim.modularization.router.compiler.utils;

import com.chiclaim.modularization.router.Constant;
import com.chiclaim.modularization.router.IProvider;
import com.chiclaim.modularization.router.compiler.FieldTypeKind;
import com.chiclaim.modularization.router.compiler.TargetTypeKind;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor7;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/27.
 */

public class ProcessorUtils {

    public static void printError(Messager messager, Element element, String message, Object... args) {
        printMessage(messager, element, Diagnostic.Kind.ERROR, message, args);
    }

    public static void printMessage(Messager messager, Element element, String message, Object... args) {
        printMessage(messager, element, Diagnostic.Kind.NOTE, message, args);
    }

    private static void printMessage(Messager messager, Element element, Diagnostic.Kind kind, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(kind, message, element);
    }

    public static String filterModuleName(String moduleName) {
        return moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
    }


    public static boolean isParcelable(Elements elements, Types types, TypeMirror typeMirror) {
        TypeMirror typeParcelable = elements.getTypeElement(Constant.PARCELABLE).asType();
        return types.isSubtype(typeMirror, typeParcelable);
    }

    public static boolean isInProvider(Elements elements, Types types, TypeElement enclosingElement) {
        TypeMirror typeActivity = elements.getTypeElement(IProvider.class.getCanonicalName()).asType();
        return types.isSubtype(enclosingElement.asType(), typeActivity);
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

    public static String getAssignStatementByTypeKind(FieldTypeKind kind, TargetTypeKind targetTypeKind) {
        boolean isActivity = targetTypeKind == TargetTypeKind.ACTIVITY;
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
            case FRAGMENT:
            case FRAGMENT_V4:
            case PROVIDER:
                return "newInstance()";
        }
        return null;
    }

    public static TargetTypeKind getTargetTypeKind(Elements elements, Types types, TypeElement enclosingElement) {
        if (isInActivity(elements, types, enclosingElement)) {
            return TargetTypeKind.ACTIVITY;
        }
        if (isInFragment(elements, types, enclosingElement)) {
            return TargetTypeKind.FRAGMENT;
        }

        if (isInProvider(elements, types, enclosingElement)) {
            return TargetTypeKind.PROVIDER;
        }

        return TargetTypeKind.UNKNOWN;
    }


    public static FieldTypeKind getElementType(Element element, Types types, Elements elements) {
        TypeMirror typeMirror = element.asType();
        switch (typeMirror.toString()) {
            case "byte":
            case "java.lang.Byte":
                return FieldTypeKind.BYTE;
            case "byte[]":
                return FieldTypeKind.BYTE_ARRAY;
            case "char":
            case "java.lang.Character":
                return FieldTypeKind.CHAR;
            case "char[]":
                return FieldTypeKind.CHAR_ARRAY;
            case "short":
            case "java.lang.Short":
                return FieldTypeKind.SHORT;
            case "short[]":
                return FieldTypeKind.SHORT_ARRAY;
            case "int":
            case "java.lang.Integer":
                return FieldTypeKind.INT;
            case "int[]":
                return FieldTypeKind.INT_ARRAY;
            case "java.util.List<java.lang.Integer>":
            case "java.util.ArrayList<java.lang.Integer>":
                return FieldTypeKind.INT_LIST;
            case "long":
            case "java.lang.Long":
                return FieldTypeKind.LONG;
            case "long[]":
                return FieldTypeKind.LONG_ARRAY;
            case "float":
            case "java.lang.Float":
                return FieldTypeKind.FLOAT;
            case "float[]":
                return FieldTypeKind.FLOAT_ARRAY;
            case "double":
            case "java.lang.Double":
                return FieldTypeKind.DOUBLE;
            case "double[]":
                return FieldTypeKind.DOUBLE_ARRAY;
            case "boolean":
            case "java.lang.Boolean":
                return FieldTypeKind.BOOLEAN;
            case "boolean[]":
                return FieldTypeKind.BOOLEAN_ARRAY;
            case "java.lang.String":
                return FieldTypeKind.STRING;
            case "java.lang.String[]":
                return FieldTypeKind.STRING_ARRAY;
            case "java.util.List<java.lang.String>":
            case "java.util.ArrayList<java.lang.String>":
                return FieldTypeKind.STRING_LIST;
            default:
                //Parcelable
                TypeMirror typeParcelable = elements.getTypeElement(Constant.PARCELABLE).asType();
                if (types.isSubtype(typeMirror, typeParcelable)) {
                    return FieldTypeKind.PARCELABLE;
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
                    if (genericTypeMirror != null && ProcessorUtils.isParcelable(elements, types, genericTypeMirror)) {
                        return FieldTypeKind.PARCELABLE_LIST;
                    }
                }

                //Parcelable[]
                if (typeMirror.getKind() == javax.lang.model.type.TypeKind.ARRAY) {
                    ArrayType arrayType = (ArrayType) typeMirror;
                    TypeMirror arrayMirrorType = arrayType.getComponentType();
                    if (ProcessorUtils.isParcelable(elements, types, arrayMirrorType)) {
                        return FieldTypeKind.PARCELABLE_ARRAY;
                    }
                }

                //IProvider
                TypeMirror typeProvider = elements.getTypeElement(IProvider.class.getCanonicalName()).asType();
                if (types.isSubtype(typeMirror, typeProvider)) {
                    return FieldTypeKind.PROVIDER;
                }

                //Serializable
                TypeMirror typeSerializable = elements.getTypeElement(Constant.SERIALIZABLE).asType();
                if (types.isSubtype(typeMirror, typeSerializable)) {
                    return FieldTypeKind.SERIALIZABLE;
                }

                //Fragment
                TypeMirror typeFragmentV4 = elements.getTypeElement(Constant.FRAGMENT_V4).asType();
                if (types.isSubtype(typeMirror, typeFragmentV4)) {
                    return FieldTypeKind.FRAGMENT_V4;
                }
                //Fragment_V4
                TypeMirror typeFragment = elements.getTypeElement(Constant.FRAGMENT).asType();
                if (types.isSubtype(typeMirror, typeFragment)) {
                    return FieldTypeKind.FRAGMENT;
                }

                return FieldTypeKind.UNKNOWN;
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
