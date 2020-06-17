plugins {
    application
    kotlin("jvm") version "1.3.72"
//    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "xyz.vadviktor"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("${group}.ScrapeKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:3.141.59")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
