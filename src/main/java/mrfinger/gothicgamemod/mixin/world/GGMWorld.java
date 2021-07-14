package mrfinger.gothicgamemod.mixin.world;

import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(World.class)
public abstract class GGMWorld implements IGGMWorld
{

    @Shadow public boolean isRemote;
    protected List<IPackEntity>[] packListsArray;

    protected Map<Fraction, Collection<IPackEntity>> packMapByFractions;
    protected Map<UUID, IPackEntity> packMapByID;

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
        this.packMapByID = new HashMap<>();
    }


    @Override
    public boolean isRemote()
    {
        return this.isRemote;
    }


    @Override
    public Map<UUID, IPackEntity> getPackMapByID()
    {
        return packMapByID;
    }

    @Override
    public IPackEntity createNewPack()
    {
        IPackEntity pack = new PackEntity(this);
        this.addPack(pack);
        return pack;
    }

    @Override
    public IPackEntity createNewPack(PackFraction fraction)
    {
        IPackEntity pack = fraction.getNewEntityPack(this);
        this.addPack(pack);
        return pack;
    }

    @Override
    public IPackEntity createNewPack(PackFraction fraction, UUID id)
    {
        IPackEntity pack = fraction.getNewEntityPack(this, id);
        this.addPack(pack);
        return pack;
    }

    @Override
    public void addPack(IPackEntity pack)
    {
        if (this.count >= packListsArray.length) this.count = 0;
        packListsArray[this.count++].add(pack);

        Collection<IPackEntity> fractions = this.packMapByFractions.get(pack.getFraction());
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

        this.packMapByID.put(pack.getId(), pack);
    }

    @Override
    public void removePack(IPackEntity pack)
    {
        this.packMapByFractions.get(pack.getFraction()).remove(pack);
        this.packMapByID.remove(pack.getId());
    }
}
