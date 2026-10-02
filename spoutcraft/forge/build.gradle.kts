plugins {
    id("net.minecraftforge.gradle")
}

sourceSets.main {
    java.srcDir("../common/forgelike/src/main/java")
    resources.srcDir("../common/forgelike/src/main/resources")
}

base {
    archivesName = providers.gradleProperty("archives_base_name").map { "$it-forge" }
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
val forgeVersion = providers.gradleProperty("forge_version").get()

repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    mavenCentral()
}

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:$minecraftVersion-$forgeVersion"))
}

minecraft {
    accessTransformers.from(rootProject.layout.projectDirectory.file("common/forgelike/src/main/resources/META-INF/accesstransformer.cfg"))

    // Default run configurations.
    // These can be tweaked, removed, or duplicated as needed.
    runs {
        // applies to all the run configs below
        register("client") {
            workingDir = layout.projectDirectory.dir("run")
            jvmArgs("-XX:+IgnoreUnrecognizedVMOptions", "-XX:+AllowEnhancedClassRedefinition")
            systemProperty("mixin.debug.verbose", "true")
            systemProperty("mixin.debug.export", "true")
            systemProperty("forge.enabledGameTestNamespaces", "spoutcraft")
            systemProperty("forge.logging.console.level", "debug")
            args("--username", "Dev")
        }
    }
}

tasks.processResources {
    val version = version
    filesMatching("META-INF/mods.toml") {
        expand("version" to version)
    }
}

// Lets Forge find the mixin configs when loading from the jar.
tasks.jar {
    manifest {
        attributes(
            "MixinConfigs" to listOf(
                "spoutcraft.spout.clientui.command.mixins.json",
                "spoutcraft.spout.clientui.resourcepack.loadingoverlay.mixins.json",
                "spoutcraft.spout.clientui.resourcepack.toast.mixins.json",
                "spoutcraft.spout.clientview.clientmod.protocol.mixins.json",
                "spoutcraft.spout.clientview.clientmod.registryidmapping.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.block.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.block.subtypes.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.blockentity.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.common.registry.codec.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.common.registry.temporarymodification.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.item.mixins.json",
                "spoutcraft.spout.gamecontent.datadriven.mixins.json"
            ).joinToString(",")
        )
    }
}

// Merge classes and resources into one directory per source set,
// because the Java module system expects a module in a single directory.
sourceSets.configureEach {
    val dir = layout.buildDirectory.dir("sourcesSets/$name")
    output.setResourcesDir(dir)
    java.destinationDirectory = dir
}
