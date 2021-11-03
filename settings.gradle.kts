dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    jcenter() // Warning: this repository is going to shut down soon
    maven { url = uri("https://jitpack.io") }
  }
}
rootProject.name = "MultiStateLayout"
include(":app")
include(":multistatelayout")
