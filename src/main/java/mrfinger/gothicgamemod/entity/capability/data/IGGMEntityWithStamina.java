package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;

public interface IGGMEntityWithStamina extends IGGMEntityLivingBase {


    IGGMDynamicAttributeInstance getStaminaAttribute();

    float getStaminaSpendingFromJump();

    default boolean canJump()
    {
        IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
        float d = this.getStaminaSpendingFromJump();
        double dd = stamina.getCurrentValue();

        if (dd < d) {
            return false;
        }

        if (!thisEntity().worldObj.isRemote) stamina.changeCurrentValue(-d);
        return true;
    }


    float getStaminaSpendingOnSprint();

    default void sprintUpdate()
    {
        IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
        double d = this.getStaminaSpendingOnSprint();
        double dd = stamina.getCurrentValue();
        if (dd < d)
        {
            this.setSprinting(false);
            this.setDisallowSprintTimer((short) 60);
        }
        else if (!thisEntity().worldObj.isRemote)
        {
            stamina.changeCurrentValue(-d);
        }

    }


    float getStaminaSpendingFromAttack();

    default boolean canAttack()
    {
        IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
        float d = this.getStaminaSpendingFromAttack();

        if (stamina.getCurrentValue() < d) {
            return false;
        }

        if (!thisEntity().worldObj.isRemote) stamina.changeCurrentValue(-d);
        return true;
    }



}
