package mrfinger.gothicgamemod.entity.effect;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;

public interface IGGMEffectManager<Entity extends IGGMEntityLivingBase>
{

    int getID();

    String getUnlocalizedName();


    boolean isCanEffectBeApplied(Entity entity);

    IGGMEffectInstance getNewEffectInstance(Entity entity);


    IGGMEffectSnapshot getEffectSnapshot(IGGMEffectInstance instance);

    IGGMEffectSnapshot getSnapshotFromBytes(ByteBuf buf);


    interface IGGMEffectSnapshot<Effect extends IGGMEffectInstance>
    {

        void loadDataForEffectInstance(Effect animation);

        void loadDataFromEffectInstance(Effect animation);


        void fromBytes(ByteBuf buf);

        void toBytes(ByteBuf buf);


        Effect getInstance();

    }

}
