package com.mtmrails.trustedserver.fabric.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.RootCommandNode;
import com.mtmrails.trustedserver.fabric.server.TrustedServerRuntime;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
abstract class RestrictedCommandsMixin {
    @Inject(method = "performCommand", at = @At("HEAD"), cancellable = true)
    private void trustedserver$blockRestrictedCommand(ParseResults<CommandSourceStack> command, String input,
            CallbackInfo ci) {
        TrustedServerRuntime runtime = TrustedServerRuntime.get();
        CommandSourceStack source = command.getContext().getSource();
        if (runtime != null && source.getEntity() instanceof ServerPlayer player && runtime.restricted(player)) {
            source.sendFailure(Component.literal("请先使用 /passwd 设置登录密码，以解除指令限制"));
            ci.cancel();
        }
    }

    @Inject(method = "sendCommands", at = @At("HEAD"), cancellable = true)
    private void trustedserver$sendEmptyCommandTree(ServerPlayer player, CallbackInfo ci) {
        TrustedServerRuntime runtime = TrustedServerRuntime.get();
        if (runtime != null && runtime.restricted(player)) {
            player.connection.send(new ClientboundCommandsPacket(new RootCommandNode<>(),
                    new ClientboundCommandsPacket.NodeInspector<Object>() {
                        @Override
                        public Identifier suggestionId(com.mojang.brigadier.tree.ArgumentCommandNode<Object, ?> node) {
                            return null;
                        }

                        @Override
                        public boolean isExecutable(com.mojang.brigadier.tree.CommandNode<Object> node) {
                            return false;
                        }

                        @Override
                        public boolean isRestricted(com.mojang.brigadier.tree.CommandNode<Object> node) {
                            return false;
                        }
                    }));
            ci.cancel();
        }
    }
}
