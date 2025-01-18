plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.williamfq.domain"
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
    implementation(project(":core"))
    implementation(libs.dagger)
    implementation(libs.hiltAndroid)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.firebase.inappmessaging.ktx)
    ksp(libs.daggerCompiler)
    ksp(libs.hiltCompiler)

    implementation(libs.protobuf.javalite)

    implementation ("com.google.protobuf:protobuf-javalite:3.25.5")

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