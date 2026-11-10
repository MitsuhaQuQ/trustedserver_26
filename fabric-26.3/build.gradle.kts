plugins {
    id("net.fabricmc.fabric-loom") version "1.19.4"
}

base {
    archivesName = "trustedserver-fabric-26.3"
}

repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    "minecraft"("com.mojang:minecraft:26.3")
    "implementation"("net.fabricmc:fabric-loader:0.20.1")
    "implementation"("net.fabricmc.fabric-api:fabric-api:0.165.2+26.3")
    "compileOnly"("com.terraformersmc:modmenu:22.0.0-alpha.3") {
        isTransitive = false
    }

    for (module in listOf(":core", ":core-client", ":core-server")) {
        "implementation"(project(module))
        "include"(project(module))
    }
    "include"("org.bouncycastle:bcprov-jdk18on:1.78.1")
    "include"("org.xerial:sqlite-jdbc:3.46.1.0")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_dependency" to "~26.3",
            "java_dependency" to ">=25"
        )
    }
    filesMatching("trustedserver.mixins.json") {
        expand("java_compatibility" to "JAVA_25")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
    options.compilerArgs.removeAll(listOf("-Xlint:all", "-Werror"))
}
