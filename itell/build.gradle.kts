plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.hassanwasfy.itell"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        compileSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11 // Java 11 for broader compatibility
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11" // Match Java version
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.hassanwasfy"
            artifactId = "itell"
            version = "2.0.5"
        }
    }
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.measurement.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.google.firebase:firebase-analytics-ktx:22.1.2")
}
