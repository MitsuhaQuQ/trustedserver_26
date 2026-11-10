# TrustedServer 26.x

TrustedServer 面向 Minecraft Java 独立服务器的 26.x 专用适配仓库。通用认证协议、密码学、客户端密钥和服务端账号存储来自 `MitsuhaQuQ/securserver`；本仓库只维护 Minecraft 26.1+ 的非混淆 Fabric 适配层。

> 当前状态：**26.3 实验适配已可构建，服务端和客户端 Mixin audit 已通过；真实双端认证联调尚未完成。**

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

构建产物位于 `fabric-26.3/build/libs/trustedserver-fabric-26.3-<版本>.jar`。

## 已完成的 26.3 API 迁移

- `GuiGraphics` → `GuiGraphicsExtractor` render-state GUI；
- `ResourceLocation` → `Identifier`；
- `GameProfile`/旧 OP API → `NameAndId` 与新权限 API；
- 旧 `PacketByteBufs` → 26.3 ByteBuf 与 payload registry；
- SDL3 环境下改用 JDK 文件选择器；
- 登录、状态 Ping、旧版 Ping、命令限制、OP 暂停和出生点保护 Mixin；
- 服务端与客户端 Mixin audit；
- 服务端启动至 EULA 检查，客户端启动至主菜单。

## 尚需验证

- offline-mode 正确、错误和缺失 PSK 的真实连接；
- online-mode 正版令牌首次签发与再次证明；
- Ed25519、登录密码、找回密码和新用户限制；
- 密钥导入/导出文件选择与冲突覆盖交互；
- normal/enhanced/disablePing 的真实客户端状态显示。

详细清单见 [docs/ADAPTER_26.md](docs/ADAPTER_26.md)。

## 通用设计文档

- [认证协议](docs/PROTOCOL.md)
- [核心逻辑与存储](docs/CORE.md)
