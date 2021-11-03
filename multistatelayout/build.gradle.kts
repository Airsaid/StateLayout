plugins {
  id("com.android.library")
  id("maven-publish")
  id("signing")
}

val publishId = "multistatelayout"
val publishVersion = "1.0.0"
val isReleaseBuild = !publishVersion.endsWith("-SNAPSHOT")

android {
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
  testImplementation(Libs.Test.JUNIT)
  androidTestImplementation(Libs.Test.ANDROIDX_JUNIT)
  androidTestImplementation(Libs.Test.ESPRESSO)
  implementation(Libs.AndroidX.ANNOTATION)
}

afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("release") {
        group = "com.airsaid"
        artifactId = publishId
        version = publishVersion

        from(components.getByName("release"))

        pom {
          name.set(publishId)
          description.set("A customize multiple state layout for Android.")
          url.set("https://github.com/Airsaid/MultiStateLayout")

          licenses {
            license {
              name.set("The Apache License, Version 2.0")
              url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
          }

          developers {
            developer {
              id.set(property("pom.id") as String)
              name.set(property("pom.name") as String)
              email.set(property("pom.email") as String)
            }
          }

          scm {
            connection.set("scm:git:git://github.com/Airsaid/MultiStateLayout.git")
            developerConnection.set("scm:git:ssh://git@github.com/Airsaid/MultiStateLayout.git")
            url.set("https://github.com/Airsaid/MultiStateLayout")
          }
        }
      }
    }

    repositories {
      maven {
        setUrl(if (isReleaseBuild) "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        else "https://s01.oss.sonatype.org/content/repositories/snapshots/")

        credentials {
          username = if (hasProperty("ossrhUsername")) properties["ossrhUsername"] as String else ""
          password = if (hasProperty("ossrhPassword")) properties["ossrhPassword"] as String else ""
        }
      }
    }
  }
}

signing {
  sign(publishing.publications)
}