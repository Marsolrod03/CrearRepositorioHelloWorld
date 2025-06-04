plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.secretsGraglePlugin)
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    secrets{
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "default.properties"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

tasks.withType<Test>{
    useJUnitPlatform()
}

dependencies {
    implementation(project(":features:actors:domain"))
    implementation(project(":lib:common_data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.hilt)
    ksp(libs.hiltCompiler)
    implementation(libs.room)
    ksp(libs.roomCompiler)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
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