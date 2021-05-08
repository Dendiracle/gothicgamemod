package mrfinger.gothicgamemod.mixin.server;

import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class GGMMinecraftServer
{

    /*@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;updateTimeLightAndEntities()V"))
    private void onServerTick(CallbackInfo ci)
    {
        PackEntity.onTick();
    }*/

}
