plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.bugtobiz"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.bugtobiz"
        minSdk = 33
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
}
