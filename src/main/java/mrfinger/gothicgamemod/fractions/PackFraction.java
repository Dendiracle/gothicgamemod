package mrfinger.gothicgamemod.fractions;

import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class PackFraction extends Fraction
{

    protected final float simplePackRange;

    protected final float spaceForEntity;

    protected final float simplePackHeight;


    public PackFraction(String unlocalizedName,float simplePackRange, float spaceForEntity)
    {
        this(unlocalizedName, simplePackRange, spaceForEntity, 4.0F);
    }

    public PackFraction(String unlocalizedName,float simplePackRange, float spaceForEntity, float simplePackHeight)
    {
        super(unlocalizedName);

        this.simplePackRange = simplePackRange;
        this.spaceForEntity = spaceForEntity;
        this.simplePackHeight = simplePackHeight;
    }


    public float getSimplePackRange()
    {
        return this.simplePackRange;
    }

    public float getSpaceForEntity()
    {
        return spaceForEntity;
    }

    public float getSimplePackHeight()
    {
        return this.simplePackHeight;
    }


    public PackEntity getNewEntityPack(IGGMWorld world)
    {
        return new PackEntity(world, this);
    }

    public PackEntity getNewEntityPack(IGGMWorld world, UUID id)
    {
        return new PackEntity(world, id, this, null);
    }


    public PackEntity getPackEntityFromNBT(NBTTagCompound nbt, UUID id, IEntityHerd entity)
    {
        PackEntity pack = new PackEntity(entity.getEntityWorld(), id, entity.getFraction(), entity);
        pack.readPackFromNBT(nbt);
        pack.addEntityToPack(entity);
        return pack;
    }


    public int getStandartRevengeDuration()
    {
        return 300;
    }

}
