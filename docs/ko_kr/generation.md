## SkyBlock Generation

A SkyBlock world generates exactly like a Default generation world, but with every block removed. Biomes and Structure
Bounding Boxes are kept in place. This means Husks will still spawn in Deserts and Blazes will spawn in Nether
Fortresses, for example.

The only difference is that End Portal eyes are randomized — this may change in the future.

Only a few things are generated:

- A (configurable) small starting island where you spawn:
  ![small spawn platform with grass and an oak tree](../screenshots/spawn_platform.png?raw=true)

- End Portal Frames (unless `generateEndPortals` is set to `false`):
  ![end portal frame remains](../screenshots/end_portal.png?raw=true)

- Trial spawner and a vault and ominous vault (unless `generateTrialChambers` is set to `false`):
  ![trial spawner and vaults](../trial_spawner_and_vaults.png?raw=true)

- Ancient City Portal Frames with a Sculk Shrieker (unless `generateAncientCityPortals` is set to `false`):
  ![ancient city portal frame remains](../screenshots/ancient_city_portal.png?raw=true)

- Silverfish spawners (unless `generateSilverfishSpawners` is set to `false`)

- Magma Cube spawners in Treasure Room Bastion Remnants (only if `generateMagmaCubeSpawners` is set to `true`)

- Random End Gateways throughout the End (only if `generateRandomEndGateways` is set to `true`)
