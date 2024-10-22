@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dreamsoftware.nimbustv"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.dreamsoftware.nimbustv"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    // Core Android Libraries
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.tv.material)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.constraint.layout)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.material.icons.extended)

    // Jetpack Compose
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.ui.tooling)
    implementation(libs.fudge.tv.compose)

    // SplashScreen
    implementation(libs.androidx.core.splashscreen)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Media3 for Media Playback
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    // JSON Parsing
    implementation(libs.kotlinx.serialization)
    implementation(libs.moshi.kotlin)

    // Work Manager
    implementation(libs.work.manager)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler.work)

    // Baseline Profile Installer
    implementation(libs.androidx.profileinstaller)
}