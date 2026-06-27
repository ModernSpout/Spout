pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "NeoForge"
            url = uri("https://maven.neoforged.net/releases/")
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("net.fabricmc.fabric-loom") version providers.gradleProperty("loom_version")
        id("net.neoforged.moddev") version providers.gradleProperty("moddevgradle_version")
    }
}

rootProject.name = "spoutcraft-mod"

include("fabric")
include("neoforge")
