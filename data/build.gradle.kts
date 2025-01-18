plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinKapt)

}

android {
    namespace = "com.williamfq.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Configuración de Room para exportar esquemas
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Módulos internos
    implementation(project(":core"))
    implementation(project(":domain"))

    // Room (Base de datos)
    implementation(libs.androidxRoomRuntime)
    implementation(libs.androidxRoomKtx)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.common)
    implementation(libs.playServicesLocation)
    implementation(libs.firebase.inappmessaging.ktx)
    implementation(libs.androidx.databinding.adapters)
    implementation(libs.firebase.database)
    ksp(libs.androidxRoomCompiler)
    implementation(libs.androidxRoomMigration)

    // Firebase (Firestore)
    implementation(libs.firebaseFirestoreKtx)

    // Hilt (Inyección de dependencias)
    implementation(libs.hiltAndroid)
    ksp(libs.hiltCompiler)

    // Networking (Retrofit y OkHttp)
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    implementation(libs.okhttp)

    // ML Kit (Detección facial)
    implementation(libs.mlkitFaceDetection)

    // Seguridad de base de datos
    implementation(libs.sqlcipher.android)
    implementation(libs.androidx.sqlite)

    // JSON avanzado
    implementation(libs.gson)

    // Arquitectura (LiveData y ViewModel)
    implementation(libs.androidxLifecycleViewModelKtx)
    implementation(libs.androidxLifecycleLiveDataKtx)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxTestExtJunit)
    androidTestImplementation(libs.espressoCore)

    // UI y Utilidades
    implementation(libs.easyPermissions)
    implementation(libs.chucker)
    implementation(libs.timber)
    implementation(libs.coil)

    implementation(libs.androidxRoomMigration)


    implementation(libs.bundles.serialization)

    implementation ("com.google.protobuf:protobuf-javalite:3.25.5")
    implementation ("com.google.android.material:material:1.12.0")


}

tasks.register<Copy>("copyRoomSchemas") {
    from(fileTree(layout.buildDirectory.dir("schemas")))
    into("$projectDir/schemas")
    doLast {
        println("Room schema files have been copied to $projectDir/schemas")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
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
