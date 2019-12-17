package com.chiclaim.modularization.router.compiler;

import com.chiclaim.modularization.router.Constant;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.router.compiler.utils.ProcessorUtils;
import com.chiclaim.modularization.router.compiler.utils.RouteJavaFileUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.chiclaim.modularization.router.Constant.KEY_MODULE_NAME;

@AutoService(Processor.class)
@SupportedOptions(KEY_MODULE_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouteProcessor extends AbstractProcessor {

    private Filer filter;
    private Messager messager;
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {

        super.init(processingEnvironment);
        filter = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        processingEnvironment.getOptions();
        Map<String, String> options = processingEnv.getOptions();
        if (options != null && !options.isEmpty()) {
            moduleName = options.get(KEY_MODULE_NAME);
            if (moduleName != null && moduleName.length() > 0) {
                moduleName = ProcessorUtils.filterModuleName(moduleName);
            }
        }
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Route.class.getCanonicalName());
        return set;
    }

    private void printElement(Element element, Class annotationClazz) {
        printValue(element.getEnclosingElement().toString());
        printValue("========element name " + element.getSimpleName());
        printValue("        element type " + element.asType());
        if (element.getAnnotation(Route.class) != null) {
            printValue("        annotation value " + element.getAnnotation(Route.class).path());
        }
    }

    private void printValue(String obj) {
        messager.printMessage(Diagnostic.Kind.NOTE, obj);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        ArrayList<AutowireRouteClass> list = new ArrayList<>();
        Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element : routeElements) {
            //printElement(element, Route.class);
            list.add(AutowireRouteClass.createWhenApplyClass(element));
        }
        if (!list.isEmpty()) {
            try {
                ClassName className = ClassName.get(Constant.ROUTE_INIT_CLASS_PACKAGE,
                        Constant.ROUTE_INIT_MODULE_CLASS_PREFIX + moduleName);
                RouteJavaFileUtils.preJavaFileByList(className, list).writeTo(filter);
                printValue("MRoute Generated Java File -->" + className);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // 生成初始化类
        /*List<String> modules = new ArrayList<>();
        Set<? extends Element> ComponentsElements = roundEnvironment.getElementsAnnotatedWith(Components.class);
        for (Element element : ComponentsElements) {
            String[] moduleNames = element.getAnnotation(Components.class).value();
            for (String module : moduleNames) {
                modules.add(ProcessorUtils.filterModuleName(module));
            }
        }

        if (!modules.isEmpty()) {
            try {
                ClassName className = ClassName.get(Constant.ROUTE_INIT_CLASS_PACKAGE, Constant.ROUTE_INIT_CLASS);
                RouteJavaFileUtils.preJavaFileByModuleNames(className, modules).writeTo(filter);
                printValue("MRoute Generated Java File -->" + className);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        return true;
    }
}
