@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.vanniktech.maven.publish)
}

android {
  namespace = "com.airsaid.statelayout"
  compileSdk = Versions.App.COMPILE_SDK

  defaultConfig {
    minSdk = Versions.App.MIN_SDK
    targetSdk = Versions.App.TARGET_SDK
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

dependencies {
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso)
  implementation(libs.androidx.annotation)
}