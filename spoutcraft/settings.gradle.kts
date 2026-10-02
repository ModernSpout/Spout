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
        maven {
            name = "Sponge"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
        maven {
            name = "Forge"
            url = uri("https://maven.minecraftforge.net/")
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("net.fabricmc.fabric-loom") version providers.gradleProperty("loom_version")
        id("net.neoforged.moddev") version providers.gradleProperty("moddevgradle_version")
        id("net.minecraftforge.gradle") version providers.gradleProperty("forgegradle_version")
    }
}

rootProject.name = "spoutcraft"

include("fabric")
include("neoforge")
include("forge")
