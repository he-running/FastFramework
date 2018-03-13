# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\as-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

    #指定代码的压缩级别
    -optimizationpasses 5

    #有时候如果不加这个,那么在混淆后会出现view点击事件无效的问题
    -dontshrink

    #包明不混合大小写
    -dontusemixedcaseclassnames

    #不去忽略非公共的库类
    -dontskipnonpubliclibraryclasses

     #优化  不优化输入的类文件
    -dontoptimize

     #预校验
    -dontpreverify

     #混淆时是否记录日志
    -verbose

     # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

    #保护注解
    -keepattributes *Annotation*

    # 保持哪些类不被混淆
    -keep public class * extends android.app.Fragment
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService
    -keep public class com.android.vending.licensing.ILicensingService
    #如果有引用v4包可以添加下面这行
    -keep public class * extends android.support.v4.app.Fragment

    #忽略警告
    -ignorewarning

    ##记录生成的日志数据,gradle build时在本项目根目录输出##

    #apk 包内所有 class 的内部结构
    -dump class_files.txt
    #未混淆的类和成员
    -printseeds seeds.txt
    #列出从 apk 中删除的代码
    -printusage unused.txt
    #混淆前后的映射
    -printmapping mapping.txt

    # 过滤第三方包的混淆：其中packagename为第三方包的包名
    # -keep class packagename.** {*;}

    #-------------------private Lib-----------------
    -keep class org.achartengine.** {*;}
    -dontwarn org.achartengine.**

    -keep class org.simple.eventbus.** { *; }
    -dontwarn org.simple.eventbus.**

    -keep class com.baidu.** {*;}
    -dontwarn com.baidu.**

    -keep class pvi.com.gdi.bgl.android.java.** { *; }
    -dontwarn pvi.com.gdi.bgl.android.java.**

    -keep class vi.com.gdi.bgl.android.java.** { *; }
    -dontwarn vi.com.gdi.bgl.android.java.**

    -keep class org.apache.commons.** { *; }
    -dontwarn org.apache.commons.**



    -keep class com.google.zxing.** { *; }
    -dontwarn com.google.zxing.**

    -keep class com.jiantong.ui.location.dial.dialog.** { *; }
    -dontwarn com.jiantong.ui.location.dial.dialog.**

    -keep class org.apache.http.entity.mime.** { *; }
    -dontwarn org.apache.http.entity.mime.**

    -keep class com.iyiguo.locationengine.** { *; }
    -dontwarn com.iyiguo.locationengine.**

    -keep class org.apache.mina.** { *; }
    -dontwarn org.apache.mina.**

    -keep class com.google.protobuf.** { *; }
    -dontwarn com.google.protobuf.**

    -keep class org.slf4j.impl.** { *; }
    -dontwarn org.slf4j.impl.**

    -keep class com.nineoldandroids.** { *; }
    -dontwarn com.nineoldandroids.**

    -keep class org.slf4j.** { *; }
    -dontwarn org.slf4j.**

    -keep class com.lidroid.xutils.** { *; }
    -dontwarn com.lidroid.xutils.**

     -keep class net.lingala.zip4j.** { *; }
     -dontwarn net.lingala.zip4j.**

    -keep class com.android.internal.http.multipart.** { *; }
    -dontwarn com.android.internal.http.multipart.**

    #------------------dependencies-----------
    -keep class com.beardedhen.androidbootstrap.** { *; }
    -dontwarn com.beardedhen.androidbootstrap.**

    -keep class com.teligen.authorizelib.** { *; }
    -dontwarn com.teligen.authorizelib.**

    -keep class com.teligen.appmanagelib.** { *; }
    -dontwarn com.teligen.appmanagelib.**

    -keep class com.wangjialin.internet.service.** { *; }
    -dontwarn com.wangjialin.internet.service.**

    -keep class dagger.** { *; }
    -dontwarn dagger.**

    -keep class com.rabbitmq.** { *; }
    -dontwarn com.rabbitmq.**

    -keep class com.google.dexmaker.** { *; }
    -dontwarn com.google.dexmaker.**

    -keep class com.google.dexmaker.mockito.** { *; }
    -dontwarn com.google.dexmaker.mockito.**

    -keep class android.support.graphics.drawable.** { *; }
    -dontwarn android.support.graphics.drawable.**

    -keep class android.support.test.** { *; }
    -dontwarn android.support.test.**

    -keep class com.tencent.bugly.** { *; }
    -dontwarn com.tencent.bugly.**
    ##所有腾讯的代码都应不混淆##
    -keep class com.tencent.** { *; }
    -dontwarn com.tencent.**

    -keep class com.tencent.** { *; }
    -dontwarn com.tencent.**

    -keep class android.support.test.espresso.** { *; }
    -dontwarn android.support.test.espresso.**

    -keep class com.android.support.test.deps.** { *; }
    -dontwarn com.android.support.test.deps.**

    -keep class org.hamcrest.** { *; }
    -dontwarn org.hamcrest.**

    -keep class com.squareup.javawriter.** { *; }
    -dontwarn com.squareup.javawriter.**

    -keep class javax.inject.** { *; }
    -dontwarn javax.inject.**

    -keep class junit.** { *; }
    -dontwarn junit.**

    -keep class org.junit.** { *; }
    -dontwarn org.junit.**

    -keep class com.bruce.pickerview.** { *; }
    -dontwarn com.bruce.pickerview.**

    -keep class com.baoyz.swipemenulistview.** { *; }
    -dontwarn com.baoyz.swipemenulistview.**

    -keep class org.mockito.** { *; }
    -dontwarn org.mockito.**

    -keep class com.google.gson.** { *; }
    -dontwarn com.google.gson.**

    -keep class javax.annotation.** { *; }
    -dontwarn javax.annotation.**

    -keep class android.support.multidex.** { *; }
    -dontwarn android.support.multidex.**

    -keep class com.android.test.runner.** { *; }
    -dontwarn com.android.test.runner.**

    -keep class android.support.multidex.instrumentation.** { *; }
    -dontwarn android.support.multidex.instrumentation.**

    -keep class rx.** { *; }
    -dontwarn rx.**

    -keep class android.support.annotation.** { *; }
    -dontwarn android.support.annotation.**

    -keep class com.teligen.** { *; }
    -dontwarn com.teligen.**

    -keep class com.smart.location.protocol.** { *; }
    -dontwarn com.smart.location.protocol.**

    -keep class org.objenesis.** { *; }
    -dontwarn org.objenesis.**

    -keep class android.net.** { *; }
    -dontwarn android.net.**

    -keep class org.apache.** { *; }
    -dontwarn org.apache.**

    -keep class cn.swu.** { *; }
    -dontwarn cn.swu.**

    -keep class org.xclcharts.** { *; }
    -dontwarn org.xclcharts.**

    -keep class org.xutils.** { *; }
    -dontwarn org.xutils.**

    -keep class android.backport.webp.** { *; }
    -dontwarn android.backport.webp.**

    #如果引用了v4或者v7包
    -keep class android.support.** { *; }
    -dontwarn android.support.**

    ########记录生成的日志数据，gradle build时 在本项目根目录输出-end######
    #####混淆保护自己项目的部分代码以及引用的第三方jar包library#######
    #-libraryjars libs/umeng-analytics-v5.2.4.jar

    #三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
    #-libraryjars libs/sdk-v1.0.0.jar
    #-libraryjars libs/look-v1.0.1.jar


    #------------------------------------

    #如果不想混淆 keep 掉
    #-keep class com.lippi.recorder.iirfilterdesigner.** {*; }
    #项目特殊处理代码

    #忽略警告
    #-dontwarn com.lippi.recorder.utils**
    #保留一个完整的包
    #-keep class com.lippi.recorder.utils.** {
    #    *;
    # }

    #-keep class  com.lippi.recorder.utils.AudioRecorder{*;}

    ####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####
    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }

    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }

    #保持 Parcelable 不被混淆
       -keep class * implements android.os.Parcelable {
         public static final android.os.Parcelable$Creator *;
    }

    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }

    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable

    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }

    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    #-keepclassmembers enum * {
    #  public static **[] values();
    #  public static ** valueOf(java.lang.String);
    #}

    -keepclassmembers class * {
        public void *ButtonClicked(android.view.View);
    }

    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }

    #不混淆滑动面板的java反射动态后退方法
    -keepclassmembers class * extends com.smart.location.presentation.presenter.SlidePanePresenter {
        public void showSlidePane(com.smart.location.presentation.presenter.SlidePanePresenter, com.yinglan.scrolllayout.ScrollLayout.Status);
        public void hideSlidePane();
    }

    #避免混淆泛型 如果混淆报错建议关掉
    #–keepattributes Signature

    #移除log 测试了下没有用还是建议自己定义一个开关控制是否输出日志
    #-assumenosideeffects class android.util.Log {
    #    public static boolean isLoggable(java.lang.String, int);
    #    public static int v(...);
    #    public static int i(...);
    #    public static int w(...);
    #    public static int d(...);
    #    public static int e(...);
    #}