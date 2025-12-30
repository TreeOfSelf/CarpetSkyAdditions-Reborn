## 安装

### 单人游戏

- 安装 [Fabric](https://fabricmc.net/use/installer/)
- 下载  [fabric-api](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files)，[fabric-carpet](https://www.curseforge.com/minecraft/mc-mods/carpet/files)，[cloth-config](https://www.curseforge.com/minecraft/mc-mods/cloth-config/files/all?filter-game-version=2020709689%3A7499),
  和 [Carpet Sky Additions](https://github.com/TreeOfSelf/CarpetSkyAdditions-Reborn/releases)
- 将下载的模组放入 `<minecraft-directory>/mods/`
- 启动 Minecraft 并选择 `创建新的世界`
- 将允许命令设置为 `开`，以便你能够启用/禁用模组功能
- 启用数据包 `carpetskyadditions/skyblock`
- 可选启用数据包 `carpetskyadditions/skyblock_acacia` 以使用金合欢树作为开局（或查看其他可用的数据包）
- 选择 `世界类型：空岛`
- 创建世界

### 多人游戏

该模组仅需在服务器端进行配置。

- 创建一个 [Fabric 服务端](https://fabricmc.net/use/server/)
- 下载  [fabric-api](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files)，[fabric-carpet](https://www.curseforge.com/minecraft/mc-mods/carpet/files)，[cloth-config](https://www.curseforge.com/minecraft/mc-mods/cloth-config/files/all?filter-game-version=2020709689%3A7499),
  和 [Carpet Sky Additions](https://github.com/TreeOfSelf/CarpetSkyAdditions-Reborn/releases)
- 将下载的模组放入 `<server-directory>/mods/`
- 启动服务端以生成配置模板 `server.properties` 和 `eula.txt` 文件
- 同意 EULA（最终用户许可协议）
- 打开配置文件 `server.properties`
- 将 `level-type=minecraft\:normal`（世界生成类型：默认）修改为 `level-type=carpetskyadditions\:skyblock`（世界生成类型：空岛）
- 将 `carpetskyadditions:skyblock` 从 `initial-disabled-packs` 移动到 `initial-enabled-packs`
- 可选将 `carpetskyadditions:skyblock_acacia`（或其他附加数据包）从 `initial-disabled-packs` 移动到 `initial-enabled-packs`，以使用金合欢树作为开局
- 启动服务器

### 配置

该模组有一个配置文件：`carpetskyadditions.toml`

#### `defaultToSkyBlockWorld`

_默认值为 false_

当设为 `true` 时，在创建新世界时将默认选择 `空岛` 世界类型。

---

#### `enableDatapackByDefault`

_默认值为 false_

当设为 `true` 时，在创建新世界时将默认启用 `skyblock` 数据包。

---

#### `initialTreeType`

_默认值为 OAK_

当设置为 `ACACIA` 时，在创建新世界时也会默认启用 `skyblock_acacia` 数据包。

仅在 `enableDatapackByDefault` 为 `true` 时生效

---

#### `autoEnableDefaultSettings`

_默认值为 true_

当设为 `true` 时，在首次使用 `空岛` 生成方式创建世界时，将启用默认的空岛设置。
