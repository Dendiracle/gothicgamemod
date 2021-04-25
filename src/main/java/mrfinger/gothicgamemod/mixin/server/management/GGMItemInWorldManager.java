package mrfinger.gothicgamemod.mixin.server.management;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemInWorldManager.class)
public class GGMItemInWorldManager {


    @Redirect(method = "tryUseItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;mainInventory:[Lnet/minecraft/item/ItemStack;", opcode = Opcodes.PUTFIELD))
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
    }

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


}
