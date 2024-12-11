-dontwarn org.greenrobot.greendao.**
-dontwarn net.sqlcipher.database.**
-dontwarn rx.**
-keep @androidx.annotation.Keep class *
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

