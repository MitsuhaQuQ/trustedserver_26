# Minecraft 26.3 适配清单

## 已完成

- 独立仓库与 Gradle 多模块骨架
- Java 25 toolchain
- Gradle 9.9 / 非重映射 Loom 1.19.4
- Minecraft 26.3、Fabric Loader 0.20.1、Fabric API 0.165.2 依赖
- 通用 core、客户端密钥与服务端账号模块同步
- 旧版适配源码作为迁移基线

## 编译阻断项

1. GUI 改为 `GuiGraphicsExtractor` 的 render-state 提取模型，全部认证 Screen 和自定义复选框需要重写。
2. `ResourceLocation` 更名为 `Identifier`，需要 26.x 专用频道 ID 适配。
3. Fabric 移除旧 `PacketByteBufs` 辅助接口，需要重写登录 query 与 play payload codec。
4. Authlib/服务端列表改用 `NameAndId`，封禁、OP、离线身份和玩家缓存接口需要迁移。
5. 命令权限与命令树包结构变化，需要重做受限玩家和 OP 暂停适配。
6. Minecraft 使用 SDL3，旧 LWJGL TinyFD 文件选择器不可用。
7. 登录、状态、旧版 Ping、游戏阶段访问限制的 Mixin 目标需要逐项重新确认。

## 完成标准

- `./gradlew clean build` 成功
- Mixin audit 成功
- offline-mode：正确/错误/缺失 PSK 路径联调
- online-mode：令牌首次签发与再次证明联调
- Ed25519、登录密码、找回密码、新用户限制联调
- 服务器列表 normal/enhanced/disablePing 联调
- 密钥导入导出与冲突确认联调
