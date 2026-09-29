plugins {
    id("net.fabricmc.fabric-loom")
    `maven-publish`
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

base {
    archivesName = providers.gradleProperty("archives_base_name").map { "$it-fabric" }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("spoutcraft") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }

    accessWidenerPath = file("src/main/resources/spoutcraft.classtweaker")
}

sourceSets.getByName("client") {
    java.srcDir("../../common/src/main/java")
    java.srcDir("../../common/minecraft/src/main/java")
    java.srcDir("../common/src/main/java")
    resources.srcDir("../../common/src/main/resources")
    resources.srcDir("../../common/minecraft/src/main/resources")
    resources.srcDir("../common/src/main/resources")
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")

    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")
}

tasks.processResources {
    val version = version
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
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
