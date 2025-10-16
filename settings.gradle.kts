pluginManagement {
  val loomVersion = settings.extra["loom_version"] as String

  plugins {
    id("fabric-loom") version loomVersion
    id("com.modrinth.minotaur") version "latest.release"
  }

  repositories {
    maven("https://maven.fabricmc.net") {
      name = "FabricMC"
    }
    gradlePluginPortal()
  }
}
