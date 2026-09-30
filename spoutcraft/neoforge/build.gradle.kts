plugins {
    id("java-library")
    id("net.neoforged.moddev")
    `maven-publish`
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

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

sourceSets.main {
    java.srcDir("../../common/src/main/java")
    java.srcDir("../../common/minecraft/src/main/java")
    java.srcDir("../common/src/main/java")
    resources.srcDir("../../common/src/main/resources")
    resources.srcDir("../../common/minecraft/src/main/resources")
    resources.srcDir("../common/src/main/resources")
}

tasks.processResources {
    val version = version
    inputs.property("version", version)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand("version" to version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    val archivesName = base.archivesName
    val projectName = project.name
    inputs.property("archivesName", archivesName)
    inputs.property("projectName", projectName)

    from(rootProject.file("../LICENSE.md")) {
        rename { "${it}_${projectName}" }
    }
}

tasks.register<Exec>("recompressJar") {
    group = "build"
    dependsOn(tasks.jar)
    val input = tasks.jar.get().archiveFile.get().asFile
    commandLine(
        "sh", "-c",
        "advzip -z -4 ${input.absolutePath}"
    )
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
}
