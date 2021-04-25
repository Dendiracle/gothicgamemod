package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import net.minecraft.item.ItemStack;

public interface IGGMEntityWithStamina extends IGGMEntityLivingBase {


    IGGMDynamicAttributeInstance getStaminaAttribute();

    float getStaminaSpending();

    default boolean canJump() {

        IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
        float d = this.getStaminaSpending() * 12.0F;
        double dd = stamina.getCurrentValue();

        if (dd < d) {
            return false;
        }

        if (!thisEntity().worldObj.isRemote) stamina.changeCurrentValue(-d);
        return true;
    }


    default void sprintUpdate() {

        IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
        double d = this.getStaminaSpending();
        double dd = stamina.getCurrentValue();
        if (dd < d) {
            this.setSprinting(false);
            this.setDisallowSprintTimer(60);
        }
        else if (!thisEntity().worldObj.isRemote) {
            stamina.changeCurrentValue(-d);
        }

    }

    default boolean canAttack() {

        IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
        float d = 1.0F;
        ItemStack item = this.getEquipmentInSlot(0);

        if (item != null && item.getItem() instanceof IItemMeleeWeapon) {
            d += 2.0F;
        }

        if (stamina.getCurrentValue() < d) {
            return false;
        }

        if (!thisEntity().worldObj.isRemote) stamina.changeCurrentValue(-d);
        return true;
    }



}
