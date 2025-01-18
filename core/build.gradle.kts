plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)

}

android {
    namespace = "com.williamfq.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxRoomRuntime)
    implementation(libs.androidxRoomKtx)
    testImplementation(libs.testng)
    testImplementation(libs.junit.junit)
    ksp(libs.androidxRoomCompiler)
    implementation(libs.mlkitFaceDetection)
    implementation(libs.playServicesMaps)
    implementation(libs.firebaseFirestoreKtx)
    implementation(libs.playServicesLocation)
    implementation(libs.dagger)
    implementation(libs.hiltAndroid)
    ksp(libs.daggerCompiler)
    ksp(libs.hiltCompiler)
    implementation(libs.protobuf.javalite)
    implementation ("com.google.android.material:material:1.12.0")

}


ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
    // Añade argumentos adicionales para Hilt si es necesario
}

kotlin {
    sourceSets {
        getByName("main") {
            kotlin.srcDirs("build/generated/ksp/main/kotlin")
        }
    }

    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}