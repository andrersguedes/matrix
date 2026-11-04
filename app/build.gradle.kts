plugins {
    id("com.android.application")
}

android {
    namespace = "br.edu.fatecgarca.matrizes"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.edu.fatecgarca.matrizes"
        minSdk = 21
        targetSdk = 34
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
    implementation("androidx.core:core-ktx:1.10.1")
}
