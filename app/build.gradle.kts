plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "br.com.arsgapps.matrizes"
    compileSdk = 37

    defaultConfig {
        applicationId = "br.com.arsgapps.matrizes"
        minSdk = 23
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-project.txt"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        checkTestSources = false
        abortOnError = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
