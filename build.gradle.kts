plugins {
    kotlin("jvm") version "2.3.10"
}

group = "ru.pht.vtl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Java-байткод
    implementation("org.ow2.asm:asm:${project.properties["asm_version"]}")
    implementation("org.ow2.asm:asm-util:${project.properties["asm_version"]}")
    implementation("org.ow2.asm:asm-tree:${project.properties["asm_version"]}")
    implementation("org.ow2.asm:asm-commons:${project.properties["asm_version"]}")
    // Версии
    implementation("io.github.z4kn4fein:semver:${project.properties["semver_version"]}")
    // Тест
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}