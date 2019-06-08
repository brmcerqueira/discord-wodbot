group = "brmcerqueira"
version = "1.0-SNAPSHOT"

val ktorVersion = "1.2.1"

plugins {
    java
    application
    kotlin("jvm") version "1.3.31"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.31")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("com.discord4j:discord4j-core:3.0.6")
}

application {
    mainClassName = "com.brmcerqueira.discord.codbot.MainKt"
}