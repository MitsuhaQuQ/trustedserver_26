package com.mtmrails.trustedserver.fabric.mixin;

import com.mtmrails.trustedserver.fabric.server.TrustedServerRuntime;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.server.players.NameAndId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
abstract class ServerPermissionMixin {
    @Inject(method = "getProfilePermissions", at = @At("HEAD"), cancellable = true)
    private void trustedserver$maskOpLevel(NameAndId profile,
            CallbackInfoReturnable<LevelBasedPermissionSet> cir) {
        TrustedServerRuntime runtime = TrustedServerRuntime.get();
        if (runtime != null && runtime.opSuspended(profile.id())) {
            cir.setReturnValue(LevelBasedPermissionSet.forLevel(PermissionLevel.ALL));
        }
    }
}
