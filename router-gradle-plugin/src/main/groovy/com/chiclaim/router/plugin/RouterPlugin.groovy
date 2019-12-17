package com.chiclaim.router.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import com.chiclaim.router.plugin.bean.RouterConfig
import com.chiclaim.router.plugin.bean.GlobalInfo
import com.chiclaim.router.plugin.generate.RouterInitGenerator
import com.chiclaim.router.plugin.handler.impl.RouterInitHandler
import com.chiclaim.router.plugin.util.Utils
import com.chiclaim.router.plugin.visitor.CodeScanClassVisitor
import com.intellij.openapi.util.text.StringUtil
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES

class RouterPlugin extends Transform implements Plugin<Project> {

    private static final String CONFIG_NAME = "router_register"

    private final GlobalInfo globalInfo = new GlobalInfo()

    void apply(Project project) {
        def isAppPlugin = project.plugins.hasPlugin(AppPlugin)
        project.extensions.create(CONFIG_NAME, RouterConfig)
        if (isAppPlugin) {
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(this)
            project.afterEvaluate {
                def config = project.extensions.findByName(CONFIG_NAME) as RouterConfig
                globalInfo.setRouterConfig(config)
                checkRouterConfig(config)
            }
        }
    }


    @Override
    String getName() {
        return "RouterPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    private static File getContentLocation(TransformOutputProvider outputProvider, DirectoryInput directoryInput) {
        return outputProvider.getContentLocation(
                directoryInput.name,
                directoryInput.contentTypes,
                directoryInput.scopes,
                Format.DIRECTORY)
    }


    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {

        println '\n//===============RouterPlugin visit start===============//\n'

        //删除之前的输出
        if (outputProvider != null)
            outputProvider.deleteAll()


        def startTime = System.currentTimeMillis()

        inputs.each { TransformInput input ->

            // 处理 jar 文件
            scanJarFiles(outputProvider, input)

            // 处理目录里的 class 文件
            scanClassInDir(outputProvider, input)

        }


        // generate initial method in router initial class
        if (globalInfo.hasAttentionInfo()) {
            println "\ntotal component count : " + globalInfo.routerComponents.size()
            for (File file : globalInfo.routerInitTransformFiles) {
                println "router init class transform path : " + file.absolutePath
            }
            RouterInitGenerator.updateInitClassBytecode(globalInfo)
        }

        def cost = (System.currentTimeMillis() - startTime) / 1000L

        println "\n~~~~~ router init total cost " + cost + " seconds ~~~~~\n"
        println '//===============RouterPlugin visit end===============//'

    }


    private void scanJarFiles(TransformOutputProvider outputProvider, TransformInput input) {
        input.jarInputs.each { JarInput jarInput ->

            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }

            File file = jarInput.file

            println "\n####### jar file path " + file.absolutePath


            def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)

            def jarFile = new JarFile(file)
            Enumeration enumeration = jarFile.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()

                if (entryName.startsWith("android/support")) break
                if (!entryName.endsWith(".class")) continue


                def classSimpleName = entryName.replace(".class", "")

                if (Utils.isRouterInitClass(globalInfo, classSimpleName)) {
                    globalInfo.getRouterInitTransformFiles().add(dest)
                }

                def packageName = getClassPackage(classSimpleName)
                if (interceptByPackage(globalInfo, packageName)) {
                    println "-----------jar entryName " + entryName
                    // class 字节流
                    InputStream inputStream = jarFile.getInputStream(jarEntry)
                    ClassReader cr = new ClassReader(inputStream)
                    ClassWriter cw = new ClassWriter(cr, 0)
                    CodeScanClassVisitor cv = new CodeScanClassVisitor(cw)
                    cv.registerHandler(new RouterInitHandler(cv, globalInfo))
                    cr.accept(cv, EXPAND_FRAMES)
                }
            }
            jarFile.close()

            // copy jar to transform dir
            FileUtils.copyFile(file, dest)
            println "----jar dest path " + dest
        }

    }

    private void scanClassInDir(TransformOutputProvider outputProvider, TransformInput input) {

        input.directoryInputs.each { DirectoryInput directoryInput ->
            if (directoryInput.file.isDirectory()) {
                directoryInput.file.eachFileRecurse { File file ->

                    def name = file.name
                    if (name.endsWith(".class")
                            && !name.startsWith("R\$")
                            && "R.class" != name
                            && "BuildConfig.class" != name) {


                        // eg. com\chiclaim\log\RouterInit.class
                        def classRelativePath = getClassRelativePath(directoryInput, file)

                        // eg. com/chiclaim/log/RouterInit
                        def classSimpleName = classRelativePath
                                .replaceAll("\\\\", "/")
                                .replace(".class", "")

                        if (Utils.isRouterInitClass(globalInfo, classSimpleName)) {
                            def fileLocation = getContentLocation(outputProvider, directoryInput).absolutePath
                            globalInfo.getRouterInitTransformFiles().add(new File(fileLocation + File.separator + classRelativePath))
                        }

                        def packageName = getClassPackage(classSimpleName)


                        if (interceptByPackage(globalInfo, packageName)) {

                            ClassReader classReader = new ClassReader(file.bytes)
                            // eg. com/chiclaim/log/RouterInit
                            def className = classReader.className

                            println "######## " + file.absolutePath
                            println "---className = " + className + ",superName = " + classReader.superName

                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor cv = new CodeScanClassVisitor(classWriter)
                            // 处理 Restore 注解
                            //cv.registerHandler(new RestoreHandler(cv))
                            cv.registerHandler(new RouterInitHandler(cv, globalInfo))
                            classReader.accept(cv, EXPAND_FRAMES)

                            if (cv.hadUpdateBytecode()) {
                                byte[] code = classWriter.toByteArray()
                                FileUtils.writeByteArrayToFile(file, code)
                            }
                        }
                    }
                }
            }

            def dest = getContentLocation(outputProvider, directoryInput)
            FileUtils.copyDirectory(directoryInput.file, dest)
        }

    }

    private static String getClassPackage(String classSimpleName) {
        int index = classSimpleName.lastIndexOf('/')
        return classSimpleName.substring(0, index)
    }


    /**
     * @param directoryInput
     * @param classFile
     * @return eg. com\chiclaim\log\RouterInit.class
     */
    private static String getClassRelativePath(DirectoryInput directoryInput, File classFile) {
        return classFile.absolutePath
                .replace(directoryInput.file.absolutePath + File.separator, '')
    }

    private static boolean interceptByPackage(GlobalInfo globalInfo, String packageName) {
        // 如果没有配置包名，使用 ClassReader 进行读取
        if (globalInfo.routerConfig.componentPackage == null) {
            return true
        }
        // 如果配置的包名，则判断当前的 class 的包名和配置的包名是否一致
        return globalInfo.getRouterConfig().componentPackage == packageName
    }

    private static void checkRouterConfig(RouterConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("please config router register in app/build.gradle")
        } else if (StringUtil.isEmpty(config.routerInitClass)) {
            throw new IllegalArgumentException("please config routerInitClass in router_register")
        } else if (StringUtil.isEmpty(config.routerInitMethod)) {
            throw new IllegalArgumentException("please config routerInitMethod in router_register")
        } else if (StringUtil.isEmpty(config.componentInterface)) {
            throw new IllegalArgumentException("please config componentInterface in router_register")
        } else {
            println "======================="
            println "router config: " + config.toString()
            println "======================="
        }
    }

}