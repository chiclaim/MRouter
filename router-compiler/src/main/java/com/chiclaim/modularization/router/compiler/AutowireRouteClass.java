package com.chiclaim.modularization.router.compiler;

import com.chiclaim.modularization.router.Constant;
import com.chiclaim.modularization.router.IAutowired;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.router.compiler.utils.ProcessorUtils;
import com.chiclaim.modularization.router.compiler.utils.RouteJavaFileUtils;
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
 * <p>
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
        autowireRouteClass.value = element.getAnnotation(Autowired.class).name();
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

    public ClassName getClassName() {
        return className;
    }

    JavaFile preJavaFile() {
        return JavaFile.builder(className.packageName(), createTypeSpec())
                .addFileComment("Generated code from " + Constant.LIB_NAME + ". Do not modify!!!")
                .build();
    }

    private TypeSpec createTypeSpec() {
        TypeSpec.Builder result = TypeSpec.classBuilder(className.simpleName())
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .addSuperinterface(IAutowired.class)
                .addMethod(createConstructor(typeName));
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
        CodeBlock.Builder builder = CodeBlock.builder();
        switch (field.getTypeKind()) {
            case PARCELABLE_ARRAY:
                builder.add("android.os.Parcelable[] parcelables = target.$L;\n",
                        CodeBlock.of(field.getAssignStatement(), field.getKeyName()))
                        .add("target.$L = java.util.Arrays.copyOf(parcelables,parcelables.length, $T.class)",
                                field.getFieldName(),
                                field.getFieldType());
                break;
            case FRAGMENT:
            case PROVIDER:
                builder.add("java.lang.Class $L = $T.getInstance().getRoute($S);"
                        , field.getFieldName()
                        , RouteJavaFileUtils.ROUTE_MANAGER
                        , field.getKeyName());
                builder.add("\nif($L !=null){\n", field.getFieldName());
                builder.add("try {\n$L" +
                                "        } catch (InstantiationException e) {\n" +
                                "            e.printStackTrace();\n" +
                                "        } catch (IllegalAccessException e) {\n" +
                                "            e.printStackTrace();\n" +
                                "        }",
                        CodeBlock.of("   target.$L = ($T)$L.newInstance();\n",
                                field.getFieldName(), field.getFieldType(), field.getFieldName()));


                builder.add("}");
                break;
            default:
                builder.add("target.$L = ", field.getFieldName());
                if (field.getTypeKind() == FieldTypeKind.SERIALIZABLE) {// 需要强转
                    builder.add("($T)target.$L",
                            field.getFieldType(),
                            CodeBlock.of(field.getAssignStatement(), field.getKeyName()));
                } else if (field.getTypeKind() == FieldTypeKind.STRING) {
                    builder.add("target.$L",
                            CodeBlock.of(field.getAssignStatement(), // 需要三个参数
                                    field.getFieldName(),
                                    field.getKeyName(),
                                    field.getFieldName()));
                } else if (ProcessorUtils.isPrimitiveType(field.getTypeKind())) {
                    builder.add("target.$L",
                            CodeBlock.of(field.getAssignStatement(), // 原始类型支持默认参数
                                    field.getKeyName(),
                                    field.getFieldName()));
                } else {
                    builder.add("target.$L",
                            CodeBlock.of(field.getAssignStatement(), field.getKeyName()));
                }
                break;
        }
        result.addStatement("$L", builder.build());

    }

}
