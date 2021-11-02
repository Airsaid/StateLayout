/**
 * @author airsaid
 */
object Libs {
  object AndroidX {
    const val KTX = "androidx.core:core-ktx:1.3.2"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.2.0"
    const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.1.0"

    const val ANNOTATION = "androidx.annotation:annotation:1.1.0"

    const val ACTIVITY = "androidx.activity:activity:${Versions.ACTIVITY}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY}"

    const val FRAGMENT = "androidx.fragment:fragment:${Versions.FRAGMENT}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
    const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Versions.FRAGMENT}"

    const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    const val DRAWERLAYOUT = "androidx.drawerlayout:drawerlayout:1.0.0"
    const val PAGING = "androidx.paging:paging-runtime:3.0.0-alpha04"

    const val LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common:${Versions.LIFECYCLE}"
    const val LIFECYCLE_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_PROCESS = "androidx.lifecycle:lifecycle-process:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"

    const val MEDIAROUTER = "androidx.mediarouter:mediarouter:1.2.2"
  }

  object Test {
    const val JUNIT = "junit:junit:4.13.2"
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.2"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.3.0"
  }

  const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
  const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
  const val MATERIAL = "com.google.android.material:material:1.3.0"

  const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
  const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
  const val RETROFIT_ADAPTER_RXJAVA = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT}"

  const val GSON = "com.google.code.gson:gson:2.8.6"

  const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
  const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"

  const val SAMPLE = "com.github.momodae.AndroidSampleLibrary:library:${Versions.SAMPLE}"
  const val MMKV = "com.tencent:mmkv-static:1.1.2"

  const val AROUTER = "com.alibaba:arouter-api:1.5.0"
  const val AROUTER_COMPILER = "com.alibaba:arouter-compiler:1.2.2"

  const val TIMBER = "com.jakewharton.timber:timber:4.7.1"
  const val LEAKCANARY = "com.squareup.leakcanary:leakcanary-android:2.7"
}