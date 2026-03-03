import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    // AGP 9.0 では com.android.library + kotlin.multiplatform の組み合わせは不可。
    // KMP 向け専用プラグインを使う。
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    // com.android.kotlin.multiplatform.library では、android {} を kotlin {} の中に書く。
    // 型は KotlinMultiplatformAndroidLibraryTarget。
    android {
        namespace = "com.example.myapp.shared"
        compileSdk = 36  // release() ラッパーは不要（plain int）
        minSdk = 24      // defaultConfig {} は不要、直接プロパティで指定

        compilerOptions {
            // compileOptions {} は廃止。Kotlin の compilerOptions {} で JVM ターゲットを指定。
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // iOS サポートは将来追加
    // iosX64()
    // iosArm64()
    // iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
            implementation(libs.multiplatform.settings)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.kotlinx.coroutines.android)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
