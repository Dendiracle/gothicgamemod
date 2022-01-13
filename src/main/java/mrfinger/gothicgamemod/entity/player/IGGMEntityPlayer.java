package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.capability.data.*;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public interface IGGMEntityPlayer extends IGGMEntityExperienceableWithEXPInside, IGGMEntityWithStamina, IGGMEntityCaster, IGGMEntityHumanoid
{

        @Override
        IGGMDynamicAttributeInstance getHealthAttribute();


        InventoryPlayer getInventoryPlayer();


        IGGMPlayerEquipmentAnimationFightStance getGGMEquipment();

        Container getGGMContainer();


        ItemStack getFirstHeldItem();

        ItemStack getSecondHeldItem();


        @Override
        default boolean isCanJump()
        {
                return IGGMEntityWithStamina.super.isCanJump();
        }


        boolean isUsingItem();

        boolean isUsingLH();

        void setItemInUse(ItemStack itemStack, int duration);

        void stopUsingItem();

        void clearItemInUse();


        boolean isBlocking();


        boolean isChangingStance();

        void changeStance();

        void setInFightStance(boolean b);

        void attackTargetEntityWithCurrentItem(Entity entity);


        EntityItem func_146097_a(ItemStack itemStack, boolean flag1, boolean flag2);


        void openGui(Object mod, int modGuiId, World world, int x, int y, int z);

}
