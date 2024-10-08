plugins {
  alias(libs.plugins.android.application)
  alias(
    libs
      .plugins
      .jetbrains
      .kotlin
      .android,
  )
  id("com.google.devtools.ksp")
  // compose compiler
  alias(libs.plugins.compose.compiler)
  // hilt
  alias(
    libs
      .plugins
      .hilt
      .gradle
      .plugin,
  )
  // ktlint
  id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

android {
  namespace = "com.mostafadevo.todotrackercompose"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.mostafadevo.todotrackercompose"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
      signingConfig = signingConfigs.getByName("debug")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
  }
}
ktlint {
  debug.set(true)
  verbose.set(true)
  android.set(true)
  outputToConsole.set(true)
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(
    libs
      .androidx
      .lifecycle
      .runtime
      .ktx,
  )
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(
    libs
      .androidx
      .ui
      .tooling
      .preview,
  )
  implementation(libs.androidx.material3)
  implementation(
    libs
      .ui
      .text
      .google
      .fonts,
  )
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(
    libs
      .androidx
      .ui
      .test
      .junit4,
  )
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(
    libs
      .androidx
      .ui
      .test
      .manifest,
  )
  // timber
  implementation(libs.timber)
  // room db
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)
  // coroutines
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)
  // hilt
  ksp(libs.hilt.android.compiler)
  implementation(
    libs
      .androidx
      .hilt
      .navigation
      .compose,
  )
  implementation(libs.hilt.android)
  // lifecycle
  implementation(
    libs
      .androidx
      .lifecycle
      .viewmodel
      .compose,
  )
  // compose live data
  implementation(libs.androidx.runtime.livedata)
  // material beta
  implementation(libs.material3)
  // compose navigation with enter , exit transitions 😳
  implementation("androidx.navigation:navigation-compose:2.7.7")
  // data store preferences
  implementation("androidx.datastore:datastore-preferences:1.1.1")
}
ksp {
  arg("dagger.incremental", "true")
}
