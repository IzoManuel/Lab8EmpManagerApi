plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.israel.israellab8empmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.israel.israellab8empmanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Loop J dependency, To be ued in accessing our API in Android
    implementation("com.loopj.android:android-async-http:1.4.9")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.activity:activity:1.7.0")

    // Coroutines to make the HTTP requests asynchronous(In the background thread)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    // Gson to convert raw JSON to pretty JSON
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation ("com.squareup.retrofit:converter-gson:2.0.0-beta2")
}