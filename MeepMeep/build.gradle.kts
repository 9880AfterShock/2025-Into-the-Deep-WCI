//apply(from = "../build.common.gradle")
//apply(from = "../build.dependencies.gradle")
//apply(plugin = "org.jetbrains.kotlin.android")

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}
repositories {
    mavenCentral()
    mavenLocal()
    google()
    maven {
        url = uri("https://maven.brott.dev/")
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    //implementation(project(":FtcRobotController"))

    implementation("com.acmerobotics.roadrunner:ftc:0.1.14")
    implementation("com.acmerobotics.roadrunner:core:1.0.0")
    implementation("com.acmerobotics.roadrunner:actions:1.0.0")
    implementation("com.acmerobotics.dashboard:dashboard:0.4.16")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.acmerobotics.roadrunner:MeepMeep:0.1.6")
}