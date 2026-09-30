import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import java.util.jar.Attributes

plugins {
    base
}

tasks.named("build") {
    dependsOn(":fabric:build")
    dependsOn(":neoforge:build")
}

val multiloaderJar by tasks.registering {
    group = "build"
    dependsOn(":fabric:jar", ":neoforge:jar")
    val fabricJar = project(":fabric").tasks.named<Jar>("jar")
    val neoforgeJar = project(":neoforge").tasks.named<Jar>("jar")
    val fabricFile = fabricJar.get().archiveFile.get().asFile
    val outputFile = layout.buildDirectory.file("libs/${fabricFile.name.replace("-fabric", "")}")
    outputs.file(outputFile)

    doLast {
        val fabricFile = fabricJar.get().archiveFile.get().asFile
        val neoforgeFile = neoforgeJar.get().archiveFile.get().asFile
        val output = outputFile.get().asFile

        fun readJar(file: File): Map<String, ByteArray> {
            JarFile(file).use { jar ->
                return jar.entries().asSequence()
                    .filter { !it.isDirectory }
                    .associate { entry ->
                        entry.name to jar.getInputStream(entry).use { it.readBytes() }
                    }
            }
        }

        fun readManifest(bytes: ByteArray): Manifest {
            return bytes.inputStream().use { Manifest(it) }
        }

        fun mergeAttributes(
            target: Attributes,
            source: Attributes,
            location: String
        ) {
            for ((key, sourceValue) in source) {
                val name = key as Attributes.Name
                val existingValue = target.getValue(name)
                if (existingValue == null) {
                    target[name] = sourceValue
                } else if (existingValue != sourceValue) {
                    throw GradleException(
                        "Conflicting manifest attribute in $location: " +
                            "${name}: \"$existingValue\" vs \"$sourceValue\""
                    )
                }
            }
        }

        fun mergeManifests(
            fabric: ByteArray,
            neoforge: ByteArray
        ): ByteArray {
            val fabricManifest = readManifest(fabric)
            val neoforgeManifest = readManifest(neoforge)
            // Merge the main attributes.
            mergeAttributes(
                fabricManifest.mainAttributes,
                neoforgeManifest.mainAttributes,
                "main attributes"
            )
            // Merge attributes belonging to named entries.
            for ((entryName, neoforgeAttributes) in neoforgeManifest.entries) {
                val fabricAttributes = fabricManifest.entries[entryName]
                if (fabricAttributes == null) {
                    fabricManifest.entries[entryName] = neoforgeAttributes
                } else {
                    mergeAttributes(
                        fabricAttributes,
                        neoforgeAttributes,
                        "entry \"$entryName\""
                    )
                }
            }
            return fabricManifest
                .let { manifest ->
                    java.io.ByteArrayOutputStream().use { output ->
                        manifest.write(output)
                        output.toByteArray()
                    }
                }
        }

        val fabricEntries = readJar(fabricFile)
        val neoforgeEntries = readJar(neoforgeFile)
        val manifestPath = "META-INF/MANIFEST.MF"

        // Merge MANIFEST.MF specially.
        val mergedManifest = mergeManifests(
            fabricEntries.getValue(manifestPath),
            neoforgeEntries.getValue(manifestPath)
        )

        // Check all other duplicate files.
        val conflicts = fabricEntries.keys
            .intersect(neoforgeEntries.keys)
            .filter { it != manifestPath }
            .filter { name ->
                !fabricEntries.getValue(name).contentEquals(
                    neoforgeEntries.getValue(name)!!
                )
            }
            .sorted()
        if (conflicts.isNotEmpty()) {
            throw GradleException(
                buildString {
                    appendLine("Cannot create multiloader JAR: conflicting files found:")
                    conflicts.forEach { appendLine("  $it") }
                }
            )
        }

        output.parentFile.mkdirs()
        JarOutputStream(output.outputStream().buffered()).use { jar ->
            val allEntries = (fabricEntries.keys + neoforgeEntries.keys)
                .filter { it != manifestPath }
                .sorted()
            for (name in allEntries) {
                val contents = fabricEntries[name]
                    ?: neoforgeEntries.getValue(name)
                jar.putNextEntry(JarEntry(name))
                jar.write(contents)
                jar.closeEntry()
            }
            // Write the merged manifest.
            jar.putNextEntry(JarEntry(manifestPath))
            jar.write(mergedManifest)
            jar.closeEntry()
        }

        logger.lifecycle("Created multiloader JAR: ${output.absolutePath}")
    }
}

tasks.register<Exec>("recompressJar") {
    group = "build"
    dependsOn(multiloaderJar)
    val input = multiloaderJar.get().outputs.files.first()
    commandLine(
        "sh", "-c",
        "advzip -z -4 ${input.absolutePath}"
    )
}
