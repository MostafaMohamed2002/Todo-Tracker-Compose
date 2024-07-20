// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false

  id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
  //compose compiler
  alias(libs.plugins.compose.compiler) apply false
  //hilt
  alias(libs.plugins.hilt.gradle.plugin) apply false
}

