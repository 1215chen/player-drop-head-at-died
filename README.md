# Player Drop Head

> 玩家死亡后掉落自己的头颅 —— 一个极简的 Fabric 模组（Minecraft 1.21.1）

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-62b47a)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Loader-Fabric-dbb69c)](https://fabricmc.net/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

**Player Drop Head** 只做一件事：当玩家死亡时，在该玩家的死亡位置掉落一个属于他自己的玩家头颅（Player Head）。头颅保留了玩家的皮肤数据，放置到方块上后外观与本人一致。

适用于生存服、PVP 服、UHC、硬核模式等场景 —— 让每一次死亡都留下一点“纪念品”。

---

## 功能特性

- 💀 **死亡即掉落**：玩家死亡瞬间，在死亡位置生成并掉落该玩家的头颅。
- 🎨 **保留皮肤**：头颅通过 `PROFILE` 组件写入玩家的游戏档案，放置后正确显示该玩家的皮肤（含正版与离线皮肤纹理缓存）。
- ⚔️ **全死因覆盖**：摔落、溺水、燃烧、怪物、PvP、`/kill` 等任何死亡原因都会触发。
- 🖥️ **服务端逻辑**：仅需安装在**服务端**（单人游戏内置服务端同样生效），客户端无需安装，原版客户端可直接进服。
- 🪶 **零配置、零负担**：没有配置文件、没有额外依赖，只注册一个死亡事件回调，几乎不产生性能开销。

## 工作原理

模组只做了一件事 —— 监听 Fabric API 的 `ServerLivingEntityEvents.AFTER_DEATH` 事件：

```java
ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
    if (entity instanceof ServerPlayer player) {
        ItemStack head = new ItemStack(Items.PLAYER_HEAD);
        head.set(DataComponents.PROFILE, new ResolvableProfile(player.getGameProfile()));
        player.spawnAtLocation(head);
    }
});
```

即：玩家死亡 → 构造带有玩家档案的玩家头颅 → 在死亡位置作为掉落物生成。

## 环境要求

| 项目 | 版本 |
| --- | --- |
| Minecraft | 1.21.1 |
| Fabric Loader | ≥ 0.16.0（开发环境 0.19.3） |
| Fabric API | 任意 1.21.1 版本（开发环境 0.116.15+1.21.1） |
| Java | ≥ 21 |

## 安装

1. 为你的 Minecraft 1.21.1 安装 [Fabric Loader](https://fabricmc.net/use/installer/)。
2. 将 [Fabric API](https://modrinth.com/mod/fabric-api) 放入 `mods/` 目录。
3. 将本模组的 `.jar` 放入 `mods/` 目录。
4. 启动游戏或服务端即可，无需任何配置。

> 多人服务器只需装在服务端；单人游戏直接放进客户端 `mods/` 目录即可。

## 从源码构建

需要 JDK 21：

```bash
git clone https://github.com/1215chen/player-drop-head-at-died.git
cd player-drop-head-at-died
./gradlew build
```

Windows 下使用 `gradlew.bat build`。构建产物位于 `build/libs/`（`player-drop-head-1.0.0.jar`，文件名中的版本号取自 `gradle.properties`）。

首次构建会下载 Gradle 发行版与依赖，若网络较慢可参考 `gradle/wrapper/gradle-wrapper.properties` 中已放大的超时与重试参数。

## 注意事项

- 头颅以普通掉落物的形式生成：若死亡位置在岩浆、虚空或被方块覆盖，头颅可能被销毁或弹出，与普通掉落物规则一致。
- 开启 `keepInventory`（死亡不掉落）时，玩家背包中的物品会保留，但本模组生成的头颅仍会照常掉落。
- 只掉落**玩家头颅**，不处理僵尸、骷髅等生物头颅；也不改变原版头颅的获取方式。
- 当前仅针对 **1.21.1** 开发与测试，其他版本未做验证。

## 许可证

本项目基于 [MIT License](LICENSE) 开源，可自由使用、修改与分发，保留版权声明即可。

---

## English

**Player Drop Head** is a minimal Fabric mod for Minecraft 1.21.1 that drops the deceased player's own head at the death location.

- Drops a `PLAYER_HEAD` carrying the player's game profile (skin preserved).
- Triggers on any death cause, including PvP and `/kill`.
- Server-side only — vanilla clients can join without installing anything.
- No config, no extra dependencies.

**Requirements:** Minecraft 1.21.1, Fabric Loader ≥ 0.16.0, Fabric API, Java 21.

**Build:** `./gradlew build` → output in `build/libs/`.

Licensed under the [MIT License](LICENSE).
