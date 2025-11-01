[![Modrinth](https://img.shields.io/modrinth/v/Hi4jcyg4?style=flat-square&logo=modrinth&label=Modrinth)](https://modrinth.com/mod/carpetskyadditions-reborn)

<img width="128" height="128" alt="image" src="https://github.com/user-attachments/assets/5e8be161-1902-48cb-97b8-8ec7c4192789" />


# Carpet Sky Additions Reborn

Carpet Sky Additions Reborn is a mod based on CarpetSkyAdditions
[View the original mod here](https://github.com/jsorrell/CarpetSkyAdditions/)

## Try the mod on sky.hardcoreanarchy.gay

Carpet Sky Additions is a module for [fabric-carpet](https://github.com/gnembon/fabric-carpet)
originally based on [skyrising/skyblock](https://github.com/skyrising/skyblock).

This mod aims to provide an expert-level SkyBlock style gameplay that depends on players' knowledge of Minecraft
mechanics.
In some cases, outside tools such as [Chunkbase](https://www.chunkbase.com/)
or [MiniHUD](https://www.curseforge.com/minecraft/mc-mods/minihud) will be helpful.
Usage of these is encouraged.
Sometimes extended grinding or AFKing will be required for progression.
Unless SkyBlock world generation is chosen or features are specifically enabled, the mod will do nothing.
This means SkyBlock and Non-SkyBlock worlds can be switched between easily without restarting the
client.

## Installation

To create a new SkyBlock world, choose `World Type: SkyBlock` and enable the datapack `"carpetskyadditions/skyblock"`.

If you want a harder challenge, enable the datapack `"carpetskyadditions/skyblock_acacia"` to start with an Acacia
tree instead of an Oak tree.

If you want an even harder challenge, enable the datapack `"carpetskyadditions/skyblock_no_tree"` to start with only
a platform without any tree.

All datapacks are also available with a More Mob Heads (Vanilla Tweaks) variant.

For custom or server installations, follow the [Detailed Installation Instructions](docs/en_us/installation.md)

## Features

### SkyBlock Generation

A SkyBlock world generates exactly like a Default generation world, but with every block removed. Biomes and Structure
Bounding Boxes are kept in place. This means Husks will still spawn in Deserts and Blazes will spawn in Nether
Fortresses, for example. Even with almost every block removed, you have access to most things in the game.

[More Generation Details](docs/en_us/generation.md)

### Gameplay Changes ###

SkyBlock generation does, however, leave a few unobtainable resources.
In addition to adding SkyBlock generation, this mod also fills in
the gaps, making as minimal and Minecrafty changes as possible.

The biggest progress-blocker is Lava, which is unobtainable by default.
This prevents going to the Nether or End or getting Cobblestone.
This mod fixes that problem by providing a way to get Lava.

Sand is also very limited in a default SkyBlock world, but the mod allows for more to be created.

Most other resources provided by this mod are cosmetic and don't make major changes to progression, such as Dead Bushes
and Ender Dragon Heads.

When possible, changes were added to a datapack instead of being programmed into the mod for ease of user customization.
The datapack is built into the mod.

SkyBlock advancements were also added to guide progression and document the mod's changes to vanilla.

*When installed with default settings, all blocks, items, mobs, and advancements obtainable in Default generation are
obtainable in SkyBlock generation.*

[List of Mod Features](docs/en_us/features.md)

[List of Datapack Features](docs/en_us/datapack.md)

### Commands
This mod provides a command for generating islands,
which simplifies having multiple players on the same server with different starting islands.

[List of Mod Commands](docs/en_us/commands.md)

### Progression Walkthrough

If you get stuck, a general progression walkthrough is available [here](docs/en_us/progression.md).

### Fixing Old Trial Spawners / Vaults

If you have broken Trial Spawners / Vaults from an older version of this mod that aren't working properly
You can use [mcaselector](https://github.com/Querz/mcaselector) to fix them.
Simply download the mcaselector.jar, place it next to your world folder, and run it in a command-line using this command:

`java -jar mcaselector.jar --mode delete --world 'world' --query 'Palette contains "trial_spawner" OR Palette contains "vault"'`
*Note: This will completely delete any chunks containing vaults/trial spawners, so they can be regenerated. Use with caution.*

## Acknowledgements
- [@Seigmannen](https://github.com/Seigmannen) Merging his improved datapacks with better balance & more varied starts

- [@skyrising](https://github.com/skyrising/skyblock) for the initial mod idea

- [@DeadlyMC](https://github.com/DeadlyMC/Skyblock-datapack) for the initial ideas for the datapack

- [@gnembon](https://github.com/gnembon/fabric-carpet) for `fabric-carpet`

- [Vanilla Tweaks](https://vanillatweaks.net) for the More Mob Heads Datapack

- All the translators on CrowdIn

## License
This project is licensed under the terms of the MIT license.

## Notable Contributors
- [Seigmannen](https://github.com/Seigmannen) (Code/Datapack/Testing)
- [XieLong55](https://github.com/XieLong55) (Simplified Chinese Translation Updates)

