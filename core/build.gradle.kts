plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}
apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.bona.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "API_KEY", "\"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\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material.v1120)
    ksp(libs.androidx.room.compiler)
    ksp(libs.room.ktx)

    // Data Store
    implementation(libs.androidx.datastore.preferences)

    //Encryption
    implementation(libs.net.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
}