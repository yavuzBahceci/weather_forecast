package dependencies

object Build {
    val build_tools = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5}"
    val hilt_gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}