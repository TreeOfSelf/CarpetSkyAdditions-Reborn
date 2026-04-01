pluginManagement {
  plugins {
    id("net.fabricmc.fabric-loom") version "1.16.0-alpha.16"
    id("com.modrinth.minotaur") version "latest.release"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  }

  repositories {
    maven("https://maven.fabricmc.net") {
      name = "FabricMC"
    }
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention")
}
