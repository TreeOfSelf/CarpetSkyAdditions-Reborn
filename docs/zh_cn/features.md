## 模组特性

#### 失活的珊瑚和失活的珊瑚扇可以冲蚀成沙

提供额外的 _沙子_ 与 _红沙_

通过指令 `/carpetskyadditions removeDefault coralErosion` 可禁用该特性

带有流动水的失活的珊瑚和失活的珊瑚扇每 16-32 秒会生成一个沙子掉落物。
而火珊瑚则会生成红沙。

珊瑚在生成沙子后有 3% 的概率会破坏。

无限自动化农场是可行的，并不复杂。

添加此特性是因为：

- 仅从流浪商人处获取沙子远远无法满足需求。
- 重力方块复制和重复交易 bug 是没有意义的，随时可能被官方修复。
- 尸壳掉落沙子的旧途径很无聊，因为它只是另一种形式的刷怪塔。

---

#### 击杀末影龙后会生成潜影贝

提供 _潜影贝_

通过指令 `/carpetskyadditions removeDefault shulkerSpawning` 可禁用该特性

当末影龙被再次击杀时，一只潜影贝会在基岩祭坛的顶部生成。

---

#### 山羊撞击分解下界疣块

提供 _下界疣_

通过指令 `/carpetskyadditions removeDefault rammingWart` 可禁用该特性

当山羊冲撞下界疣块时，该方块会被破坏并掉落下界疣。

---

#### 浓稠的药水可将石头转化为深板岩

提供 _深板岩_

通过指令 `/carpetskyadditions removeDefault renewableDeepslate` 可禁用该特性

仅禁用喷溅/滞留药水的转化，可使用命令 `/carpetskyadditions setDefault renewableDeepslate no_splash`

右键点击或通过发射器使用的浓稠药水可以将对应的石头转化成为深板岩。

喷溅的浓稠药水也会将所有覆盖的石头方块转化成深板岩。

转化概率等于该实体本应获得的药水持续时间百分比的两倍。

浓稠的滞留型药水会在其云雾范围内，持续将所有石头方块转化为深板岩。

---

#### 下界岩和菌岩会随下界传送门结构一起生成

Provides _Netherrack_ and _Nylium_

通过指令 `/carpetskyadditions removeDefault renewableNetherrack` 可禁用该特性

当下界传送门在虚空中生成时，其周围会生成少量的下界岩或菌岩方块。

生成哪种方块取决于生物群系 —— 在绯红森林中生成绯红菌岩，在诡异森林中生成诡异菌岩，在其他群系则生成下界岩。

---

#### 流浪商人销售高花

提供 _高花_

通过指令 `/carpetskyadditions removeDefault tallFlowersFromWanderingTrader` 可禁用该特性

高花的交易机制模仿了基岩版的机制。

##### 追加的一级交易内容

| 获得物品 | 价格 | 失效前可交易次数 |
| ---- | -- | -------- |
| 丁香   | 1  | 12       |
| 玫瑰丛  | 1  | 12       |
| 牡丹   | 1  | 12       |
| 向日葵  | 1  | 12       |

---

#### 恼鬼可以被转化为悦灵

提供 _悦灵_

通过指令 `/carpetskyadditions removeDefault allayableVexes` 可禁用该特性

对恼鬼播放正确顺序的 5 个音符盒音符，可将它们转化为悦灵。

恼鬼会检测周围 16 格范围内的音符，并根据是否演奏了正确的音符而发出粒子效果。 乐器种类和八度音阶都将被忽略，这意味着 F#<sub>3</sub> 和 F#<sub>5</sub> 将被视作同一个音符。

