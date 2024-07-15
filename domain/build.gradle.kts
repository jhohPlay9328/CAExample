plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jh.oh.play.domain"
    compileSdk = rootProject.extra["compileSdk_version"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk_version"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_18.toString()
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}