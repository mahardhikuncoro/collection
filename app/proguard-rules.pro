-keep class base.** { *; }
-keep class user.** { *; }
-keep class ops.** { *; }
-keep class id.co.smmf.** { *; }
#-keep class id.co.smma.** { *; }


-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# support design
-keep class android.support.design.widget.** { *; }
-keep interface android.support.design.widget.** { *; }
-dontwarn android.support.design.**

# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep public class * extends android.support.v7.widget.RecyclerView$LayoutManager {
    public <init>(...);
}
# retrofit
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}

# okhttp
-dontwarn okio.**
-dontwarn okhttp3.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# picasso
-dontwarn picasso.**
-dontnote com.squareup.picasso.Utils
-dontwarn com.squareup.picasso.OkHttpDownloader
-dontwarn com.squareup.picasso.OkHttp3Downloader
-dontnote okhttp3.internal.Platform
-dontnote com.squareup.okhttp.internal.Platform

-dontwarn org.joda.convert.**

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-dontwarn androidx.annotation.**