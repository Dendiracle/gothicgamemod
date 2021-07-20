package mrfinger.gothicgamemod.fractions;

import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class PackFraction extends Fraction
{

    protected final float simplePackRange;

    protected final float simplePackHeight;

    protected final int maxMembersInPack;


    public PackFraction(String unlocalizedName, float simplePackRange, int maxMembersInPack)
    {
        this(unlocalizedName, simplePackRange, 4.0F, maxMembersInPack);
    }

    public PackFraction(String unlocalizedName,float simplePackRange, float simplePackHeight, int maxMembersInPack)
    {
        super(unlocalizedName);

        this.simplePackRange = simplePackRange;
        this.simplePackHeight = simplePackHeight;
        this.maxMembersInPack = maxMembersInPack;
    }


    public float getSimplePackRange()
    {
        return this.simplePackRange;
    }

    public float getSimplePackHeight()
    {
        return this.simplePackHeight;
    }


    public IPackEntity getNewEntityPack(IGGMWorld world)
    {
        return new PackEntity(world, this);
    }

    protected IPackEntity getNewEntityPack(IGGMWorld world, UUID id)
    {
        return new PackEntity(world, id, this);
    }


    public IPackEntity getPackEntityFromNBT(IGGMWorld world, NBTTagCompound compound)
    {
        IPackEntity pack = this.getNewEntityPack(world, UUID.fromString(compound.getString("id")));
        pack.readPackFromNBT(compound);
        return pack;
    }

    public void writePackToNBT(IPackEntity pack, NBTTagCompound compound)
    {
        compound.setString("id", pack.getId().toString());
        pack.writePackToNBT(compound);
    }


    public int getStandartRevengeDuration()
    {
        return 300;
    }

}
