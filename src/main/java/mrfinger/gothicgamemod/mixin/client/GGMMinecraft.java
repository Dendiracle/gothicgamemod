package mrfinger.gothicgamemod.mixin.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.IGGMMinecraft;
import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.client.multiplayer.IGGMPlayerControllerMP;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketSetItemInUseInFightAnim;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Timer;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@SideOnly(Side.CLIENT)
@Mixin(Minecraft.class)
public class GGMMinecraft implements IGGMMinecraft {


    @Shadow public EntityClientPlayerMP thePlayer;

    @Shadow public PlayerControllerMP playerController;

    @Shadow public GameSettings gameSettings;

    @Shadow private Timer timer;


    @Override
    public Timer getTimer()
    {
        return this.timer;
    }


    /*@ModifyArg(method = "func_147116_af", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    private Entity modifyAttack(Entity entity) {
        return ((IGGMEntityPlayer) this.thePlayer).inFightStance() ? null : entity;
    }*/

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;changeCurrentItem(I)V"))
    private void fixChangingItem(InventoryPlayer inventoryPlayer, int index)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance())
        {
            player.getGGMEquipment().changeCurrentItem(index < 0);
        }
        else {
            inventoryPlayer.changeCurrentItem(index);
        }
    }

    @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = Opcodes.PUTFIELD))
    private void fixChangingItem2(InventoryPlayer inventoryPlayer, int index)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance()) {

            player.getGGMEquipment().setCurrentItem(index);
        }
        else {
            inventoryPlayer.currentItem = index;
        }
    }


    @Inject(method = "func_147116_af", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityClientPlayerMP;swingItem()V", ordinal = 0), cancellable = true)
    private void onAttack(CallbackInfo ci)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;
        if (player.inFightStance())
        {
            ((IGGMPlayerControllerMP) this.playerController).startAttack();
            ci.cancel();
        }
    }

    @Inject(method = "func_147115_a", at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovingObjectPosition;blockX:I", ordinal = 0), cancellable = true)
    private void onLeftClick(boolean b, CallbackInfo ci)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance())
        {
            ci.cancel();
        }
    }


    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityClientPlayerMP;isUsingItem()Z"))
    private boolean fixUsingItem(EntityClientPlayerMP player)
    {
        return player.isUsingItem() || ((IGGMAbstractClientPlayer) player).getGGMEquipment().isUsingItem();
    }


    @Inject(method = "func_147121_ag", at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovingObjectPosition;blockX:I"), cancellable = true)
    private void fixUsingItem1(CallbackInfo ci)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.isDeniedUsingItems())
        {
            ci.cancel();
        }
    }


    @Inject(method = "func_147121_ag", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;", ordinal = 1), cancellable = true)
    private void fixUsingItem2(CallbackInfo ci)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.isDeniedUsingItems())
        {
            ci.cancel();
        }
    }


    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onStoppedUsingItem(Lnet/minecraft/entity/player/EntityPlayer;)V", ordinal =  0))
    private void disableUseItemInFightStance(PlayerControllerMP controller, EntityPlayer playerr)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.inFightStance())
        {
            PacketDispatcher.sendToServer(new CPacketSetItemInUseInFightAnim(false));
            player.getGGMEquipment().endUseItem();
        }
        else
        {
            this.playerController.onStoppedUsingItem(this.thePlayer);
        }
    }

    /*@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;func_147115_a(Z)V", ordinal =  0))
    private void disableUseItemInFightStance(CallbackInfo ci)
    {
        System.out.println("Debug in GGMMinecraft 0");
        IGGMEntityPlayer player = (IGGMEntityPlayer) this.thePlayer;

        if (player.getGGMEquipment().isUsingItem())
        {
            System.out.println("Debug in GGMMinecraft 1");
            if (!this.gameSettings.keyBindUseItem.getIsKeyPressed())
            {
                System.out.println("disabling blocking");
                PacketDispatcher.sendToServer(new CPacketSetItemInUseInFightAnim(false));
                player.getGGMEquipment().endUseItem();
            }

            label:

            while (true)
            {
                if (!this.gameSettings.keyBindAttack.isPressed())
                {
                    while (this.gameSettings.keyBindUseItem.isPressed())
                    {
                        ;
                    }

                    while (true)
                    {
                        if (this.gameSettings.keyBindPickBlock.isPressed())
                        {
                            continue;
                        }

                        break label;
                    }
                }
            }
        }
    }*/


}
