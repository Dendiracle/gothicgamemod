package mrfinger.gothicgamemod.mixin.world;

import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.util.GGMTicks;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(WorldServer.class)
public abstract class GGMWorldServer extends GGMWorld
{

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 4))
    private void onTick(CallbackInfo ci)
    {
        this.habitatsCollection.tick();
    }

}
