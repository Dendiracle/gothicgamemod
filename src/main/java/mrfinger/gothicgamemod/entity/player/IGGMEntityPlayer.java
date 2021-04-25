package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.capability.IGGMEntityExperienceable;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityCaster;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithStamina;
import mrfinger.gothicgamemod.inventory.IGGMContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public interface IGGMEntityPlayer extends IGGMEntityLivingBase, IGGMEntityExperienceable, IGGMEntityWithStamina, IGGMEntityCaster, IGGMEntityWithAttackAnim {


        @Override
        IGGMDynamicAttributeInstance getHealthAttribute();


        InventoryPlayer getInventoryPlayer();

        IGGMInventoryPlayer getGGMEquipment();

        Container getGGMContainer();


        void clearItemInUse();


        boolean isBlocking();


        int getNewAttackCooldown();

        int getAttackCooldown();

        boolean inFightStance();

        boolean isChangingStance();

        void changeStance();

        void setInFightStance(boolean b);

        void repeatAttack();

        void attackTargetEntityWithCurrentItem(Entity entity);


        EntityItem func_146097_a(ItemStack itemStack, boolean flag1, boolean flag2);


        void openGui(Object mod, int modGuiId, World world, int x, int y, int z);

}
