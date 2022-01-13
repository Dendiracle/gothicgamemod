package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public interface IAnimationHelper<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>> extends IAnimation<Entity>, IAnimationPlayer<Entity, Animation>
{

    @Override
    default void onSet(Entity entity, IAnimationPlayer animationPlayer)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onSet(entity, animationPlayer);
    }


    @Override
    default void updateAnimation(IAnimationPlayer animationPlayer)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().updateAnimation(animationPlayer);
    }


    @Override
    default boolean isCanAnimationWillChangedFor(IAnimationManager manager)
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().isCanAnimationWillChangedFor(manager);
    }

    @Override
    default boolean isCanAnimationWillChangedFor(IAnimation animation)
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().isCanAnimationWillChangedFor(animation);
    }

    @Override
    default void onRemoveAnimation(Entity entity, IAnimationPlayer animationPlayer)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onRemoveAnimation(entity, animationPlayer);
    }


    default int getDuration()
    {
        return this.getActiveAnimation() != null ? this.getActiveAnimation().getDuration() : 0;
    }

    default int getCountdown()
    {
        return this.getActiveAnimation() != null ? this.getActiveAnimation().getCountdown() : 0;
    }


    default boolean isHurtingAnimation()
    {
        return this.getActiveAnimation() != null && this.getActiveAnimation().isHurtingAnimation();
    }


    @Override
    default void controlEntityMovement()
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().controlEntityMovement();
    }


    @Override
    default boolean blockMovement()
    {
        return this.getActiveAnimation() != null && this.getActiveAnimation().blockMovement();
    }


    @Override
    default boolean allowDropItems()
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowDropItems();
    }

    @Override
    default boolean allowDigging()
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowDigging();
    }

    @Override
    default boolean allowUsingItems()
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowUsingItems();
    }

    @Override
    default boolean allowSetItemInUse(ItemStack itemStack, int duration)
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowSetItemInUse(itemStack, duration);
    }

    @Override
    default void onItemUseSetted(ItemStack itemStack, int duration)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onItemUseSetted(itemStack, duration);
    }

    @Override
    default void onUsingItem(ItemStack itemStack, int duration)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onUsingItem(itemStack, duration);
    }

    @Override
    default boolean allowChangeItem()
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowChangeItem();
    }


    @Override
    default boolean allowJump()
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowJump();
    }

    @Override
    default void onJumped()
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onJumped();
    }


    @Override
    default void onTakingDamage(IGGMDamageSource damageSource, float damage)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onTakingDamage(damageSource, damage);
    }

    @Override
    default void onDeath(IGGMDamageSource damageSource)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().onDeath(damageSource);
    }


    @Override
    default boolean allowMount(IGGMEntity mount)
    {
        return this.getActiveAnimation() == null || this.getActiveAnimation().allowMount(mount);
    }


    @Override
    default void animateModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.getActiveAnimation() != null) this.getActiveAnimation().animateModel(model, f0, f1, tickRate);
    }

}
