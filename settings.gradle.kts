pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        gradlePluginPortal()
    }
}

rootProject.name = "trustedserver-26"

include("core", "core-server", "core-client", "fabric-26.3")
