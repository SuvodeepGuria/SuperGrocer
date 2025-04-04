plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
//    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.suvodeep.supergrocer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.suvodeep.supergrocer"
        minSdk = 30
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
//repositories {
//    mavenCentral()
//}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation ("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //implementation("com.squareup.retrofit2:converter-scalars:2.11.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("com.airbnb.android:lottie-compose:6.1.0")

    implementation("androidx.datastore:datastore-preferences:1.1.3")

    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.firebase:firebase-auth")

    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.android.play:integrity:1.3.0")
    implementation("com.google.firebase:firebase-database")

//    implementation ("com.google.firebase:firebase-firestore")

//    implementation("com.google.firebase:firebase-auth:23.2.0")

    implementation("com.razorpay:checkout:1.6.40")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")


}

// https://training-uploads.internshala.com/android/grocery_delivery_app/items.json