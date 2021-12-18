package mrfinger.gothicgamemod.entity.animation.manager;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

import java.util.UUID;

public abstract class AbstractAnimationManager<Entity extends IGGMEntityLivingBase> implements IAnimationManager<Entity>
{

    protected final UUID uuid;

    protected AbstractAnimationManager(UUID uuid)
    {
        this.uuid = uuid;
    }


    @Override
    public UUID getID()
    {
        return this.uuid;
    }
}
