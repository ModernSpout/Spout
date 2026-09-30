plugins {
    id("net.neoforged.moddev")
}

base {
    archivesName = providers.gradleProperty("archives_base_name").map { "$it-neoforge" }
}

repositories {
    mavenCentral()
}

neoForge {
    version = providers.gradleProperty("neoforge_version").get()

    accessTransformers {
        file("src/main/resources/META-INF/accesstransformer.cfg")
    }

    mods {
        register("spoutcraft") {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        register("client") {
            jvmArgument("-XX:+IgnoreUnrecognizedVMOptions")
            jvmArgument("-XX:+AllowEnhancedClassRedefinition")
            systemProperty("mixin.debug.verbose", "true")
            systemProperty("mixin.debug.export", "true")
            systemProperty("neoforge.enabledGameTestNamespaces", "spoutcraft")
            client()
        }
    }
}

tasks.processResources {
    val version = version
    filesMatching("META-INF/neoforge.mods.toml") {
        expand("version" to version)
    }
}
