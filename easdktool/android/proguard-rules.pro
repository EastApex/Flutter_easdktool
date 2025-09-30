# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontpreverify
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-keepattributes *Annotation*
-dontwarn net.sqlcipher.database.**
-dontwarn rx.**
-dontwarn org.greenrobot.greendao.**
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#-keeppackagenames com.apex.ax_bluetooth.*
-keep class com.apex.ax_bluetooth.*{*;}
-keep class android.Manifest{*;}
-keep class android.content.*{*;}
-keep class androidx.core.content.*{*;}
-keep public class android.bluetooth.*{*;}
-keep public class android.os.*{*;}
-keep public class android.text.*{*;}
-keep public class android.net.*{*;}
-keep public class java.math.*{*;}
-keep public class java.util.*{*;}
-keep public class androidx.core.os.*{*;}
-keep public class android.util.*{*;}
-keep public class androidx.core.*{*;}
-keep public class com.google.protobuf.*{*;}
-keep public class google.protobuf.*{*;}
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }
-keep public class java.nio.charset.*{*;}
-keep public class android.*{*;}
-keep class * extends android.app.Application
-keep public class java.io.*{*;}
-keep public class java.lang.*{*;}
-keep public class java.text.*{*;}
-keep public class * extends android.content.BroadcastReceiver**

-keep class org.greenrobot.greendao.**{*;}
-keep public class * extends org.greenrobot.greendao.AbstractDao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keepclassmembers class **$Properties {*;}
-keep class net.sqlcipher.database.**{*;}
-keep public interface net.sqlcipher.database.**
-keep class * extends android.database.sqlite.SQLiteOpenHelper
-keep class com.github.yuweiguocn.library.greendao.**{*;}
# If you DO use SQLCipher:
-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}
-keep class com.greendao.gen.**{*;}


# If you do NOT use RxJava:
-dontwarn rx.**
-keep class com.example.easdktool.**{*;}
-keepclassmembers enum *{*;}
-keep class com.example.easdktool.enumerate.*{*;}
-keep class io.flutter.*{*;}
-keep class com.alibaba.fastjson.** { *; }
-keepclassmembers class com.alibaba.fastjson.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okhttp3.logging.HttpLoggingInterceptor { *; }
-dontwarn okhttp3.**
-keep class * implements android.os.Parcelable {

  public static final android.os.Parcelable$Creator *;

}
-keep class com.jieli.jl_bt_ota.** { *; }



