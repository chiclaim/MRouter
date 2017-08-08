#如果打开dontoptimize选项会不能消除debug日志信息
#-dontoptimize
-dontskipnonpubliclibraryclasses

#不用预先检查
-dontpreverify
#关闭混淆
#-dontobfuscate
#不用输出通知
-dontnote
#不用输出详细的过程
-verbose

#系统v4包
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

#系统v7包
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

#四大组件和其子类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment


#MRouter
-keep public class com.chiclaim.modularization.router.**{*;}
-keepclasseswithmembernames class * { @com.chiclaim.modularization.router.annotation.* <methods>; }
-keepclasseswithmembernames class * { @com.chiclaim.modularization.router.annotation.* <fields>; }
-keep public class * implements com.chiclaim.modularization.router.IAutowired{ public <init>(**); }