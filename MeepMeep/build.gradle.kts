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
    sourceCompatibility = JavaVersion.VERSION_18 // both use 17, 18 just works? unsupported officall and 17 works too
    targetCompatibility = JavaVersion.VERSION_18 // both use 17, 18 just works? unsupported officall and 17 works too
}
dependencies {
    //implementation(project(":FtcRobotController")) //both commented out
    //implementation("com.acmerobotics.dashboard:dashboard:0.4.16")
    implementation("com.acmerobotics.roadrunner:MeepMeep:0.1.6")
}