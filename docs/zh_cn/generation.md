## 空岛生成

空岛世界的生成方式与默认世界生成完全相同，但所有方块都会被移除。 生物群系和结构的边界框仍然会被保留。 这意味着，例如，尸壳仍会在沙漠中生成，烈焰人仍会在下界要塞中生成。

唯一的区别在于末地传送门框架上的末影之眼是随机的 —— 这可能会在未来调整。

只有少量东西会伴随世界生成：

- 一个小（可自定义的）的出生点平台：
  ![一个小出生点平台，包括草方块，以及一棵树](../screenshots/spawn_platform.png?raw=true "出生点平台")

- 末地传送门框架（除非属性 `generateEndPortals` 被设置为 `false`）：
  ![末地传送门框架将得到保留](../screenshots/end_portal.png?raw=true "末地传送门框架")

- Trial spawner and a vault and ominous vault (unless `generateTrialChambers` is set to `false`):
  ![trial spawner and vaults](../screenshots/trial_spawner_and_vaults.png?raw=true "Trial Spaner and Vaults")

- 带有幽匿尖啸体的远古城市传送门框架（除非属性 `generateAncientCityPortals` 被设置为 `false`）：
  ![远古城市传送门框架将得到保留](../screenshots/ancient_city_portal.png?raw=true "远古城市传送门框架")

- 蠹虫刷怪笼（除非属性 `generateSilverfishSpawners` 被设置为 `false`）

- 堡垒遗迹的宝藏室中的岩浆怪刷怪笼（仅当属性 `generateMagmaCubeSpawners` 设置为 `true` 时生效）

- 末地中的所有随机返回折跃门（仅当属性 `generateRandomEndGateways` 设置为 `true` 时生效）
