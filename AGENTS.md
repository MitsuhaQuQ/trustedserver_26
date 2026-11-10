# TrustedServer 26.x repository instructions

本仓库只维护 Minecraft Java 26.1+ 的适配层；1.21.11 及更早版本继续在 `MitsuhaQuQ/securserver` 维护。

## 固定约束

- Fabric 使用非重映射插件 `net.fabricmc.fabric-loom`，不得添加 Yarn、Intermediary 或 `officialMojangMappings()`。
- 构建与运行使用 Java 25。
- `core`、`core-client`、`core-server` 的认证协议和密码学行为必须与主仓库保持兼容；Minecraft 适配不得擅自修改正式协议版本。
- 客户端和服务端必须同时安装 mod；不支持原版客户端、Geyser、Velocity 或 BungeeCord。
- 单人游戏和局域网内置服务器不启用认证。
- 不得为了编译通过而删除 PSK/正版令牌门禁、Ed25519、密码通道、新用户限制、状态脱敏或限流功能。
- 只有完整 `clean build`、Mixin audit 和真实客户端/服务端联调通过后，README 才能把 26.3 标记为可用。

## 同步规则

通用核心初始来源为 `MitsuhaQuQ/securserver` 的 `v0.1.0` 后续开发状态。同步核心时应记录上游 commit，并单独验证协议向量、SQLite、Argon2id 和恢复事务测试。
