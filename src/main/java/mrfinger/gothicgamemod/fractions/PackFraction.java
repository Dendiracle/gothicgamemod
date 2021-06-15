package mrfinger.gothicgamemod.fractions;

import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;

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


    public int getStandartRevengeDuration()
    {
        return 300;
    }

}
