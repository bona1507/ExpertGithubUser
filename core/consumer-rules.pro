-keep,includedescriptorclasses class net.sqlcipher.** { *; }

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn com.bona.core.data.remote.ItemState$Error
-dontwarn com.bona.core.data.remote.ItemState$Loading
-dontwarn com.bona.core.data.remote.ItemState$Success
-dontwarn com.bona.core.data.remote.ItemState
-dontwarn com.bona.core.di.CoreModuleKt
-dontwarn com.bona.core.ui.ItemUserAdapter
-dontwarn com.bona.core.utils.ItemUser
-dontwarn com.bona.core.utils.ItemUserInteractor
-dontwarn com.bona.core.utils.ItemUserRepository
-dontwarn com.bona.core.utils.ItemUserUseCase

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
 @com.google.gson.annotations.SerializedName <fields>;
}