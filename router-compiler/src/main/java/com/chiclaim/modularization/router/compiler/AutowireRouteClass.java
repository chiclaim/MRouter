package com.chiclaim.modularization.router.compiler;

import com.chiclaim.modularization.router.annotation.Autowire;
import com.chiclaim.modularization.router.annotation.Constant;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.router.compiler.utils.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/25.
 */

public class AutowireRouteClass {


    private String targetTypeName;
    private String value;

    private TypeName typeName;
    private ClassName className;
    private List<AutowireField> fields;


    static AutowireRouteClass createWhenApplyClass(Element element) {
        AutowireRouteClass bindClass = new AutowireRouteClass();
        bindClass.targetTypeName = element.asType().toString();
        bindClass.value = element.getAnnotation(Route.class).path();
        return bindClass;
    }

    static AutowireRouteClass createWhenApplyField(Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        AutowireRouteClass autowireRouteClass = new AutowireRouteClass();
        String packageName = enclosingElement.getQualifiedName().toString();
        packageName = packageName.substring(0, packageName.lastIndexOf("."));
        String className = enclosingElement.getSimpleName().toString();
        autowireRouteClass.typeName = ProcessorUtils.getTypeName(enclosingElement);
        autowireRouteClass.value = element.getAnnotation(Autowire.class).name();
        autowireRouteClass.targetTypeName = element.asType().toString();
        autowireRouteClass.className = ClassName.get(packageName, className +
                Constant.ROUTE_INIT_MODULE_CLASS_AUTOWIRE_SUFFIX);
        autowireRouteClass.fields = new ArrayList<>();
        return autowireRouteClass;
    }

    public void addAnnotationField(AutowireField field) {
        fields.add(field);
    }


    public String getTargetTypeName() {
        return targetTypeName;
    }

    public String getValue() {
        return value;
    }


    JavaFile preJavaFile() {
        return JavaFile.builder(className.packageName(), createTypeSpec())
                .addFileComment("Generated code from My Butter Knife. Do not modify!!!")
                .build();
    }

    private TypeSpec createTypeSpec() {
        TypeSpec.Builder result = TypeSpec.classBuilder(className.simpleName())
                .addModifiers(Modifier.PUBLIC);
        result.addModifiers(Modifier.FINAL);
        result.addMethod(createConstructor(typeName));
        return result.build();
    }

    private MethodSpec createConstructor(TypeName targetType) {
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);
        constructor.addParameter(targetType, "target", Modifier.FINAL);
        for (AutowireField bindings : fields) {
            addInitStatement(constructor, bindings);
        }
        return constructor.build();
    }

    private void addInitStatement(MethodSpec.Builder result, AutowireField field) {
        CodeBlock.Builder builder = CodeBlock.builder()
                .add("target.$L = ", field.getName());
        if (field.isNeedCast()) {
            builder.add("($T)target.getIntent().$L",
                    field.getType(),
                    CodeBlock.of(field.getAssignStatement(), field.getValue()).toString());
        } else {
            builder.add("target.getIntent().$L",
                    CodeBlock.of(field.getAssignStatement(), field.getValue()).toString());
        }
        result.addStatement("$L", builder.build());

    }

}
