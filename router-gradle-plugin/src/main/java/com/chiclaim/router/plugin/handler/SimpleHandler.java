package com.chiclaim.router.plugin.handler;

import com.chiclaim.router.plugin.bean.AnnotationBean;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;

public abstract class SimpleHandler implements IHandler {

    protected ClassVisitor classVisitor;

    public SimpleHandler(ClassVisitor classVisitor) {
        this.classVisitor = classVisitor;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

    }

    @Override
    public void visitSource(String source, String debug) {

    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {

    }

    @Override
    public void visitAnnotation(String desc, boolean visible) {

    }

    @Override
    public void visitAttribute(Attribute attribute) {

    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {

    }

    @Override
    public void visitField(int access, String name, String desc, String signature, Object value) {

    }

    @Override
    public void visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

    }

    @Override
    public void visitEnd() {

    }

    @Override
    public void visitFieldAnnotation(String desc, boolean visible, AnnotationBean annotationBean) {

    }

    @Override
    public void visitFieldAnnotationMember(String name, Object value, AnnotationBean annotationBean) {

    }

}
