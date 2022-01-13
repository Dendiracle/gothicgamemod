package mrfinger.gothicgamemod.entity.effect.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.effect.IGGMEffectManager;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.item.ItemStack;

public interface IGGMEffectInstance<Manager extends IGGMEffectManager<Entity>, Entity extends IGGMEntityLivingBase>
{

    Manager getGenericEffect();

    Entity getEntity();


    void onSetsToEntity(Entity entity, IGGMEffectInstance oldEffect);


    void onEntityUpdate();


    default void onRemoveEffect(IGGMEffectInstance newEffect) {}


    default void removeEffect()
    {
        this.getEntity().removeEffect(this.getGenericEffect());
    }


    default boolean allowDigging()
    {
        return true;
    }

    default int onSetItemInUse(ItemStack itemStack, int duration)
    {
        return duration;
    }

    default int onItemUseUpdate(ItemStack itemStack, int count)
    {
        return count;
    }

    default boolean onStopItemUsing(ItemStack itemStack, int count)
    {
        return false;
    }

    default ItemStack onItemUseFinish(ItemStack origin, int count, ItemStack result)
    {
        return result;
    }


    default IGGMDamageSource onAttackTarget(IGGMEntity target, IGGMDamageSource damageSource, float damage)
    {
        return damageSource;
    }

    default void onKillEntity(IGGMEntityLivingBase target) {}


    default boolean onAttackEntityFrom(IGGMDamageSource damageSource, float damage)
    {
        return false;
    }

    default void onDeath(IGGMDamageSource damageSource) {}


    default boolean onMountEntity(IGGMEntity mount)
    {
        return false;
    }


    default void onJumped() {}


    /*default float getNewBreakSpeed(Block block, int meta, float originSpeed, float newSpeed, int x, int y, int z)
    {
        return newSpeed;
    }*/

}
