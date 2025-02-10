-dontpreverify
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keeppackagenames com.apex.ax_bluetooth.*
-dontwarn org.greenrobot.greendao.**
-dontwarn net.sqlcipher.database.**
-dontwarn rx.**
-keep @androidx.annotation.Keep class *

#-keeppackagenames proto2java.*
-obfuscationdictionary rules.txt
-classobfuscationdictionary rules.txt
-packageobfuscationdictionary rules.txt
-keep class com.apex.ax_bluetooth.callback.*{*;}
-keep class com.apex.ax_bluetooth.enumeration.*{*;}
-keep class com.apex.ax_bluetooth.model.*{*;}
-keep class com.apex.ax_bluetooth.listener.EABleConnectListener{*;}
-keep class com.apex.ax_bluetooth.listener.EABleScanListener{*;}
-keep class com.apex.ax_bluetooth.broadcast.ClassicBluetoothBondReceiver{*;}
-keep class com.apex.ax_bluetooth.core.EABleManager{*;}
-keep class com.apex.ax_bluetooth.core.EABleBluetoothOption{public boolean autoReply;}
-keep class com.apex.ax_bluetooth.core.EABleBluetoothOption{public boolean isTestVersion;}
#-keep class com.apex.ax_bluetooth.data_package.package_data.PackageOrder{*;}
#-keep class com.apex.ax_bluetooth.data_parse.Parse8803Data{*;}
#-keep class com.apex.ax_bluetooth.core.impl.MotionReportImpl{*;}
-keep class com.apex.ax_bluetooth.utils.*{*;}
-keep class android.Manifest{*;}
-keep class android.content.*{*;}
-keep class androidx.core.content.*{*;}
-keep public class android.bluetooth.*{*;}
-keep public class android.os.*{*;}
-keep public class android.text.*{*;}
-keep public class java.math.*{*;}
-keep public class java.util.*{*;}
-keep public class androidx.core.os.*{*;}
-keep public class android.util.*{*;}
-keep public class com.google.protobuf.*{*;}
-keep public class google.protobuf.*{*;}
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }
-keep public class java.nio.charset.*{*;}
-keep public class java.io.*{*;}
-keep public class * extends android.content.BroadcastReceiver**
-dontwarn rx.**

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}
-keep public class * extends android.database.sqlite.SQLiteOpenHelper{
public <init>(...);
}
-keep public class * extends org.greenrobot.greendao.AbstractDaoMaster
-keep public class com.greendao.gen.DaoMaster
-keep public class com.greendao.gen.DaoMaster$DevOpenHelper{public <init>(...);}
-keep public class com.greendao.gen.DaoMaster$OpenHelper{
   public <init>(...);
}
-keep public class com.example.easdktool.db.UpgradeHelper{
public <init>(...);
}
-keep public class * extends org.greenrobot.greendao.database.DatabaseOpenHelper{
public <init>(...);
}
-keep class org.greenrobot.greendao.** { *; }
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao { public static java.lang.String TABLENAME; }
-keep class **$Properties{*;}

-keep public interface org.greenrobot.greendao.**
-keep public class com.greendao.gen.**{*;}
-keep public class com.example.easdktool.db.**{*;}

-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }
-keep class android.**{*;}
-keepclassmembers class **$OpenHelper {
    public static ** $getInstance(android.database.sqlite.SQLiteOpenHelper);
}
-keep class **$Entity
-keepclassmembers class * {
    public <init>(org.greenrobot.greendao.database.Database);
}

