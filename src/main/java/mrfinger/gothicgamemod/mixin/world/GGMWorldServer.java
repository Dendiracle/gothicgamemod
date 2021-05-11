package mrfinger.gothicgamemod.mixin.world;

import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.util.GGMTicks;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(WorldServer.class)
public abstract class GGMWorldServer extends GGMWorld {


    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci)
    {
        int index = GGMTicks.gTicks % 10;
        List<PackEntity> listToRemove = new ArrayList<>();
        List<PackEntity> list = packListsArray[index];

        for (PackEntity pack : list)
        {
            pack.onHalfSecUpdate();
            if (pack.getEntitiesSet().isEmpty()) listToRemove.add(pack);
        }

        if (!listToRemove.isEmpty()) list.removeAll(listToRemove);
    }

}
