package com.chiclaim.router.plugin.generate;

import com.chiclaim.router.plugin.bean.GlobalInfo;
import org.apache.commons.io.FileUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.FileInputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM5;

public class RouterInitGenerator {

    private RouterInitGenerator() {
    }

    public static void generateInit(GlobalInfo globalInfo) throws IOException {
        FileInputStream inputStream = new FileInputStream(globalInfo.getRouterInitTransformFile());
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new RouterInitVisitor(Opcodes.ASM5, cw, globalInfo);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        byte[] classBytes = cw.toByteArray();
        FileUtils.writeByteArrayToFile(globalInfo.getRouterInitTransformFile(), classBytes, false);
    }

    private static class RouterInitVisitor extends ClassVisitor {

        private GlobalInfo globalInfo;

        RouterInitVisitor(int i, ClassVisitor classVisitor, GlobalInfo globalInfo) {
            super(i, classVisitor);
            this.globalInfo = globalInfo;
        }


        @Override
        public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
            return new AdviceAdapter(ASM5, methodVisitor, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    super.onMethodEnter();

                    if (name.equals(globalInfo.getRouterConfig().getRouterInitMethod())) {
                        for (String clazz : globalInfo.getRouterComponents()) {
                            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                                    clazz,
                                    globalInfo.getRouterConfig().getRouterInitMethod(),
                                    globalInfo.getRouterConfig().getRouterInitMethodDescriptor(),
                                    false);
                        }
                    }
                }
            };

        }
    }


}
