plugins {
    java
    application
    kotlin("jvm") version "1.3.21"
}

group = "brmcerqueira"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.discord4j:discord4j-core:3.0.6")
}

application {
    mainClassName = "com.brmcerqueira.discord.codbot.Main"
}