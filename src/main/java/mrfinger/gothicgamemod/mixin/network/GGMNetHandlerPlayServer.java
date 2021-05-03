package mrfinger.gothicgamemod.mixin.network;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetHandlerPlayServer.class)
public class GGMNetHandlerPlayServer {


    @Shadow @Final private MinecraftServer serverController;

    @Shadow public EntityPlayerMP playerEntity;


    @Redirect(method = "processUseEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayerMP;attackTargetEntityWithCurrentItem(Lnet/minecraft/entity/Entity;)V"))
    private void fixProcessUseEntity(EntityPlayerMP playerMP, Entity entity) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) playerEntity;
        if (!player.inFightStance()) {
            player.attackTargetEntityWithCurrentItem(entity);
        }
    }


    @Redirect(method = "processPlayerBlockPlacement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;", ordinal = 0))
    private ItemStack fixUsingItem(InventoryPlayer inventoryPlayer) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) playerEntity;

        if (player.inFightStance()) {

            return player.getGGMEquipment().getSecHeldItem() != null ? player.getGGMEquipment().getSecHeldItem() : player.getGGMEquipment().getHeldItem();
        }

        return inventoryPlayer.getCurrentItem();
    }

}