当恼鬼处于矿车中时，可以使用比较器和探测铁轨来确定序列中的下一个音符。
The Comparator outputs a value from 0 (corresponding to F#) to 11 (corresponding to F).

---

#### 狐狸携带甜浆果生成

提供 _甜浆果_

通过指令 `/carpetskyadditions removeDefault foxesSpawnWithSweetBerriesChance` 可禁用该特性

通过指令 `/carpetskyadditions setDefault foxesSpawnWithSweetBerriesChance <chance>` 可调整生成甜浆果的概率。

当狐狸携带物品生成时，这个物品有 20% 的概率为甜浆果。 狐狸在生成之后很快就会吃掉它，所以动作要快。

---

#### 铁砧压合煤炭块为钻石

提供 _钻石_

通过指令 `/carpetskyadditions removeDefault renewableDiamonds` 可禁用该特性

下落的铁砧可将整组煤炭块转化为钻石。

---

#### 雷击藤蔓使其通电

提供_发光地衣_

通过指令 `/carpetskyadditions removeDefault lightningElectrifiesVines` 可禁用该特性

如果雷电击中附着藤蔓的萤石，这些藤蔓将会转变为发光地衣。 击中萤石上的避雷针时该特性依旧有效。

---

#### 紫颂植物在末地小岛上生成

提供 _紫颂果_ 和 _紫颂花_

通过指令 `/carpetskyadditions removeDefault gatewaysSpawnChorus` 可禁用该特性

当一个末地折跃门在虚空上方生成时，其伴生的末地石小岛将会生成一株紫颂植物。

---

#### 海豚可以找到海洋之心

提供 _海洋之心_

通过指令 `/carpetskyadditions removeDefault renewableHeartsOfTheSea` 可禁用该特性

当海豚被喂食鱼后，它会在海底的沙子或砂砾中找到一个海洋之心。

请确保给予海豚足够的空间来搜索。

必须在海洋群系 —— 这玩意儿是海洋之心，而不是丛林之心。

---

#### 紫水晶母岩可再生

提供 _紫水晶母岩_

通过指令 `/carpetskyadditions removeDefault renewableBuddingAmethysts` 可禁用该特性

当岩浆被方解石包围后，外围再被平滑玄武岩包围时，最终岩浆会变为一个紫水晶母岩。

##### 如何搭建该结构

![岩浆](../screenshots/amethyst_step_1_240.png?raw=true "紫水晶母岩再生步骤 1")
\---->
![用方解石包围岩浆](../screenshots/amethyst_step_2_240.png?raw=true "紫水晶母岩再生步骤 2")
\---->
![用平滑玄武岩包围方解石](../screenshots/amethyst_step_3_240.png?raw=true "紫水晶母岩再生步骤 3")

在一定时间后（每刻有 1/100 的概率 —— 约 2 小时），处于结构正中心的岩浆将会转变为紫水晶母岩。

![岩浆变成了紫水晶母岩](../screenshots/amethyst_result_240.png?raw=true "紫水晶母岩再生结果")

---

#### 树苗会在沙子上枯死

提供 _枯萎的灌木_

通过指令 `/carpetskyadditions removeDefault saplingsDieOnSand` 可禁用该特性

树苗可以放在沙子和红沙上。

一段时间后它们会枯萎并转化为枯萎的灌木。

---

#### 枯萎的灌木可用水重新滋润

提供 _灌木丛_

通过指令 `/carpetskyadditions removeDefault doDeadBushToBush` 可禁用该特性

Using a Water Bottle on a Dead Bush turns it into a Bush.

---

#### 末影龙掉落龙首

提供 _龙首_

通过指令 `/carpetskyadditions removeDefault renewableDragonHeads` 可禁用该特性

当末影龙被**闪电苦力怕**击杀时，她会掉落她的头颅。

---

#### 巨型蘑菇生成菌丝

提供 _菌丝_

通过指令 `/carpetskyadditions removeDefault hugeMushroomsSpreadMycelium` 可禁用该特性

当巨型蘑菇成熟时，它会往附近的方块上扩散菌丝，该机制类似于巨型云杉扩散灰化土。

---

#### 具有音波定位能力的生物在被音爆杀死时掉落回响碎片

提供 _回响碎片_

通过指令 `/carpetskyadditions removeDefault renewableEchoShards` 可禁用该特性

蝙蝠和海豚在被监守者释放的音爆杀死时会掉落回响碎片。

---

#### 溺尸会孵育嗅探兽蛋

提供 _嗅探兽蛋_

通过指令 `/carpetskyadditions removeDefault sniffersFromDrowneds` 可禁用该特性

溺尸有 1% 的概率在生成时副手持有一个嗅探兽蛋。

当溺尸死亡时，这个蛋永远不会掉落。

然而，当持有嗅探兽蛋的溺尸踩踏海龟蛋时，会用嗅探兽蛋替换被破坏的海龟蛋。

---

#### 嗅探兽具有“可疑”行为

提供 _可疑的沙子_ 和 _可疑的沙砾_

通过指令 `/carpetskyadditions removeDefault suspiciousSniffers` 可禁用该特性

仅禁用铁粒掉落可使用 `/carpetskyadditions setDefault suspiciousSniffers no_iron`

嗅探兽会在沙子、红沙、可疑的沙子、沙砾以及可疑的沙砾中挖掘。

它们会从这些材料中挖出铁粒。
这些铁粒更多是用于世界观设定而非作为主要铁资源，但在建成刷铁机之前，可以提供一种被动的铁来源。

在沙漠神殿或温暖海洋遗迹范围内挖掘时，有 10% 的概率将沙子转化为可疑的沙子；
在古道遗迹或寒冷海洋遗迹范围内挖掘时，有 10% 的概率将沙砾转化为可疑的沙砾。

可疑方块将继承其生成所在结构的战利品表。
在古迹废墟中，有 20% 的概率使用稀有古迹废墟战利品表。
在沙漠神殿中，有 20% 的概率使用沙漠水井战利品表。

---

#### 监守者附近的附魔台可以提供迅捷潜行的附魔

提供 _迅捷潜行_

通过指令 `/carpetskyadditions removeDefault renewableSwiftSneak` 可禁用该特性

处于监守者周围 8 格范围内的附魔台可以为其上的物品提供迅捷潜行的附魔。

---

#### 毒马铃薯转化蜘蛛

提供 _洞穴蜘蛛_

通过指令 `/carpetskyadditions removeDefault poisonousPotatoesConvertSpiders` 可禁用该特性

对蜘蛛使用毒马铃薯可将其转化为洞穴蜘蛛。

---

#### 流浪商人可以骑着骆驼生成

提供 _骆驼_

通过指令 `/carpetskyadditions removeDefault traderCamels` 可禁用该特性

当流浪商人在沙漠或恶地生物群系中生成时（标签 `carpetskyadditions:wandering_trader_spawns_on_camel`），
将不会携带商队羊驼，而是会骑乘一只骆驼。

如果流浪商人在骑乘骆驼时消失，且仍然坐在骆驼上，那么骆驼也会随之消失。

当流浪商人正在骑乘时，骆驼无法被骑乘、喂食或拴绳。

注意：如果仅在服务器端安装，该流浪商人会显示为站在骆驼上，而不是坐着。 此外，对被骑乘的骆驼进行喂食或拴绳时，客户端会显示使用了仙人掌或拴绳。
此问题仅影响客户端显示，但仍不建议尝试干预流浪商人的骆驼。

---

#### 小型垂滴叶可以繁殖

提供额外的 _小型垂滴叶_

通过指令 `/carpetskyadditions removeDefault spreadingSmallDripleaves` 可禁用该特性

当小型垂滴叶种植在黏土上，且下半部分处于含水状态、上半部分未含水，并且上半部分的光照等级恰好为 5 时，它可以进行扩散。 其扩散方式与蘑菇类似。

它只会向满足相同条件的方块扩散。
水平方向最远可扩散 5 格，垂直方向最远 2 格。
距离越近的方块概率越高。
在其周围 5x2x5 的范围内（上下两半均计数），最多只能存在 15 个小型垂滴叶方块。

在空岛世界中，小型垂滴叶通常非常稀缺，因为它们只能通过流浪商人一次获得 10 个。
该机制允许对其进行农场化和更频繁的使用。

---

#### 珊瑚可以向方解石扩散

提供额外的 _珊瑚块_

通过指令 `/carpetskyadditions removeDefault spreadingCoral` 可禁用该特性

当一个方解石方块在其周围 3x3 范围内至少存在 8 个相同类型的珊瑚方块时，它在一次随机刻中可以转化为该珊瑚方块（如果该珊瑚能够存活）。

转化概率取决于该位置的适宜度。
The suitability is based on the [generation temperature and continentalness parameters](https://minecraft.wiki/w/World_generation#Overworld).
理想位置定义为温度 0.65、大陆性 -0.3，对应于温暖地区、靠近海岸的位置。
这些数值可在单人游戏的 F3 界面中查看。
算法如下：

```
如果维度不是主世界：
    适宜度 = 0
否则如果是平坦世界：
    适宜度 = 0.5
否则：
    # 该数值会被限制在 0 到 1 之间
    适宜度 = 1 - ((温度 - 0.65)^2 + (大陆性 + 0.3)^2)
```

一次随机刻中的转化概率为 `适宜度 × 0.49 + 0.01`。

在最适宜的情况下，平均约 2 分钟完成一次转化。
在最不适宜的情况下，平均接近 2 小时。

在空岛世界中，珊瑚方块通常非常有限，因为它们只能通过流浪商人一次获得 8 个。
该机制允许对其进行农场化和更频繁的使用。

---

#### 流浪商人出售熔岩

提供 _熔岩_

通过指令`/carpetskyadditions setDefault lavaFromWanderingTrader true`启用该特性

\*\*\* 默认禁用 - 取而代之的是通过村庄英雄的礼物来获取熔岩 \*\*\*

##### 追加的二级交易内容

| 物品  | 价格 | 物品输入 | 失效前可交易次数 |
| --- | -- | ---- | -------- |
| 熔岩桶 | 16 | 桶    | 1        |

---

#### 烈焰人被带到主世界时会转变为旋风人

提供 _旋风棒_

通过指令 `/carpetskyadditions setDefault blazeToBreeze false` 可禁用该特性

烈焰人从下界进入主世界时会转变为旋风人。
带有自定义名称标签的实体不受影响。

---

#### 对未激活的试炼刷怪笼使用旋风棒会将其变为旋风人试炼刷怪笼

提供 _试炼密室战利品_

通过指令 `/carpetskyadditions setDefault generateTrialChambers false` 可禁用生成

在未激活的试炼刷怪笼上使用旋风棒会将其转变为旋风人试炼刷怪笼。
这是获取试炼刷怪笼战利品的方式。

---

#### 在张开的眼眸花附近种植苍白橡木树苗会生成一个嘎吱之心

Provides _Creaking Heart_ and _Resin_

通过指令 `/carpetskyadditions setDefault paleBlossomCreakingHeart false` 可禁用该特性

如果在苍白之园生物群系中，将四个苍白橡树树苗放置在一朵开启的眼眸花附近，苍白橡树有 10% 的概率生成一个嘎吱之心。

---

### Carpet 特性

默认状态下安装也会启用以下`fabric-carpet`的特性：

- 启用 [`renewableSponges`](https://github.com/gnembon/fabric-carpet/wiki/Current-Available-Settings#renewablesponges) 选项以再生海绵。
  - 运行指令 `/carpet removeDefault renewableSponges` 可禁用该特性
- 启用 [`piglinsSpawningInBastions`](https://github.com/gnembon/fabric-carpet/wiki/Current-Available-Settings#piglinsSpawningInBastions) 选项以在堡垒遗迹中再生猪灵蛮兵，从而获取远古残骸。
  - 运行指令 `/carpet removeDefault piglinsSpawningInBastions` 可禁用该特性
