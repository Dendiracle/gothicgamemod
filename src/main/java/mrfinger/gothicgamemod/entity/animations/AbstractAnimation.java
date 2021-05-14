package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public abstract class AbstractAnimation implements IAnimation {


    protected IGGMEntityLivingBase entity;


    public AbstractAnimation()
    {

    }

    public AbstractAnimation(IGGMEntityLivingBase entity)
    {
        this.entity = entity;
    }

    @Override
    public IGGMEntityLivingBase getEntity()
    {
        return this.entity;
    }

    @Override
    public void setEntity(IGGMEntityLivingBase entity)
    {
        this.entity = entity;
    }

}
