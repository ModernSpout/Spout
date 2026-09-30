plugins {
    id("net.fabricmc.fabric-loom")
}

base {
    archivesName = providers.gradleProperty("archives_base_name").map { "$it-fabric" }
}

loom {
    mods {
        register("spoutcraft") {
            sourceSet(sourceSets.main.get())
        }
    }

    accessWidenerPath = file("src/main/resources/spoutcraft.classtweaker")
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")

    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")
}

tasks.processResources {
    val version = version
    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}
