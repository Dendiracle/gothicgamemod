package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface IAnimation {


    IGGMEntityLivingBase getEntity();

    void setEntity(IGGMEntityLivingBase entity);


    void onUpdate();


    default IAnimation onSetNewAnimation(IAnimation animation)
    {
        return this;
    }

    boolean tryEndAnimation();

    default void onEndAnimation() {}


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
}
