package com.chiclaim.modularization.router.compiler;

import com.chiclaim.modularization.router.annotation.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import static com.chiclaim.modularization.router.annotation.Constant.ROUTE_INIT_CLASS_PACKAGE;
import static com.chiclaim.modularization.router.annotation.Constant.ROUTE_INIT_MODULE_CLASS_PREFIX;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/25.
 */

public class RouteJavaFileUtil {

    private final static ClassName ROUTE_MANAGER = ClassName.get("com.chiclaim.modularization.router", "RouteManager");


    private static TypeSpec createTypeSpec(ClassName className, List<BindClass> classes) {
        TypeSpec.Builder result = TypeSpec.classBuilder(className.simpleName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        result.addMethod(createStaticMethod(classes));
        return result.build();
    }

    private static MethodSpec createStaticMethod(List<BindClass> bindClasses) {
        MethodSpec.Builder method = MethodSpec.methodBuilder(Constant.ROUTE_INIT_CLASS_METHOD)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        for (BindClass bindClazz : bindClasses) {
            CodeBlock.Builder builder = CodeBlock.builder()
                    .add("$T.getInstance().addRoute($S, $L)",
                            ROUTE_MANAGER, bindClazz.getValue(), bindClazz.getClassName() + ".class");
            method.addStatement("$L", builder.build());
        }
        return method.build();
    }


    public static JavaFile preJavaFileByList(ClassName className, List<BindClass> classes) {
        return JavaFile.builder(className.packageName(), createTypeSpec(className, classes))
                .addFileComment("Generated code from " + Constant.LIB_NAME + ". Do not modify!!!")
                .build();
    }


    private static TypeSpec createTypeSpecForModuleNames(ClassName className, List<String> classes) {
        TypeSpec.Builder result = TypeSpec.classBuilder(className.simpleName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        result.addMethod(createStaticMethodForModuleNames(classes));
        return result.build();
    }

    private static MethodSpec createStaticMethodForModuleNames(List<String> moduleNames) {
        MethodSpec.Builder method = MethodSpec.methodBuilder(Constant.ROUTE_INIT_CLASS_METHOD)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        for (String name : moduleNames) {
            String simpleName = Constant.ROUTE_INIT_MODULE_CLASS_PREFIX + name;
            ClassName className = ClassName.get(Constant.ROUTE_INIT_CLASS_PACKAGE, simpleName);
            CodeBlock.Builder builder = CodeBlock.builder()
                    .add("$T.$L", className, Constant.ROUTE_INIT_CLASS_METHOD + "()");
            method.addStatement("$L", builder.build());
        }
        return method.build();
    }


    public static JavaFile preJavaFileByModuleNames(ClassName className, List<String> moduleNames) {
        return JavaFile.builder(className.packageName(), createTypeSpecForModuleNames(className, moduleNames))
                .addFileComment("Generated code from " + Constant.LIB_NAME + ". Do not modify!!!")
                .build();
    }
}
