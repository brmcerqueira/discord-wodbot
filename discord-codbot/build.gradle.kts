import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "brmcerqueira"
version = "1.0-SNAPSHOT"

val ktorVersion = "1.2.1"

plugins {
    java
    application
    kotlin("jvm") version "1.3.31"
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-jackson:$ktorVersion")
    compile("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.discord4j:discord4j-core:3.0.6")
}

application {
    mainClassName = "com.brmcerqueira.discord.wodbot.MainKt"
    applicationDefaultJvmArgs = listOf("-XX:+UseContainerSupport")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("fat")
    manifest {
        attributes(mapOf("Main-Verticle" to application.mainClassName))
    }
}