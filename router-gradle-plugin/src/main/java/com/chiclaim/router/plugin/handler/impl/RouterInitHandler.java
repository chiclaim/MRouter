package com.chiclaim.router.plugin.handler.impl;

import com.chiclaim.router.plugin.bean.GlobalInfo;
import com.chiclaim.router.plugin.handler.SimpleHandler;
import org.objectweb.asm.ClassVisitor;

public class RouterInitHandler extends SimpleHandler {


    private GlobalInfo scanResultInfo;

    public RouterInitHandler(ClassVisitor classVisitor, GlobalInfo scanResultInfo) {
        super(classVisitor);
        this.scanResultInfo = scanResultInfo;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (interfaces != null) {
            for (String interfaceName : interfaces) {
                if (scanResultInfo.getRouterConfig().getComponentInterface().equals(interfaceName)) {
                    scanResultInfo.getRouterComponents().add(name);
                    break;
                }
            }
        }
    }

    @Override
    public boolean hadUpdateBytecode() {
        return false;
    }

}
