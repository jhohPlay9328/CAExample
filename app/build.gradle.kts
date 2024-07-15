plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
}
android {
    namespace = "com.jh.oh.play.caexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jh.oh.play.caexample"
        minSdk = rootProject.extra["minSdk_version"] as Int
        targetSdk = rootProject.extra["targetSdk_version"] as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-kakao-rules.pro",
                "proguard-naver-rules.pro"
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.ui)
    ksp(libs.androidx.databinding.compiler)

    implementation(libs.bundles.sns.login)

    implementation(libs.bundles.network.app)

    implementation(libs.androidx.ktx)
    implementation(libs.material)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.images)

    implementation(libs.colorpickerview)
}