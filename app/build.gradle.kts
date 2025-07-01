plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.cebolaolotofacil"
    // CORREÇÃO: Alinhado a compileSdk com a versão testada do AGP para estabilidade.
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cebolaolotofacil"
        minSdk = 26
        // CORREÇÃO: Alinhado a targetSdk com a compileSdk.
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.runtime:runtime")
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.splashscreen)

    // Testes de Unidade
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.11") // Para mocking
    testImplementation("app.cash.turbine:turbine:1.1.0") // Para testar Flows
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1") // Para testar Coroutines

    // Testes de Instrumentação (UI)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
}