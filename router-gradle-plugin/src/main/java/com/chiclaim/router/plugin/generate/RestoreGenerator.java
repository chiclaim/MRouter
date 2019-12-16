package com.chiclaim.router.plugin.generate;

import com.chiclaim.router.plugin.bean.AnnotationBean;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * Description
 * <br/>
 * Created by kumu on 2018/7/23.
 */
public class RestoreGenerator {

    private RestoreGenerator() {}


    public static void generateOnSave(MethodVisitor mv, List<AnnotationBean> restoreAnnotationFields) {

        mv.visitCode();

        //Code: super.onSaveInstanceState(bundle)
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL, "android/support/v7/app/AppCompatActivity", "onSaveInstanceState",
                "(Landroid/os/Bundle;)V", false);


        //Code: Toast.make(getApplication(),"this is from asm toast",Toast.SHORT).show();
        //getApplication()
        //mv.visitVarInsn(ALOAD, 0);
        //mv.visitMethodInsn(INVOKEVIRTUAL, "android/content/Context", "getApplicationContext", "()Landroid/content/Context;", false);
        //toast message
        //mv.visitLdcInsn("this is from asm toast");
        //toast short
        //mv.visitInsn(ICONST_1);
        //makeText
        //mv.visitMethodInsn(INVOKESTATIC, "android/widget/Toast", "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
        //show
        //mv.visitMethodInsn(INVOKEVIRTUAL, "android/widget/Toast", "show", "()V", false);

        for (AnnotationBean bean : restoreAnnotationFields) {
            //Code: outState.putInt("currentPosition", currentPosition);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn(bean.getFieldName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, bean.getClassName(), bean.getFieldName(), bean.getFieldType());
            mv.visitMethodInsn(INVOKEVIRTUAL, "android/os/Bundle", "putInt", "(Ljava/lang/String;I)V", false);
        }

        mv.visitInsn(RETURN);
        //maxStack:3; maxLocals:2
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    public static void generateOnRestore(MethodVisitor mv, List<AnnotationBean> restoreAnnotationFields) {
        mv.visitCode();
        //Code: super.onRestoreInstanceState(bundle)
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL, "android/support/v7/app/AppCompatActivity", "onRestoreInstanceState",
                "(Landroid/os/Bundle;)V", false);
        //Code: currentPosition = savedInstanceState.getInt("currentPosition");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn("currentPosition");
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/os/Bundle", "getInt", "(Ljava/lang/String;)I", false);
        mv.visitFieldInsn(PUTFIELD, "com/chiclaim/log/RestoreActivity", "currentPosition", "I");

/*

        //show currentPosition value
        mv.visitVarInsn(ALOAD, 0);
        //toast message
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "com/chiclaim/log/RestoreActivity", "currentPosition", "I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false);
        //toast short
        mv.visitInsn(ICONST_1);
        //makeText
        mv.visitMethodInsn(INVOKESTATIC, "android/widget/Toast", "makeText",
                "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
        //show
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/widget/Toast", "show",
                "()V", false);
*/

        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }


}
