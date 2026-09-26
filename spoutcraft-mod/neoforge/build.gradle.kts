plugins {
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
        register("spoutcraft_mod") {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main {
    java.srcDir("../common/src/client/java")
    java.srcDir("../../common-src/src/main/java")
    java.srcDir("../../common-minecraft-src/src/main/java")
    java.srcDir("../../common-fabric-src/src/main/java")
    resources.srcDir("../common/src/client/resources")
    resources.srcDir("../../common-src/src/main/resources")
    resources.srcDir("../../common-minecraft-src/src/main/resources")
    resources.srcDir("../../common-fabric-src/src/main/resources")
}

tasks.processResources {
    val version = project.version
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

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
}
