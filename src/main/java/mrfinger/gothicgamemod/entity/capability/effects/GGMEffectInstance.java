package mrfinger.gothicgamemod.entity.capability.effects;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public abstract class GGMEffectInstance implements IGGMEffectInstance
{

    protected final IGGMEffect genericEffect;

    protected IGGMEntityLivingBase entity;


    public GGMEffectInstance(IGGMEffect genericEffect)
    {
        this.genericEffect = genericEffect;
        this.entity = entity;
    }


    public GGMEffectInstance(IGGMEffect genericEffect, IGGMEntityLivingBase entity)
    {
        this(genericEffect);

        this.entity = entity;
    }


    @Override
    public IGGMEffect getGenericEffect()
    {
        return this.genericEffect;
    }

    @Override
    public IGGMEntityLivingBase getEntity()
    {
        return entity;
    }


    @Override
    public IGGMEffectInstance setEntity(IGGMEntityLivingBase entity)
    {
        this.entity = entity;

        return this;
    }

    @Override
    public void onEntityUpdate()
    {

    }

}
