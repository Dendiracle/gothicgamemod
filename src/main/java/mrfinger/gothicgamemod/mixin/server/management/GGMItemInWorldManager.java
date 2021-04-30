package mrfinger.gothicgamemod.mixin.server.management;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInWorldManager.class)
public abstract class GGMItemInWorldManager {


    /*@Redirect(method = "tryUseItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;mainInventory:[Lnet/minecraft/item/ItemStack;", opcode = Opcodes.PUTFIELD))
    private void fixUsingItem1(ItemStack[] mainInventory, int index, ItemStack itemStack, EntityPlayer playerS, World world, ItemStack originalStack) {

        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerS;

        if (player.inFightStance()) {

            int slotIndex = player.getGGMEquipment().getCurrentItemIndex() * 2;
            if (player.getGGMEquipment().getSecHeldItem() != null) ++slotIndex;
            player.getGGMEquipment().setInventorySlotContents(slotIndex, itemStack);
        }
        else {

            mainInventory[index] = itemStack;
        }
        System.out.println("GGMItemInWorldManager " + mainInventory[index]);
    }*/

    @Shadow public abstract boolean isCreative();

    @Shadow public EntityPlayerMP thisPlayerMP;

    @Inject(method = "tryUseItem", at = @At(value = "HEAD"), cancellable = true)
    private void fixUsingItem1(EntityPlayer playerS, World world, ItemStack originalStack, CallbackInfoReturnable<Boolean> cir) {

        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerS;

        if (player.inFightStance())
        {

            int i = originalStack.stackSize;
            int j = originalStack.getItemDamage();
            ItemStack itemstack1 = originalStack.useItemRightClick(world, playerS);

            if (itemstack1 == originalStack && (itemstack1 == null || itemstack1.stackSize == i && itemstack1.getMaxItemUseDuration() <= 0 && itemstack1.getItemDamage() == j))
            {
                cir.setReturnValue(false);
            }
            else
            {

                int slotIndex = player.getGGMEquipment().getCurrentItemIndex() * 2;
                if (player.isUsingLH()) ++slotIndex;
                player.getGGMEquipment().setInventorySlotContents(slotIndex, itemstack1);

                if (this.isCreative())
                {
                    itemstack1.stackSize = i;

                    if (itemstack1.isItemStackDamageable())
                    {
                        itemstack1.setItemDamage(j);
                    }
                }

                if (itemstack1.stackSize == 0)
                {
                    player.getGGMEquipment().setInventorySlotContents(slotIndex, null);
                    MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thisPlayerMP, itemstack1));
                }

                if (!playerS.isUsingItem())
                {
                    player.sendContainerToPlayer(player.getGGMContainer());
                }
            }

            cir.setReturnValue(true);
        }
    }

    /*@Inject(method = "tryUseItem", at = @At(value = "RETURN", ordinal = 1))
    private void fixUsingItem1(EntityPlayer playerS, World world, ItemStack originalStack, CallbackInfoReturnable cir)
    {
        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerS;

        if (player.inFightStance())
        {
            player.getInventoryPlayer().mainInventory[player.getInventoryPlayer().currentItem] = originalStack;
        }

        System.out.println("GGMItemInWorldManager 2" + player.getInventoryPlayer().mainInventory[player.getInventoryPlayer().currentItem]);
    }*/

    @Redirect(method = "tryUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayerMP;sendContainerToPlayer(Lnet/minecraft/inventory/Container;)V"))
    private void fixUsingItem2(EntityPlayerMP playerS, Container containerPlayer) {

        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerS;

        if (player.inFightStance()) {

            player.sendContainerToPlayer(player.getGGMContainer());
        }
        else {

            player.sendContainerToPlayer(containerPlayer);
        }
    }

    @Inject(method = "tryUseItem", at = @At("TAIL"))
    private void debug1(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_, CallbackInfoReturnable cir)
    {
        System.out.println("GGMItemInWorldManager TAIL " + p_73085_1_.inventory.mainInventory[p_73085_1_.inventory.currentItem]);
    }
    /*@Inject(method = "tryUseItem", at = @At("HEAD"))
    private void debug1(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_, CallbackInfoReturnable cir)
    {
        System.out.println("GGMItemInWorldManager head");
    }

    @Inject(method = "tryUseItem", at = @At(value = "RETURN", opcode = 0))
    private void debug2(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_, CallbackInfoReturnable cir)
    {
        System.out.println("GGMItemInWorldManager ret false");
    }

    @Inject(method = "tryUseItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;inventory:Lnet/minecraft/entity/player/InventoryPlayer;", ordinal = 0))
    private void debug3(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_, CallbackInfoReturnable cir)
    {
        System.out.println("GGMItemInWorldManager preInventory: " + p_73085_1_.inventory.mainInventory[p_73085_1_.inventory.currentItem]);
    }

    @Inject(method = "tryUseItem", at = @At(value = "JUMP", ordinal = 2))
    private void debug4(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_, CallbackInfoReturnable cir)
    {
        System.out.println("GGMItemInWorldManager postInventory: " + p_73085_1_.inventory.mainInventory[p_73085_1_.inventory.currentItem]);
    }*/


}
