import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.lab3comp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab3comp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Rating bar
    implementation ("com.github.a914-gowtham:compose-ratingbar:1.3.4")

    val nav_version = "2.7.6"

    // Java language implementation
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.ui:ui-util:1.0.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.compose.material:material:1.5.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Room Local DB
    val room_version = "2.4.3"
    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.1")
    annotationProcessor ("androidx.room:room-compiler:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")


    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    val dagger_hilt_version = "2.40"
    val hilt_lifecycle_viewmodel_version = "1.0.0-alpha03"
    val hilt_compiler_version = "1.0.0"

    //Dagger-Hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.44.2")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
}