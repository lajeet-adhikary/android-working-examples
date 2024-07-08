plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbranins.kotlin.kapt)
    alias(libs.plugins.hilt.android.plugin)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.lajeet.practicecurrencyexchange"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lajeet.practicecurrencyexchange"
        minSdk = 24
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
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.vm)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.logging.interceptor)

    //Networking + Coroutines
    implementation(libs.coroutines)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    //Background Work
    implementation(libs.work.manager)

    //Compose + support
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.preview.support)
    debugImplementation(libs.compose.preview.support.debug)
    implementation(libs.compose.activity)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.livedata)
    implementation(libs.compose.nav)
    implementation(libs.compose.hilt.nav)

    //Room
    implementation(libs.room.db)
    kapt(libs.room.db.compiler)
    implementation(libs.room.ktx)
    kapt(libs.room.ktx.compiler)
}