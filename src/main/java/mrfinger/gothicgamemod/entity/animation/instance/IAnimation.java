package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public interface IAnimation<Entity extends IGGMEntityLivingBase>
{

    IAnimationManager getAnimationManager();


    Entity getEntity();

    void onSet(Entity entity, IAnimationPlayer player);


    void updateAnimation(IAnimationPlayer animationPlayer);


    boolean isCanAnimationWillChangedFor(IAnimationManager manager);

    boolean isCanAnimationWillChangedFor(IAnimation animation);

    void onRemoveAnimation(Entity entity, IAnimationPlayer animationPlayer);


    default int getDuration()
    {
        return -1;
    }

    default int getCountdown()
    {
        return -1;
    }

    default void setDuration(int value) {}

    default void resizeDuration(float value) {}

    default void setCountdown(int value) {}


    default boolean isHurtingAnimation()
    {
        return false;
    }


    default void controlEntityMovement() {}


    default boolean blockMovement()
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

    default boolean allowSetItemInUse(ItemStack itemStack, int duration)
    {
        return true;
    }

    default void onItemUseSetted(ItemStack itemStack, int duration) {}

    default void onUsingItem(ItemStack itemStack, int duration) {}

    default boolean allowChangeItem()
    {
        return true;
    }


    default boolean allowJump()
    {
        return true;
    }

    default void onJumped() {}


    default void onTakingDamage(IGGMDamageSource damageSource, float damage) {}

    default void onDeath(IGGMDamageSource damageSource) {}


    default boolean allowMount(IGGMEntity mount)
    {
        return true;
    }


    default void animateModel(ModelBase model, float f0, float f1, float tickRate) {}

}
