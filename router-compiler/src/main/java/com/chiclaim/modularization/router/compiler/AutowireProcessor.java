package com.chiclaim.modularization.router.compiler;

import com.chiclaim.modularization.router.annotation.Autowire;
import com.chiclaim.modularization.router.annotation.Constant;
import com.chiclaim.modularization.router.compiler.utils.ProcessorUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
@SupportedOptions(Constant.KEY_MODULE_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AutowireProcessor extends AbstractProcessor {


    private Types types;
    private Elements elements;

    private Filer filter;
    private Messager messager;
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        types = processingEnv.getTypeUtils();            // Get type utils.
        elements = processingEnv.getElementUtils();      // Get class meta.
        filter = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        printValue("init====" + this);
        processingEnvironment.getOptions();
        Map<String, String> options = processingEnv.getOptions();
        if (options != null && !options.isEmpty()) {
            moduleName = options.get(Constant.KEY_MODULE_NAME);
            if (moduleName != null && moduleName.length() > 0) {
                moduleName = ProcessorUtils.filterModuleName(moduleName);
            }
            printValue("moduleName:" + moduleName);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Autowire.class.getCanonicalName());
        return set;
    }

    private void printElement(Element element, Class annotationClazz) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        printValue("========annotation 所在的类完整名称 " + enclosingElement.getQualifiedName());
        printValue("========annotation 所在类的类名 " + enclosingElement.getSimpleName());
        printValue("========annotation 所在类的父类 " + enclosingElement.getSuperclass());
        printValue("        annotation所在的类 " + enclosingElement.asType());
        printValue("        annotation所在的字段类型 " + element.asType());
        printValue("        annotation 上的值 " + element.getAnnotation(Autowire.class).name());
        printValue("        type is Activity " + ProcessorUtils.isInActivity(elements, types, enclosingElement));

    }

    private void printValue(String obj) {
        messager.printMessage(Diagnostic.Kind.NOTE, obj);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<TypeElement, AutowireRouteClass> map = new LinkedHashMap<>();
        Set<? extends Element> autowireElements = roundEnvironment.getElementsAnnotatedWith(Autowire.class);
        for (Element element : autowireElements) {
            printElement(element, Autowire.class);
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            AutowireRouteClass autowireRouteClass = map.get(enclosingElement);
            if (autowireRouteClass == null) {
                autowireRouteClass = AutowireRouteClass.createWhenApplyField(element);
                map.put(enclosingElement, autowireRouteClass);
            }
            String annotationValue = element.getAnnotation(Autowire.class).name();
            String name = element.getSimpleName().toString();
            TypeName type = TypeName.get(element.asType());

            TypeKind kind = ProcessorUtils.getElementType(element, types, elements);
            String statement = ProcessorUtils.getStatementByElementType(kind);

            AutowireField viewBinding = AutowireField.create(name, type, annotationValue, statement,
                    kind == TypeKind.SERIALIZABLE);
            autowireRouteClass.addAnnotationField(viewBinding);

        }

        for (Map.Entry<TypeElement, AutowireRouteClass> entry : map.entrySet()) {
            try {
                entry.getValue().preJavaFile().writeTo(filter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
