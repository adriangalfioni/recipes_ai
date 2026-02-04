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

-keep class io.ktor.** { *; }
-keep class com.google.ai.client.generativeai.** { *; }
-dontwarn io.ktor.**

-dontwarn org.junit.jupiter.api.extension.AfterAllCallback
-dontwarn org.junit.jupiter.api.extension.AfterEachCallback
-dontwarn org.junit.jupiter.api.extension.ParameterResolver
-dontwarn org.junit.jupiter.api.extension.TestInstancePostProcessor

# Required for Gson TypeToken to work
-keepattributes Signature
-keepattributes *Annotation*
# Keep anonymous subclasses of TypeToken (CRITICAL)
-keep class * extends com.google.gson.reflect.TypeToken

# Keep Gson core classes
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keepattributes Signature
-keepattributes RuntimeVisibleAnnotations
-keep class com.google.gson.stream.** { *; }

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep LocalIngredient completely (no optimization)
-keep class agalfioni.recipesai.core.domain.models.LocalIngredient {
    <fields>;
    <init>(...);
}

# Crashlytics
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.