# TrustedServer 26.x

TrustedServer 面向 Minecraft Java 独立服务器的 26.x 专用适配仓库。通用认证协议、密码学、客户端密钥和服务端账号存储来自 `MitsuhaQuQ/securserver`；本仓库只维护 Minecraft 26.1+ 的非混淆 Fabric 适配层。

> 当前状态：**26.3 迁移中，尚不可用，也没有可发布 jar。**

## 为什么单独建仓库

Minecraft 26.1 起不再混淆，并同时重构了 GUI render-state、资源标识符、玩家身份、权限和网络 API。它需要 Java 25 与非重映射 Loom，和 1.20.x/1.21.x 的构建及源码边界差异过大，因此不再与旧版本适配层共用同一源码目录。

## 构建环境

- Minecraft 26.3
- Java 25
- Gradle 9.9
- Fabric Loom 1.19.4（`net.fabricmc.fabric-loom`，非 remap 模式）
- Fabric Loader 0.20.1
- Fabric API 0.165.2+26.3

```powershell
.\gradlew.bat clean build
```

当前命令预期会在 `fabric-26.3` 的 Minecraft 适配源码处失败；`core`、`core-client`、`core-server` 可以独立测试。

## 当前迁移重点

- `GuiGraphics` → `GuiGraphicsExtractor` render-state GUI
- `ResourceLocation` → `Identifier`
- `GameProfile`/旧 OP API → `NameAndId` 与新权限 API
- 旧 `PacketByteBufs`/登录 query 包装 → 26.3 payload/codec
- SDL3 环境下的认证密钥文件选择器
- 登录、状态 Ping、命令限制和权限相关 Mixin 注入点

详细清单见 [docs/ADAPTER_26.md](docs/ADAPTER_26.md)。

## 通用设计文档

- [认证协议](docs/PROTOCOL.md)
- [核心逻辑与存储](docs/CORE.md)
