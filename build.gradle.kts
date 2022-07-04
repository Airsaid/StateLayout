// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
  alias(libs.plugins.android.application).apply(false)
  alias(libs.plugins.android.library).apply(false)
  alias(libs.plugins.kotlin.android).apply(false)
  alias(libs.plugins.vanniktech.maven.publish).apply(false)
}

subprojects {
  tasks.whenTaskAdded {
    if (name == "publish") {
      this.finalizedBy(rootProject.tasks.getByName("generateSampleApk"))
    }
  }
}

tasks.register<Copy>("generateSampleApk") {
  from(rootProject.layout.projectDirectory.file("app/build/outputs/apk/debug/app-debug.apk"))
  into(rootProject.layout.projectDirectory)
  rename { "sample.apk" }

  dependsOn(tasks.getByPath(":app:assembleDebug"))
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}