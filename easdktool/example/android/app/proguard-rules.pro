# 混淆时所采用的算法(谷歌推荐算法)
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
#-useuniqueclassmembernames
#-allowaccessmodification

# If you do NOT use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do NOT use RxJava:
-dontwarn rx.**


-keep class com.example.easdktool_example.** { *; }  # 替换com.example.yourappname为你的包名
-keep class android.**{*;}
-keep class androidx.**{*;}
-keep class java.lang.**{*;}
-keep class io.flutter.**{*;}

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
-dontwarn com.google.android.play.core.splitcompat.SplitCompatApplication
-dontwarn com.google.android.play.core.splitinstall.SplitInstallException
-dontwarn com.google.android.play.core.splitinstall.SplitInstallManager
-dontwarn com.google.android.play.core.splitinstall.SplitInstallManagerFactory
-dontwarn com.google.android.play.core.splitinstall.SplitInstallRequest$Builder
-dontwarn com.google.android.play.core.splitinstall.SplitInstallRequest
-dontwarn com.google.android.play.core.splitinstall.SplitInstallSessionState
-dontwarn com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
-dontwarn com.google.android.play.core.tasks.OnFailureListener
-dontwarn com.google.android.play.core.tasks.OnSuccessListener
-dontwarn com.google.android.play.core.tasks.Task
-dontwarn com.google.common.collect.ArrayListMultimap
-dontwarn com.google.common.collect.Multimap
-dontwarn java.awt.Color
-dontwarn java.awt.Font
-dontwarn java.awt.Point
-dontwarn java.awt.Rectangle
-dontwarn javax.money.CurrencyUnit
-dontwarn javax.money.Monetary
-dontwarn javax.ws.rs.Consumes
-dontwarn javax.ws.rs.Produces
-dontwarn javax.ws.rs.core.Response
-dontwarn javax.ws.rs.core.StreamingOutput
-dontwarn javax.ws.rs.ext.MessageBodyReader
-dontwarn javax.ws.rs.ext.MessageBodyWriter
-dontwarn javax.ws.rs.ext.Provider
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.glassfish.jersey.internal.spi.AutoDiscoverable
-dontwarn org.javamoney.moneta.Money
-dontwarn org.joda.time.DateTime
-dontwarn org.joda.time.DateTimeZone
-dontwarn org.joda.time.Duration
-dontwarn org.joda.time.Instant
-dontwarn org.joda.time.LocalDate
-dontwarn org.joda.time.LocalDateTime
-dontwarn org.joda.time.LocalTime
-dontwarn org.joda.time.Period
-dontwarn org.joda.time.ReadablePartial
-dontwarn org.joda.time.format.DateTimeFormat
-dontwarn org.joda.time.format.DateTimeFormatter
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
-dontwarn springfox.documentation.spring.web.json.Json
-dontwarn javax.servlet.ServletOutputStream
-dontwarn javax.servlet.http.HttpServletRequest
-dontwarn javax.servlet.http.HttpServletResponse
-dontwarn javax.ws.rs.RuntimeType
-dontwarn javax.ws.rs.WebApplicationException
-dontwarn javax.ws.rs.core.Configurable
-dontwarn javax.ws.rs.core.Configuration
-dontwarn javax.ws.rs.core.Context
-dontwarn javax.ws.rs.core.Feature
-dontwarn javax.ws.rs.core.FeatureContext
-dontwarn javax.ws.rs.core.MediaType
-dontwarn javax.ws.rs.core.MultivaluedMap
-dontwarn javax.ws.rs.ext.ContextResolver
-dontwarn javax.ws.rs.ext.Providers
-dontwarn org.glassfish.jersey.CommonProperties
-dontwarn org.glassfish.jersey.internal.util.PropertiesHelper
-dontwarn org.springframework.core.MethodParameter
-dontwarn org.springframework.core.ResolvableType
-dontwarn org.springframework.core.annotation.Order
-dontwarn org.springframework.data.redis.serializer.RedisSerializer
-dontwarn org.springframework.data.redis.serializer.SerializationException
-dontwarn org.springframework.http.HttpHeaders
-dontwarn org.springframework.http.HttpInputMessage
-dontwarn org.springframework.http.HttpOutputMessage
-dontwarn org.springframework.http.MediaType
-dontwarn org.springframework.http.converter.AbstractHttpMessageConverter
-dontwarn org.springframework.http.converter.GenericHttpMessageConverter
-dontwarn org.springframework.http.converter.HttpMessageNotReadableException
-dontwarn org.springframework.http.converter.HttpMessageNotWritableException
-dontwarn org.springframework.http.server.ServerHttpRequest
-dontwarn org.springframework.http.server.ServerHttpResponse
-dontwarn org.springframework.http.server.ServletServerHttpRequest
-dontwarn org.springframework.messaging.Message
-dontwarn org.springframework.messaging.MessageHeaders
-dontwarn org.springframework.messaging.converter.AbstractMessageConverter
-dontwarn org.springframework.util.Assert
-dontwarn org.springframework.util.CollectionUtils
-dontwarn org.springframework.util.MimeType
-dontwarn org.springframework.util.ObjectUtils
-dontwarn org.springframework.util.StringUtils
-dontwarn org.springframework.validation.BindingResult
-dontwarn org.springframework.web.bind.annotation.ControllerAdvice
-dontwarn org.springframework.web.bind.annotation.ResponseBody
-dontwarn org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
-dontwarn org.springframework.web.servlet.view.AbstractView
-dontwarn org.springframework.web.socket.sockjs.frame.AbstractSockJsMessageCodec
-dontwarn retrofit2.Converter$Factory
-dontwarn retrofit2.Converter
-dontwarn retrofit2.Retrofit
-dontwarn net.sqlcipher.Cursor
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
-keep class com.jieli.**{*;}
-keep class com.google.**{*;}










