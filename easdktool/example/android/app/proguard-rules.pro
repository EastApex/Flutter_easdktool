


# 混淆时所采用的算法(谷歌推荐算法)
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
#-useuniqueclassmembernames
#-allowaccessmodification



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
-keep class io.flutter.app.** { *; }
-keep class io.flutter.plugin.** { *; }
-keep class io.flutter.util.** { *; }
-keep class io.flutter.view.** { *; }
-keep class io.flutter.** { *; }
-keep class com.example.easdktool_example.** { *; }  # 替换com.example.yourappname为你的包名

# 保留Dart代码
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends android.content.BroadcastReceiver  {
    public <init>();
}
-keep public class * extends  android.content.ContentProvider{
 public <init>();
}
# Flutter wrapper
-keep class * extends android.app.Activity






