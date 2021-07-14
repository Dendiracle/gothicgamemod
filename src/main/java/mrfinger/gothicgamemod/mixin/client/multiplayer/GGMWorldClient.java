package mrfinger.gothicgamemod.mixin.client.multiplayer;

import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.mixin.world.GGMWorld;
import mrfinger.gothicgamemod.util.GGMTicks;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(WorldClient.class)
public abstract class GGMWorldClient extends GGMWorld
{

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci)
    {
        int index = GGMTicks.gTicks % 10;
        List<IPackEntity> listToRemove = new ArrayList<>();
        List<IPackEntity> list = packListsArray[index];

        for (IPackEntity pack : list)
        {
            if (pack.isPackToRemove()) listToRemove.add(pack);
        }

        if (!listToRemove.isEmpty())
        {
            list.removeAll(listToRemove);

            for (IPackEntity pack : listToRemove)
            {
                this.removePack(pack);
            }
        }
    }




}
