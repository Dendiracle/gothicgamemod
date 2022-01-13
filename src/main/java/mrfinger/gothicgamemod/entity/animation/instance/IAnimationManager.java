package mrfinger.gothicgamemod.entity.animation.instance;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;

import java.util.Map;
import java.util.UUID;

public interface IAnimationManager<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>>
{

    UUID getID();

    void setID(UUID id);


    IStartAnimationData getAnimationData(Entity entity, IAnimationPlayer<Entity, Animation> player);

    Animation getNewAnimationInstance();



/*    <Snapshot extends IAnimationSnapshot<Animation>> Snapshot saveAnimationToSnapshot(Animation animation);

    <Snapshot extends IAnimationSnapshot<Animation>> Animation getAnimationFromSnapshot(Snapshot snapshot);*/

    IAnimationSnapshot<Animation> saveAnimationToSnapshot(Animation animation);

    IAnimationSnapshot<Animation> getSnapshotFromBytes(ByteBuf buf);



    Animation getAnimationFromSnapshot(IAnimationSnapshot<Animation> snapshot);


    interface IStartAnimationData<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>>
    {

        boolean canStartAnimation();

        void onAnimationStarted(Animation animation);

    }


    interface IAnimationSnapshot<Animation extends IAnimation>
    {

        void loadDataForAnimation(Animation animation);

        void loadDataFromAnimation(Animation animation);


        void fromBytes(ByteBuf buf);

        void toBytes(ByteBuf buf);


        Animation getInstance();

    }

}
