package mrfinger.gothicgamemod.entity.animation.instance;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;

import java.util.UUID;

public abstract class AbstractAnimationManager<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>> implements IAnimationManager<Entity, Animation>
{

    protected UUID uuid;


    @Override
    public UUID getID()
    {
        return this.uuid;
    }

    @Override
    public void setID(UUID id)
    {
        if (this.uuid != null)
        {
            throw new UnsupportedOperationException("This operation must be don't use after id is setted");
        }

        this.uuid = id;
    }


    @Override
    public IStartAnimationData getAnimationData(Entity entity, IAnimationPlayer<Entity, Animation> player)
    {
        return new StartAnimationData();
    }


    @Override
    public IAnimationSnapshot<Animation> saveAnimationToSnapshot(Animation animation)
    {
        IAnimationSnapshot<Animation> snapshot = this.getNewSnapshot();
        snapshot.loadDataFromAnimation(animation);
        return snapshot;
    }

    @Override
    public IAnimationSnapshot<Animation> getSnapshotFromBytes(ByteBuf buf)
    {
        IAnimationSnapshot<Animation> snapshot = this.getNewSnapshot();
        snapshot.fromBytes(buf);
        return snapshot;
    }

    protected IAnimationSnapshot<Animation> getNewSnapshot()
    {
        return new AnimationSnapshot();
    }


    @Override
    public Animation getAnimationFromSnapshot(IAnimationSnapshot<Animation> snapshot)
    {
        Animation animation = this.getNewAnimationInstance();
        snapshot.loadDataForAnimation(animation);
        return animation;
    }


    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof AbstractAnimationManager && ((AbstractAnimationManager) obj).getID().equals(this.uuid);
    }


    public static class StartAnimationData<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>> implements IStartAnimationData<Entity, Animation>
    {

        @Override
        public boolean canStartAnimation()
        {
            return true;
        }

        @Override
        public void onAnimationStarted(Animation animation) {}

    }

    public class AnimationSnapshot implements IAnimationSnapshot<Animation>
    {

        public AnimationSnapshot() {}


        @Override
        public void loadDataForAnimation(Animation animation) {}

        @Override
        public void loadDataFromAnimation(Animation animation) {}


        @Override
        public void fromBytes(ByteBuf buf) {}

        @Override
        public void toBytes(ByteBuf buf) {}


        @Override
        public Animation getInstance()
        {
            Animation animation = AbstractAnimationManager.this.getNewAnimationInstance();
            this.loadDataForAnimation(animation);
            return animation;
        }

    }

}
