## 空岛生成

空岛世界的生成方式与默认世界生成完全相同，但所有方块都会被移除。 生物群系和结构的边界框仍然会被保留。 这意味着，例如，尸壳仍会在沙漠中生成，烈焰人仍会在下界要塞中生成。

唯一的区别在于末地传送门框架上的末影之眼是随机的 —— 这可能会在未来调整。

只有少量东西会伴随世界生成：

- A (configurable) small starting island where you spawn:
  ![small spawn platform with grass and an oak tree](../screenshots/spawn_platform.png?raw=true)

- End Portal Frames (unless `generateEndPortals` is set to `false`):
  ![end portal frame remains](../screenshots/end_portal.png?raw=true)

- Trial spawner and a vault and ominous vault (unless `generateTrialChambers` is set to `false`):
  ![trial spawner and vaults](../trial_spawner_and_vaults.png?raw=true)

- Ancient City Portal Frames with a Sculk Shrieker (unless `generateAncientCityPortals` is set to `false`):
  ![ancient city portal frame remains](../screenshots/ancient_city_portal.png?raw=true)

- 蠹虫刷怪笼（除非属性 `generateSilverfishSpawners` 被设置为 `false`）

- 堡垒遗迹的宝藏室中的岩浆怪刷怪笼（仅当属性 `generateMagmaCubeSpawners` 设置为 `true` 时生效）

- 末地中的所有随机返回折跃门（仅当属性 `generateRandomEndGateways` 设置为 `true` 时生效）
