package mrfinger.gothicgamemod.mixin.common.server.management;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInWorldManager.class)
public abstract class GGMItemInWorldManager {


    /*@Redirect(method = "tryUseItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;mainInventory:[Lnet/minecraft/item/ItemStack;", opcode = Opcodes.PUTFIELD))
    private void fixUsingItem1(ItemStack[] mainInventory, int index, ItemStack itemStack, EntityPlayer playerS, World world, ItemStack originalStack) {

        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerS;

        if (player.inFightStance()) {

            int slotIndex = player.getGGMEquipment().getCurrentItemIndex() * 2;
            if (player.getGGMEquipment().getSecondHeldItem() != null) ++slotIndex;
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
            player.getGGMEquipment().setUseItem();

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
    private void fixUsingItem2(EntityPlayerMP playerS, Container containerPlayer)
    {
        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerS;

        if (player.inFightStance())
        {
            player.sendContainerToPlayer(player.getGGMContainer());
        }
        else
        {

            player.sendContainerToPlayer(containerPlayer);
        }
    }


}
