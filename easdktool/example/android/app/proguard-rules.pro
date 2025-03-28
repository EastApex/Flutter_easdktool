-dontpreverify
-optimizationpasses 5
# 是否使用大小写混合(windows大小写不敏感，建议加入)
-dontusemixedcaseclassnames
 # 是否混淆非公共的库的类
-dontskipnonpubliclibraryclasses
# 是否混淆非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 混淆时是否做预校验(Android不需要预校验，去掉可以加快混淆速度)
# 混淆时是否记录日志(混淆后会生成映射文件)
-verbose


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




