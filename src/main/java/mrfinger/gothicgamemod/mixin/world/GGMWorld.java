package mrfinger.gothicgamemod.mixin.world;

import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.util.GGMTicks;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(World.class)
public abstract class GGMWorld implements IGGMWorld {


    @Shadow public boolean isRemote;
    public List<PackEntity>[] packListsArray;

    public Map<Fraction, Collection<PackEntity>> packMapByFractions;

    private int count;


    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(CallbackInfo ci)
    {
        this.packListsArray = new List[10];

        for (int i = 0; i < packListsArray.length; ++i)
        {
            packListsArray[i] = new ArrayList<>();
        }

        this.packMapByFractions = new HashMap<>();
        this.packMapByFractions.put(GGMFractions.neutralFraction, new ArrayList<>());
    }


    @Override
    public boolean isRemote()
    {
        return this.isRemote;
    }


    @Override
    public PackEntity createNewPack()
    {
        PackEntity pack = new PackEntity(this);
        this.addPack(pack);
        return pack;
    }

    @Override
    public PackEntity createNewPack(PackFraction fraction)
    {
        PackEntity pack = fraction.getNewEntityPack(this);
        this.addPack(pack);
        return pack;
    }

    @Override
    public PackEntity findRightPack(IEntityHerd entity)
    {
        Collection<PackEntity> fractions = this.packMapByFractions.get(entity.getFraction());

        if (fractions != null)
        {
            for (PackEntity pack : fractions)
            {
                if (pack.getDistanceSQToEntity(entity) <= pack.getMaxRangeToMembers()) return pack;
            }
        }

        PackEntity pack = createNewPack(entity.getFraction());
        pack.setPos(entity.getPosX(), entity.getPosY(), entity.getPosZ());
        pack.setSize(entity.getFraction().getSimplePackRange(), entity.getFraction().getSimplePackHeight());
        return pack;
    }

    @Override
    public void addPack(PackEntity pack)
    {
        if (this.count >= packListsArray.length) this.count = 0;
        packListsArray[this.count++].add(pack);

        Collection<PackEntity> fractions = this.packMapByFractions.get(pack.getFraction());
        if (fractions != null)
        {
            fractions.add(pack);
        }
        else
        {
            fractions = new ArrayList<>();
            fractions.add(pack);
            this.packMapByFractions.put(pack.getFraction(), fractions);
        }
    }

}
