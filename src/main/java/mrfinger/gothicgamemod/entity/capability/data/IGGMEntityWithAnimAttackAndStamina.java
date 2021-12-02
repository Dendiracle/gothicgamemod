package mrfinger.gothicgamemod.entity.capability.data;

import net.minecraft.entity.EntityLivingBase;

public interface IGGMEntityWithAnimAttackAndStamina extends IGGMEntityWithAnimAttack, IGGMEntityWithStamina
{

    @Override
    default boolean canMeleeAttackAnimatedly()
    {
        return this.getStaminaSpendingFromAnimatedAttack() <= this.getStaminaAttribute().getDynamicValue();
    }

    float getStaminaSpendingFromAnimatedAttack();

    @Override
    default void onAnimatedMeleeAttack()
    {
        this.staminaOnAnimatedMeleeAttack();
    }

    default void staminaOnAnimatedMeleeAttack()
    {
        if (!((EntityLivingBase) this).worldObj.isRemote)
        {
            this.getStaminaAttribute().changeCurrentValue(-this.getStaminaSpendingFromAnimatedAttack());
        }
    }

}
