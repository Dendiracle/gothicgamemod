package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.manager.IAnimationManager;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public interface IAnimation<Entity extends IGGMEntityLivingBase>
{

    IAnimationManager getAnimationManager();


    Entity getEntity();

    void setEntity(Entity entity);


    void updateAnimation(IAnimationPlayer animationPlayer);


    boolean isCanAnimationHelperWillChanged();

    void onRemoveAnimation(Entity entity);


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


    default void modifyModel(ModelBase model, float f0, float f1, float tickRate) {}

}
