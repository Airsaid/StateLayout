plugins {
  id("android.application")
}

android {
  defaultConfig {
    applicationId = "com.airsaid.statelayout.sample"
    versionCode = 1
    versionName = "1.0.0"
  }
}

dependencies {
  implementation(libs.androidx.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.android.material)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso)
  implementation(projects.statelayout)
//  implementation(libs.statelayout)
}