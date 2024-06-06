plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "gal.cntg.cntgapp" // id de la aplicación. Carpeta raíz.
    compileSdk = 34  // Versión con la que estamos compilando

    defaultConfig {
        applicationId = "gal.cntg.cntgapp"
        minSdk = 27 // Versión minima v8
        targetSdk = 34 // Versión con la que hacemos las pruebas
        versionCode = 1 // número natural por cada versión del código por la que vayamos
        versionName = "1.0" // String que puede ser cualquier cosa.

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

    //con esto, habilitamos la vinculación de vistas automáticas entre XML y Activity
    // (evitamos el findViewById)
    buildFeatures {
        viewBinding = true
    }
}

// Dependencias. Librerías de terceros o son extras.
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.picasso)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database.ktx)  // <-- librería para maps.
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries
}