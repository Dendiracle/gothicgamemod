package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public interface IAnimation {


    IGGMEntityLivingBase getEntity();

    void setEntity(IGGMEntityLivingBase entity);


    String getUnlocalizedName();


    void updateAnimation();


    default IAnimation onSetNewAnimation(IAnimation animation)
    {
        return this;
    }

    boolean tryEndAnimation();

    default void onEndAnimation() {}


    IAnimationEpisode getEpisode();

    int getEpisodeDuration();

    int getEpisodeCount();


    boolean setAnimationEpisode(IAnimationEpisode animationEpisode,int duration);

    boolean setAnimationEpisode(String episodeName, int duration);

    default void setEpisodeCount(int count) {}

    void clearAnimationEpisode();


    default void setMoveControl(float forward, float strafe) {}

    default void setRotationControl(float yaw, float pitch) {}

    default void controlMove() {}

    default void controlRotation() {}


    default boolean denyMovement()
    {
        return true;
    }


    default boolean denyDropItems()
    {
        return true;
    }

    default boolean denyDigging()
    {
        return true;
    }

    default boolean denyUsingItems()
    {
        return true;
    }

    default boolean denySetItemInUse(ItemStack itemStack, int duration)
    {
        return true;
    }

    default void onItemUseSetted(ItemStack itemStack, int duration) {}

    default void onUsingItem(ItemStack itemStack, int duration) {}

    default boolean denyChangeItem()
    {
        return true;
    }


    default void onTakingDamage(IGGMDamageSource damageSource, float damage) {}

    default void onDeath(IGGMDamageSource damageSource) {}


    default boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return true;
    }

    default boolean denyJump()
    {
        return true;
    }

    default void onJumped() {}


    default void modifyModel(ModelBase model, float f0, float f1, float tickRate) {}

}
