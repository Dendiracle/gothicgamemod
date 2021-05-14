package mrfinger.gothicgamemod.entity.capability.effects;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IGGMEffectInstance
{

    IGGMEffect getGenericEffect();

    IGGMEntityLivingBase getEntity();


    IGGMEffectInstance setEntity(IGGMEntityLivingBase entity);


    void onEntityUpdate();


    default IGGMEffectInstance onSetNewEffect(IGGMEffectInstance origin, IGGMEffectInstance result)
    {
        return result;
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


    default double onJump(double originMotion, double resultMotion)
    {
        return resultMotion;
    }


    /*default float getNewBreakSpeed(Block block, int meta, float originSpeed, float newSpeed, int x, int y, int z)
    {
        return newSpeed;
    }*/

}
