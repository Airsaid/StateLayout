// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
  }
  dependencies {
    classpath("com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
    classpath("com.github.momodae.AndroidSampleLibrary:plugin:${Versions.SAMPLE}")
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
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