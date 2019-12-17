package com.chiclaim.router.plugin.visitor;

import com.chiclaim.router.plugin.handler.IHandler;
import com.chiclaim.router.plugin.handler.IUpdate;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.objectweb.asm.Opcodes.ASM5;

public class CodeScanClassVisitor extends ClassVisitor implements IUpdate {


    private List<IHandler> handlers;


    public CodeScanClassVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
        handlers = new ArrayList<>(4);
    }

    public void registerHandler(IHandler handler) {
        handlers.add(handler);
    }

    @Override
    public boolean hadUpdateBytecode() {
        for (IUpdate update : handlers) {
            if (update.hadUpdateBytecode()) return true;
        }
        return false;
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        for (IHandler handler : handlers) {
            handler.visit(version, access, name, signature, superName, interfaces);
        }
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
        for (IHandler handler : handlers) {
            handler.visitSource(source, debug);
        }
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(owner, name, desc);
        for (IHandler handler : handlers) {
            handler.visitOuterClass(owner, name, desc);
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        for (IHandler handler : handlers) {
            handler.visitAnnotation(desc, visible);
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        for (IHandler handler : handlers) {
            handler.visitAttribute(attribute);
        }
        super.visitAttribute(attribute);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        for (IHandler handler : handlers) {
            handler.visitInnerClass(name, outerName, innerName, access);
        }

        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        for (IHandler handler : handlers) {
            handler.visitField(access, name, desc, signature, value);
        }
        return super.visitField(access, name, desc, signature, value);
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
        for (IHandler handler : handlers) {
            handler.visitEnd();
        }
    }
}
