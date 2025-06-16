plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.series.ui"
    compileSdk = 35


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":features:series:domain"))
    implementation(project(":lib:common_ui"))
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.hilt)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.compose)


    implementation(libs.composeMaterial)
    implementation(libs.composeTooling)
    implementation(libs.coilCompose)
    implementation(libs.coil)
    implementation(libs.lottieCompose)
    implementation(libs.lottie)
    implementation(platform(libs.composeBom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation(libs.navigateCompose)
}