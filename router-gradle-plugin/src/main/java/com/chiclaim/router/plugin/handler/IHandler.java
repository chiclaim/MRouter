package com.chiclaim.router.plugin.handler;

import org.objectweb.asm.Attribute;

public interface IHandler extends IFieldVisitorHandler, IUpdate {

    void visit(int version, int access, String name, String signature, String superName, String[] interfaces);

    void visitSource(String source, String debug);

    void visitOuterClass(String owner, String name, String desc);


    void visitAnnotation(String desc, boolean visible);

    void visitAttribute(Attribute attribute);

    void visitInnerClass(String name, String outerName, String innerName, int access);

    void visitField(int access, String name, String desc, String signature, Object value);


    void visitMethod(int access, final String name, final String desc, String signature, String[] exceptions);

    void visitEnd();
}
