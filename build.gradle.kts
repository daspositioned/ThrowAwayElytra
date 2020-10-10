import javax.swing.plaf.metal.MetalIconFactory

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "net.seliba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(kotlin("stdlib"))
    shadow("org.spigotmc:spigot-api:1.16.3-R0.1-SNAPSHOT")
    shadow("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
}

configurations {

}