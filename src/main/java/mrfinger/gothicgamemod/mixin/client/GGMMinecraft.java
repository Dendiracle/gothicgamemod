package mrfinger.gothicgamemod.mixin.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.multiplayer.IGGMPlayerControllerMP;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@SideOnly(Side.CLIENT)
@Mixin(Minecraft.class)
public class GGMMinecraft {


    @Shadow public EntityClientPlayerMP thePlayer;

    @Shadow public PlayerControllerMP playerController;


    /*@ModifyArg(method = "func_147116_af", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    private Entity modifyAttack(Entity entity) {
        return ((IGGMEntityPlayer) this.thePlayer).inFightStance() ? null : entity;
    }*/

    @Inject(method = "func_147116_af", at = @At(value = "HEAD"), cancellable = true)
    private void onAttack(CallbackInfo ci) {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;
        if (player.inFightStance()) {

            ((IGGMPlayerControllerMP) this.playerController).startAttack();
            ci.cancel();
        }
    }

    @Inject(method = "func_147115_a", at = @At(value = "JUMP", ordinal = 1), cancellable = true)
    private void onLeftClick(boolean b, CallbackInfo ci) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;
        if (player.inFightStance()) {

            ci.cancel();
        }
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;changeCurrentItem(I)V"))
    private void fixChangingItem(InventoryPlayer inventoryPlayer, int index) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance()) {

            player.getGGMEquipment().changeCurrentItem(index < 0);
        }
        else {
            inventoryPlayer.changeCurrentItem(index);
        }
    }

    @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = Opcodes.PUTFIELD))
    private void fixChangingItem2(InventoryPlayer inventoryPlayer, int index) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance()) {

            player.getGGMEquipment().setCurrentItem(index);
        }
        else {
            inventoryPlayer.currentItem = index;
        }
    }


    @Inject(method = "func_147121_ag", at = @At(value = "HEAD"), cancellable = true)
    private void fixUsingItem1(CallbackInfo ci)
    {
        if (((IGGMEntityPlayer) this.thePlayer).getAttackTicksLeft() > 0) ci.cancel();
    }

    @Redirect(method = "func_147121_ag", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;", ordinal = 1))
    private ItemStack fixUsingItem2(InventoryPlayer inventoryPlayer) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance()) {

            return player.getGGMEquipment().getSecHeldItem() != null ? player.getGGMEquipment().getSecHeldItem() : player.getGGMEquipment().getHeldItem();
        }

        return inventoryPlayer.getCurrentItem();
    }


}
