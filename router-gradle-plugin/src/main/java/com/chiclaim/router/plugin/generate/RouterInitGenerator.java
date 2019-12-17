package com.chiclaim.router.plugin.generate;

import com.chiclaim.router.plugin.bean.GlobalInfo;
import com.chiclaim.router.plugin.util.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import static org.objectweb.asm.Opcodes.ASM5;

public class RouterInitGenerator {

    private RouterInitGenerator() {
    }

    public static void updateInitClassBytecode(GlobalInfo globalInfo) throws IOException {
        for (File file : globalInfo.getRouterInitTransformFiles()) {
            if (file.getName().endsWith(".jar")) {
                JarFile jarFile = new JarFile(file);
                Enumeration enumeration = jarFile.entries();

                // create tmp jar file
                File tmpJarFile = new File(file.getParent(), file.getName() + ".tmp");

                if (tmpJarFile.exists()) {
                    tmpJarFile.delete();
                }

                JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpJarFile));

                while (enumeration.hasMoreElements()) {
                    JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                    // eg. com/google/common/collect/AbstractTable.class
                    String entryName = jarEntry.getName();
                    ZipEntry zipEntry = new ZipEntry(entryName);
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    jarOutputStream.putNextEntry(zipEntry);
                    if (Utils.isRouterInitClass(globalInfo, entryName.replace(".class", ""))) {
                        byte[] bytes = generateClassBytes(globalInfo, inputStream);
                        jarOutputStream.write(bytes);
                    } else {
                        jarOutputStream.write(IOUtils.toByteArray(inputStream));
                    }
                    // inputStream.close(); close by ClassReader
                    jarOutputStream.closeEntry();
                }
                jarOutputStream.close();
                jarFile.close();

                if (file.exists()) {
                    file.delete();
                }
                tmpJarFile.renameTo(file);
            } else {
                byte[] classBytes = generateClassBytes(globalInfo, new FileInputStream(file));
                FileUtils.writeByteArrayToFile(file, classBytes, false);
            }
        }
    }

    private static byte[] generateClassBytes(GlobalInfo globalInfo, InputStream inputStream) throws IOException {
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new RouterInitVisitor(Opcodes.ASM5, cw, globalInfo);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
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
