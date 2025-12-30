## 数据包特性

### Modifying

To customize the datapack,
Download the mod .jar file and open it with any archive tool you prefer such as [7-Zip](https://7-zip.org/), WinRAR, File Roller, Ark, or a command line utility like unzip or tar
and edit to your liking.

Look in either /resourcepacks or /data

#### Structures

Configured features can be overridden to replace the default world spawn platform and Sky Island platform.

The world spawn platform uses a configured feature defined in
`data/carpetskyadditions/worldgen/configured_feature/spawn_platform.json`.
By default, it loads the structure defined in `data/carpetskyadditions/structures/spawn_platform.nbt`
at an offset of (-4, -1) at y=63.

---

### Built-In "SkyBlock" Datapack Features

Note that this datapack needs to be explicitly enabled when starting a new world.

#### Villagers Gift Lava Buckets

Provides _Lava_

Disable by deleting `skyblock/data/minecraft/loot_table/gameplay/hero_of_the_village`

Lava Buckets can be received as a Hero of the Village gift from Armorers, Weaponsmiths, and Toolsmiths.

---

#### Elytra Obtainable from Endermites

Provides _Elytra_

Disable by deleting `skyblock/data/minecraft/loot_table/entities/endermite.json`

Player killing an Endermite affected by both Slow Falling and Levitation
has a chance to drop an Elytra, increased by looting.

---

#### Piglin Brutes Drop Ancient Debris

Provides _Ancient Debris_

Disable by deleting `skyblock/data/minecraft/loot_table/entities/piglin_brute.json`

A Carpet setting enables Piglin Brutes to spawn in bastions. These Brutes have a chance to drop Ancient Debris.

---

#### Calcite and Tuff Obtainable

Provides _Calcite_ and _Tuff_

Disable by deleting `skyblock/data/skyblock/recipe/[tuff_from_blasting_andesite.json + calcite_from_blasting_diorite.json]`

Put Diorite in a Blast Furnace for Calcite.

Put Andesite in a Blast Furnace for Tuff.

---

#### Glow Berries Craftable

Provides _Glow Berries_

Disable by deleting `skyblock/data/skyblock/recipe/glow_berries.json`

Sweet Berries crafted with Glow Ink Sacs give Glow Berries.

---

#### Spider Jockeys Drop Cobwebs

Provides _Cobwebs_

Disable by deleting `skyblock/data/minecraft/loot_table/entities/[skeleton.json + spider.json]`

When a player kills a Spider Jockey, the first half killed drops a cobweb.

---

#### Cocoa Beans Obtainable by Fishing in Jungles

Provides _Cocoa Beans_

Disable by deleting `skyblock/data/minecraft/loot_table/gameplay/fishing/junk.json`

Matching Bedrock, Cocoa Beans can be obtained as a junk item when fishing in a Jungle.

---

#### Ores are Craftable

Provides _ores_

Disable by deleting `skyblock/data/skyblock/recipe/*_ore.json`

All ores can be crafted using a block of the base stone material and four of the ore's material.

The recipe requires ingots, Nether Quartz, Coal, Diamonds, or Emeralds.

_Copper, Redstone, and Lapis Lazuli require blocks._

For example, Nether Gold Ore can be crafted with Netherrack in the center and Gold Ingots on the four sides.

![4 Gold Ingots around Netherrack gives Nether Gold Ore](../screenshots/ore_recipe.png?raw=true "Ore Recipe")

---

#### Horse Armor is Craftable

Provides _Iron Horse Armor_, _Golden Horse Armor_, and _Diamond Horse Armor_

Disable by deleting `skyblock/data/skyblock/recipe/*_horse_armor.json`

Craft Horse Armors with their respective materials in a **H** shape.

---

#### Cats Gift Enchanted Golden Apples

Provides _Enchanted Golden Apples_

Disable by deleting `skyblock/data/minecraft/loot_table/gameplay/cat_morning_gift.json`

Cats will rarely bring the player an Enchanted Golden Apple as a morning gift.

---

#### Piglins Give Gilded Blackstone

Provides _Gilded Blackstone_

Disable by deleting `skyblock/data/minecraft/loot_table/gameplay/piglin_bartering.json`

Piglins will rarely give Gilded Blackstone when bartering.

---

#### Creepers Drop Structure Specific Music Discs or Fragments in those Structures

Provides the music discs _Pigstep_, _otherside_, _5_, _Precipice_, and _Creator_

Disable by deleting `skyblock/data/minecraft/loot_table/entities/creeper.json`

When Creepers are killed by Skeletons in the Bastions, they can drop the Music Disc "Pigstep".

When Creepers are killed by Skeletons in the Strongholds, they can drop the Music Disc "otherside".

When Creepers are killed by Skeletons in the Ancient Cities, they can drop Disc Fragment 5.

When Creepers are killed by Skeletons in the Trial Chambers, they can drop the Music Discs "Precipice" or "Creator".

---

#### Zoglins Drop Snout Banner Patterns

Provides _Snout Banner Pattern_

Disable by deleting `skyblock/data/minecraft/loot_table/entities/zoglin.json`

Zoglins will always drop a Snout Banner Pattern when killed by a player.

---

#### Flowering Azalea Leaves Drop Spore Blossoms

Provides _Spore Blossoms_

Disable by deleting `skyblock/data/minecraft/loot_table/blocks/flowering_azaliea_leaves.json`

Flowering Azalea Leaves have a chance to drop Spore Blossoms, increased by Fortune.

---

#### Endermen Can Pick Up Tall Grass and Large Ferns

Provides _Tall Grass and Large Ferns_

Disable by deleting `skyblock/data/minecraft/tags/block/enderman_holdable.json`

Endermen who have picked up Tall Grass and Large Ferns can be killed to retrieve those as an item,
mimicking pre-1.19.3 behavior.

The mod fixes Endermen's handling of double-tall blocks, allowing them to be correctly placed down without being
destroyed.

---

#### Netherite Upgrade Smithing Template is Craftable

Provides _Netherite Upgrade Smithing Template_

Disable by deleting `skyblock/data/skyblock/recipe/netherite_upgrade_smithing_template_from_netherite.json`

A Netherite Upgrade Smithing Template is crafted from the duplication recipe
with the Smithing Template replaced with a Netherite Ingot.

![Netherite Upgrade Smithing Template Crafting Recipe](../screenshots/netherite_upgrade_smithing_template_recipe.png?raw=true "Template Recipe")

---

#### Loot Tables for Trial Vaults are Updated

Provides _Trial Chamber Pottery Sherds_ and the music disc _Creator (Music Box)_

As the trial vaults are easy to find and provides an easy way to get some resources that would make some parts of this mod obsolete, the
loot tables for the trial vaults has been updated.

Added to regular vaults:

- Flow Pottery Sherd
- Guster Pottery Sherd
- Scrape Pottery Sherd

Removed from regular vaults:

- Diamond
- Diamond Axe
- Diamond Chestplate
- Golden Apple
- Bolt Armor Trim Smithing Template
- Music Disc (Precipice)
- Trident

Added to ominous vaults:

- Music Disc (Creator (Music Box))

Removed from ominous vaults:

- Diamond
- Golden Apple
- Diamond Axe
- Diamond Chestplate
- Block of Diamond
- Enchanted Golden Apple
- Flow Armor Trim Smithing Template
- Music Disc (Creator)

---

### Advancements

The datapack adds numerous advancements to guide progression.

It also adds multiple challenge advancements that provide Armor Trim Smithing Templates as rewards upon completion.

The challenge advancements are documented below.

#### Seeing Patterns Everywhere

Completing rewards _Sentry Armor Trim Smithing Template_

Granted upon obtaining all Banner Patterns.

---

#### Let There Be Light

Completing rewards _Eye Armor Trim Smithing Template_

Granted upon obtaining all Light Sources in the following list:

- Torch and Soul Torch
- Lantern and Soul Lantern
- Campfire and Soul Campfire
- Candle and all Colored Candles
- Glowstone
- Jack o'Lantern
- Shroomlight
- Sea Lantern
- All Froglights
- Lava Bucket
- Redstone Lamp
- Glow Berries
- Glow Lichen
- Sea Pickle
- End Rod
- Furnace, Blast Furnace, and Smoker
- Brewing Stand
- Crying Obsidian
- Respawn Anchor
- Redstone Ore and Deepslate Redstone Ore
- Enchanting Table
- Ender Chest
- Redstone Torch
- Sculk Sensor and Calibrated Sculk Sensor
- Sculk Catalyst
- Amethyst Cluster and all sized Buds
- Magma Block
- Brown Mushroom
- Beacon
- Conduit
- Dragon Egg
- All variants of Copper Bulb

---

#### End City Builder

Completing rewards _Spire Armor Trim Smithing Template_

Granted upon crafting or stonecutting all Purpur Blocks, Pillars, Stairs, and Slabs while levitated.

---

#### Treasure Map to Nowhere

Completing rewards _Vex Armor Trim Smithing Template_

Granted upon entering a Woodland Mansion bounding box.

The spirit of this challenge is to follow a treasure map from a villager,
but looking up the coordinates would work too.

---

#### Wither Art Thou

Completing rewards _Rib Armor Trim Smithing Template_

Granted upon killing a Wither within the (small) bounding box of a Nether Fortress.

---

#### Air Tunes

Completing rewards _Silence Armor Trim Smithing Template_

Granted upon playing all music discs in a Jukebox.

Music discs must be clicked onto a Jukebox, not hoppered in.

---

#### Way of the Ancients

Completing rewards _Dune Armor Trim Smithing Template_

An exact replica Desert Pyramid must be built as described [here](https://minecraft.fandom.com/wiki/Desert_pyramid/Structure).
The structure can face any direction. Only layers from the Blue Terracotta layer to the top matter.

The advancement is granted when a Husk is sacrificed on the Blue Terracotta in the center of the pyramid.

---

#### Sky Pirate

Completing rewards _Coast Armor Trim Smithing Template_

Granted upon traveling 30km in a boat.

---

#### War Pigs

Completing rewards _Snout Armor Trim Smithing Template_

Granted upon killing a Piglin Brute while riding a Pig and wearing a Piglin Head.

The vanilla War Pigs advancement is impossible; this serves as its replacement.

---

#### Resistance Isn't Futile

Completing rewards _Ward Armor Trim Smithing Template_

Granted upon getting hit by a Warden while wearing full Protection 4 Netherite Armor
and affected by Resistance 4.

---

#### Spy in the Sky

Completing rewards _Wild Armor Trim Smithing Template_

Granted after looking through a Spyglass at all animals in the following list:

- Axolotl
- Armadillo
- Bat
- Bee
- Camel
- Cat
- Spider and Cave Spider
- Chicken
- Cod, Salmon, Pufferfish, and Tropical Fish
- Cow
- Dolphin
- Horse, Donkey, and Mule
- Endermite
- Fox
- Frog and Tadpole
- Squid and Glow Squid
- Goat
- Hoglin
- Llama
- Mooshroom
- Ocelot
- Panda
- Parrot
- Pig
- Polar Bear
- Rabbit
- Sheep
- Silverfish
- Sniffer
- Strider
- Turtle
- Wolf

---

#### Lightning Conqueror

Completing rewards _Bolt Armor Trim Smithing Template_

Granted after using a channeling trident to take down creepers, guardians, mooshrooms, zombified piglins, and witches during a thunderstorm

---

#### Harnessing the Flow

Completing rewards _Flow Armor Trim Smithing Template_

Granted after using a riptide trident under the influence of a conduit during rain.
