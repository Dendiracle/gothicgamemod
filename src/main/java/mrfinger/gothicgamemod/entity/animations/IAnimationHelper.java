package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public interface IAnimationHelper<Entity extends IGGMEntityLivingBase, Episode extends IAnimationEpisode>
{

    Entity getEntity();

    void setEntity(Entity entity);


    String getUnlocalizedName();


    void updateAnimation();


    boolean isCanAnimationHelperWillChanged();

    default void onChangeAnimationHelper()
    {
        if (this.getAnimationEpisode() != null) this.clearAnimationEpisode();
    }


    Episode getAnimationEpisode();

    int getEpisodeDuration();

    int getEpisodeCountdown();


    boolean setAnimationEpisode(Episode animationEpisode);

    boolean setAnimationEpisode(Episode animationEpisode, int duration);

    boolean setAnimationEpisode(String episodeName, int duration);

    void setEpisodeCountdown(int countDown);

    boolean breakAnimationEpisode();

    void clearAnimationEpisode();


    default void setRotationControl(float yaw, float pitch) {}

    default void controlMove() {}

    default void controlRotation() {}


    default boolean denyMovement()
    {
        return true;
    }


    default boolean allowDropItems()
    {
        return true;
    }

    default boolean allowDigging()
    {
        return true;
    }

    default boolean allowUsingItems()
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


    default boolean allowJump()
    {
        return true;
    }

    default void onJumped() {}


    default void onTakingDamage(IGGMDamageSource damageSource, float damage) {}

    default void onDeath(IGGMDamageSource damageSource)
    {
        if (this.getAnimationEpisode() != null) this.clearAnimationEpisode();
    }


    default boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return true;
    }


    default void modifyModel(ModelBase model, float f0, float f1, float tickRate) {}

}
