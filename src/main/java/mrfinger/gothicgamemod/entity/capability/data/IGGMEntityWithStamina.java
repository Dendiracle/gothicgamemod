package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

public interface IGGMEntityWithStamina extends IGGMEntityLivingBase
{

    IGGMDynamicAttributeInstance getStaminaAttribute();


    @Override
    default boolean canJump()
    {
        return this.getActiveAnimation().allowJump() && this.getStaminaSpendingFromJump() <= this.getStaminaAttribute().getDynamicValue();
    }

    float getStaminaSpendingFromJump();

    @Override
    default void onJump()
    {
        this.getActiveAnimation().onJumped();
        this.staminaOnJump();
    }

    default void staminaOnJump()
    {
        if (!((EntityLivingBase) this).worldObj.isRemote)
        {
            this.getStaminaAttribute().changeCurrentValue(-this.getStaminaSpendingFromJump());
        }
    }


    float getStaminaSpendingOnSprint();

    @Override
    default void moveUpdate()
    {
        this.staminaMoveUpdate();
    }

    default void staminaMoveUpdate()
    {
        if (((EntityLivingBase) this).isSprinting() && ((EntityLivingBase) this).moveForward > this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).getBaseValue())
        {
            IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
            double d = this.getStaminaSpendingOnSprint();
            double dd = stamina.getDynamicValue();
            if (dd < d)
            {
                ((EntityLivingBase) this).setSprinting(false);
            }
            else if (!((EntityLivingBase) this).worldObj.isRemote)
            {
                stamina.changeCurrentValue(-d);
            }
        }
    }

}
