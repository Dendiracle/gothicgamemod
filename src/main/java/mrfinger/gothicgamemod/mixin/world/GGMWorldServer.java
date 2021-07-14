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

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci)
    {
        int index = GGMTicks.gTicks % 10;
        List<IPackEntity> listToRemove = new ArrayList<>();
        List<IPackEntity> list = packListsArray[index];

        for (IPackEntity pack : list)
        {
            pack.onUpdatePackAI();
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

    @Override
    public IPackEntity findRightPack(IEntityHerd entity)
    {
        Collection<IPackEntity> fractions = this.packMapByFractions.get(entity.getFraction());

        if (fractions != null)
        {
            for (IPackEntity pack : fractions)
            {
                float maxRangeSQ = pack.getMaxRangeToMembers();
                maxRangeSQ *= maxRangeSQ;
                if (pack.getDistanceSQToEntity(entity) <= maxRangeSQ) return pack;
            }
        }

        IPackEntity pack = createNewPack(entity.getFraction());
        pack.setPos(entity.getPosX(), entity.getPosY(), entity.getPosZ());
        pack.setSize(entity.getFraction().getSimplePackRange(), entity.getFraction().getSimplePackHeight());
        return pack;
    }

}
