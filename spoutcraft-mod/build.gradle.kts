plugins {
    base
}

tasks.named("build") {
    dependsOn(":fabric:build")
    dependsOn(":neoforge:build")
}
