plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.hassanwasfy.itell"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.hassanwasfy"
            artifactId = "iTell"
            version = "1.0.0"

            pom {
                name.set("iTell")
                description.set("A lightweight library for tracking the current screen in Android apps.")
                url.set("https://github.com/hassanwasfy/iTell")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("hassanwasfy")
                        name.set("Hassan Wasfy")
                        email.set("hassanwasfy7@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/hassanwasfy/iTell.git")
                    developerConnection.set("scm:git:ssh://github.com:hassanwasfy/iTell.git")
                    url.set("https://github.com/hassanwasfy/iTell")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
