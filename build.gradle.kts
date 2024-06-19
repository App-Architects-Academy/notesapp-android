
buildscript {
    dependencies {
        val nav_version = "2.8.0-beta03"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
    repositories {
        google()
        mavenCentral()
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.android.gradle.plugin) apply false
    alias(libs.plugins.ksp) apply false
}