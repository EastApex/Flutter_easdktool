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
-dontwarn net.sqlcipher.database.**
-dontwarn rx.**
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keeppackagenames com.apex.ax_bluetooth.*
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
-keep class com.github.yuweiguocn.library.greendao.*{*;}
-keep public class * extends android.content.BroadcastReceiver**

-keep class * extends org.greenrobot.greendao.AbstractDaoMaster
-keep class * extends org.greenrobot.greendao.database.DatabaseOpenHelper{*;}
-keep class * extends android.database.sqlite.SQLiteOpenHelper


#-keep  class org.greenrobot.greendao.identityscope.IdentityScopeType{*;}
-keep class org.greenrobot.greendao.**{*;}
-keep public interface org.greenrobot.greendao.**

-keep class com.github.yuweiguocn.library.greendao.**{*;}

-dontwarn org.greenrobot.greendao.database.**



-dontobfuscate
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties { *; }

# If you DO use SQLCipher:
-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }

# If you do NOT use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do NOT use RxJava:
-dontwarn rx.**
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}
-keep class com.example.easdktool.*{*;}
-keepclassmembers enum *{*;}
-keep class com.example.easdktool.enumerate.*{*;}
-keep class io.flutter.*{*;}
-keep class com.alibaba.*{*;}
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-keep class * implements android.os.Parcelable {

  public static final android.os.Parcelable$Creator *;

}
-keep class * implements io.flutter.embedding.engine.plugins.FlutterPlugin
-keep class * implements io.flutter.plugin.common.EventChannel.*{*;}


