package mrfinger.gothicgamemod.mixin.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.multiplayer.IGGMPlayerControllerMP;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketStartAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(PlayerControllerMP.class)
public class GGMPlayerControllerMP implements IGGMPlayerControllerMP {


    @Shadow @Final private Minecraft mc;

    private int attackPenalty;


    @Override
    public int getAttackPenalty() {
        return this.attackPenalty;
    }

    @Override
    public void startAttack() {

        if (this.attackPenalty <= 0) {

            if (player().getAttackTicksLeft() <= 1) {

                PacketDispatcher.sendToServer(new CPacketStartAttack((short) 0));
                player().startAttack();
            }
            else {

                this.attackPenalty = player().getLastAttackDuration() + player().getAttackTicksLeft();
            }
        }


    }


    @Inject(method = "updateController", at = @At("TAIL"))
    private void onUpdate(CallbackInfo ci) {

        if (this.attackPenalty > 0) {
            --this.attackPenalty;
        }
    }


    @ModifyArg(method = "sendUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/client/C08PacketPlayerBlockPlacement;<init>(IIIILnet/minecraft/item/ItemStack;FFF)V"))
    private ItemStack fixUsingItem1(ItemStack itemStack) {

        ItemStack secHeldItem = player().getGGMEquipment().getSecHeldItem();

        if (player().inFightStance() && secHeldItem != null && secHeldItem.stackSize > 0) {

            itemStack = secHeldItem;
        }

        return itemStack;
    }

    @Redirect(method = "sendUseItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;mainInventory:[Lnet/minecraft/item/ItemStack;", opcode = Opcodes.PUTFIELD))
    private void fixUsingItem2(ItemStack[] mainInventory, int index, ItemStack itemStack) {

        if (player().inFightStance()) {

            int slotIndex = player().getGGMEquipment().getCurrentItemIndex() * 2;
            if (player().getGGMEquipment().getSecHeldItem() != null) ++slotIndex;
            player().getGGMEquipment().setInventorySlotContents(slotIndex, itemStack);
        }
        else {

            mainInventory[index] = itemStack;
        }
    }



    private IGGMEntityPlayer player() {
        return (IGGMEntityPlayer) this.mc.thePlayer;
    }
}
