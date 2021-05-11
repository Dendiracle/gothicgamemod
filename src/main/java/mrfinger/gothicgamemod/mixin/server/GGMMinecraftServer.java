package mrfinger.gothicgamemod.mixin.server;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public class GGMMinecraftServer
{

    /*@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;updateTimeLightAndEntities()V"))
    private void onServerTick(CallbackInfo ci)
    {
        PackEntity.onTick();
    }*/

}
