plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.secretsGraglePlugin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.crearrepositorio"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.crearrepositorio"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "default.properties"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
}

tasks.withType<Test>{
    useJUnitPlatform()
}

dependencies {
    implementation(project(":features:actors:data"))
    implementation(project(":features:actors:ui"))
    implementation(project(":features:actors:domain"))
    implementation(project(":lib:common"))
    implementation(project(":lib:common_ui"))
    implementation(project(":lib:common_data"))
    implementation(libs.lottie)
    implementation(libs.hilt)
    ksp(libs.ksp)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.coil)
    implementation(libs.androidx.lifecycle.runtime.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.compose)
    implementation(libs.composeMaterial)
    implementation(libs.composeTooling)
    implementation(libs.coilCompose)
    implementation(libs.lottieCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.preference)
    implementation(libs.room)
    ksp(libs.roomCompiler)
    implementation(libs.hiltWorker)
    ksp(libs.hiltCompilerWorker)
    implementation(libs.runtime.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.turbine)
    testImplementation(libs.kotlin.jUnit5Test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}