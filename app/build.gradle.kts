plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(27)
    buildToolsVersion("27.0.3")
    defaultConfig {
        applicationId = "com.justdoit.graffiti"
        minSdkVersion(18)
        targetSdkVersion(22)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.android.support:appcompat-v7:27.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.2.41")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.0.0")
    implementation("com.fpliu:Android-CustomDimen:1.0.0")
    implementation("com.google.code.gson:gson:2.8.0")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

}
