
class Versions(properties: ExtraPropertiesExtension) {
  val mod = properties["mod_version"] as String
  val java = JavaVersion.toVersion(properties["java_version"] as String)
  val minecraft = properties["minecraft_version"] as String
  val minecraftCompatibility = properties["compatible_minecraft_versions"] as String
  val project = "$minecraft-$mod"
  val fabricLoader = properties["loader_version"] as String
  val fabricApi = properties["fabric_version"] as String
  val carpet = properties["carpet_core_version"] as String
  val clothConfig = properties["cloth_config_version"] as String
  val modmenu = properties["modmenu_version"] as String
}

plugins {
  id("net.fabricmc.fabric-loom")
  id("com.modrinth.minotaur") version "latest.release"
}

val versions = Versions(project.extra)
val modId = project.extra["mod_id"] as String
version = versions.project

repositories {
  maven("https://masa.dy.fi/maven") {
    content {
      includeGroup("carpet")
    }
  }
  maven("https://maven.shedaniel.me") {
    content {
      includeGroup("me.shedaniel.cloth")
    }
  }
  maven("https://maven.terraformersmc.com/releases/") {
    content {
      includeGroup("com.terraformersmc")
    }
  }
}

dependencies {
  minecraft("com.mojang", "minecraft", versions.minecraft)
  implementation("net.fabricmc", "fabric-loader", versions.fabricLoader)
  implementation("carpet", "fabric-carpet", versions.carpet)

  // Add fabric-api
  implementation("net.fabricmc.fabric-api", "fabric-api", versions.fabricApi)

  arrayOf(
    "fabric-object-builder-api-v1",
    "fabric-registry-sync-v0",
    "fabric-resource-loader-v0",
    "fabric-transitive-access-wideners-v1",
    "fabric-lifecycle-events-v1",
  ).forEach { implementation(fabricApi.module(it, versions.fabricApi)) }

  implementation("me.shedaniel.cloth", "cloth-config-fabric", versions.clothConfig) {
    exclude("net.fabricmc.fabric-api")
  }
  implementation("com.terraformersmc", "modmenu", versions.modmenu)
}

base {
  archivesName.set(modId)
}

tasks {
  processResources {
    val templateContext = mapOf(
      "name" to project.extra["mod_name"],
      "version" to version,
      "mc_compatibility" to versions.minecraftCompatibility,
    )

    inputs.properties(templateContext)
    filesMatching("fabric.mod.json") {
      expand(templateContext)
    }
  }

  withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(versions.java.majorVersion))
    }
    withSourcesJar()
  }

  jar {
    // Embed license in output jar
    from("LICENSE") {
      rename { "${it}_${base.archivesName.get()}" }
    }
  }

  withType<AbstractArchiveTask> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
  }
}

loom {
  accessWidenerPath.set(file("src/main/resources/$modId.accesswidener"))
}

tasks.assemble.get().dependsOn(
  tasks.register<Zip>("zipTranslationPack") {
    description = "Zips the Translations resource pack"
    val tempDir = layout.buildDirectory.dir("translations-pack")
    copy {
      into(tempDir)
      from("translations-pack")

      into("assets/$modId/lang") {
        from("src/main/resources/assets/$modId/lang") {
          exclude("en_us.json")
        }
      }
    }
    archiveClassifier.set("translations")
    group = "build"
    from(tempDir)
    destinationDirectory.set(base.distsDirectory)
  },
  tasks.register<Zip>("zipSkyBlockDatapack") {
    description = "Zips the SkyBlock datapack"
    val tempDir = layout.buildDirectory.dir("datapack/skyblock")
    copy {
      into(tempDir)
      from("src/main/resources/resourcepacks/skyblock")
    }
    archiveClassifier.set("datapack")
    group = "build"
    from(tempDir)
    destinationDirectory.set(base.distsDirectory)
  },
)


