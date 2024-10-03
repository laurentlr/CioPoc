import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("com.codingfeline.buildkonfig")
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        //TODO add same dependencies as in core:analytics:implementation
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.1")
            api("com.segment.analytics.kotlin:core:1.16.3")
            implementation("io.customer.android:datapipelines:4.2.0")
            implementation("io.customer.android:messaging-push-fcm:4.2.0")
            implementation("io.customer.android:messaging-in-app:4.2.0")
        }
    }
}

android {
    namespace = "cio.poc"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

buildkonfig {
    packageName = "com.example.app"
    // objectName = "YourAwesomeConfig"
    // exposeObjectWithName = "YourAwesomePublicConfig"

    defaultConfigs {
        buildConfigField(STRING, "cdpApiKey", "f6ca30bf18db5a08dd6a")
    }
}
