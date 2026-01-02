<img width="128" height="128" alt="image" src="https://github.com/user-attachments/assets/5e8be161-1902-48cb-97b8-8ec7c4192789" />

[English](../../README.md) | 简体中文

# Carpet Sky Additions Reborn

Carpet Sky Additions Reborn 是一个基于 CarpetSkyAdditions 的模组
[在此查看原模组](https://github.com/jsorrell/CarpetSkyAdditions/)

## 在 sky.hardcoreanarchy.gay 上试玩本模组

Carpet Sky Additions 是 [fabric-carpet](https://github.com/gnembon/fabric-carpet) 的一个模块，最初基于 [skyrising/skyblock](https://github.com/skyrising/skyblock)。

本模组旨在提供专家级的空岛生存（SkyBlock）游戏体验，依赖于玩家对 Minecraft 机制的了解。
在某些情况下，使用外部工具如 [Chunkbase](https://www.chunkbase.com/) 或 [MiniHUD](https://www.curseforge.com/minecraft/mc-mods/minihud) 会很有帮助。
鼓励使用这些工具。
有时为了推进进度，需要长时间的刷怪或挂机（AFK）。
除非选择空岛世界生成或特别启用了某些功能，否则本模组不会做任何事情。
这意味着可以在空岛世界和非空岛世界之间轻松切换，而无需重启客户端。

## 安装

要创建一个新的空岛世界，请选择 `世界类型：空岛` 并启用数据包 `"carpetskyadditions/skyblock"`。

如果你想要更大的挑战，请启用数据包 `"carpetskyadditions/skyblock_acacia"`，这样开局将是金合欢树而不是橡树。

如果你想要更难的挑战，请启用数据包 `"carpetskyadditions/skyblock_no_tree"`，这样开局将只有一个平台，没有任何树木。

所有数据包都有 More Mob Heads (Vanilla Tweaks) 变体可供选择。

对于自定义或服务器安装，请遵循 [详细安装说明](installation.md)。

## 特性

### 空岛生成

空岛世界的生成方式与默认世界生成完全相同，但所有方块都会被移除。 生物群系和结构的边界框仍然会被保留。 这意味着，例如，尸壳仍会在沙漠中生成，烈焰人仍会在下界要塞中生成。 即使移除了几乎所有方块，你仍然可以获得游戏中的大部分物品。

[更多生成细节](generation.md)

### 游戏性更改

然而，空岛生成确实留下了一些无法获取的资源。
除了添加空岛生成外，本模组还填补了这些空白，尽可能进行最小化且符合原版风格的更改。

最大的进度阻碍是熔岩，默认情况下无法获取。
这阻碍了前往下界或末地，也无法获得圆石。
本模组通过提供获取熔岩的方法解决了这个问题。

在默认的空岛世界中，沙子也非常有限，但本模组允许制造更多沙子。

本模组提供的大多数其他资源都是装饰性的，不会对进度产生重大影响，例如枯萎的灌木和末影龙首。

为了方便用户自定义，只要可能，更改都会添加到数据包中，而不是编程到模组中。
数据包已内置于模组中。
数据包已内置于模组中。

还添加了空岛进度，以引导游戏进程并记录模组对原版的更改。

_在默认设置下安装时，默认生成中可获得的所有方块、物品、生物和进度在空岛生成中均可获得。_

[模组特性列表](features.md)

[数据包特性列表](datapack.md)

### 命令

本模组提供了一个生成岛屿的命令，这简化了在同一服务器上拥有不同起始岛屿的多个玩家的情况。

[模组命令列表](commands.md)

### 进度指南

如果你卡住了，可以在[这里](progression.md)找到通用的进度指南。

### 修复旧的试炼刷怪笼 / 宝库

如果你有来自本模组旧版本的损坏的试炼刷怪笼 / 宝库无法正常工作
你可以使用 [mcaselector](https://github.com/Querz/mcaselector) 来修复它们。
只需下载 mcaselector.jar，将其放在你的世界文件夹旁边，并在命令行中使用以下命令运行它：

`java -jar mcaselector.jar --mode delete --world 'world' --query 'Palette contains "trial_spawner" OR Palette contains "vault"'`
_注意：这将完全删除任何包含宝库/试炼刷怪笼的区块，以便它们可以重新生成。 请谨慎使用。_

## 致谢

- [@Seigmannen](https://github.com/Seigmannen) 合并了他改进的数据包，具有更好的平衡性和更多样化的开局

- [@skyrising](https://github.com/skyrising/skyblock) 最初的模组创意

- [@DeadlyMC](https://github.com/DeadlyMC/Skyblock-datapack) 最初的数据包创意

- [@gnembon](https://github.com/gnembon/fabric-carpet) 的 `fabric-carpet`

- [Vanilla Tweaks](https://vanillatweaks.net) 的 More Mob Heads 数据包

- Crowdin 上的所有翻译人员

## 许可证

本项目根据 MIT 许可证条款授权。

## 杰出贡献者

- [Seigmannen](https://github.com/Seigmannen) (代码/数据包/测试)
- [XieLong55](https://github.com/XieLong55) (简体中文翻译更新)

