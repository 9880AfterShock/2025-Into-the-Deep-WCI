plugins {
    id("java-library")
    id("kotlin")
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
    //implementation("com.acmerobotics.dashboard:dashboard:0.4.16")
    implementation("com.acmerobotics.roadrunner:MeepMeep:0.1.6")
}