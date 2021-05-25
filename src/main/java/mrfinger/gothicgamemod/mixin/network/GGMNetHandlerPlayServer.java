package mrfinger.gothicgamemod.mixin.network;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayServer.class)
public class GGMNetHandlerPlayServer {


    @Shadow @Final private MinecraftServer serverController;

    @Shadow public EntityPlayerMP playerEntity;


    @Inject(method = "processPlayerDigging", at = @At("HEAD"), cancellable = true)
    private void cancelDrop(C07PacketPlayerDigging packet, CallbackInfo ci)
    {
        int func = packet.func_149506_g();
        if (((IGGMEntityPlayer) this.playerEntity).isDeniedDropItems() && (func == 4 || func == 3)) ci.cancel();
        if (((IGGMEntityPlayer) this.playerEntity).isDeniedDigging() && (func == 2 || func == 1 || func == 0)) ci.cancel();
    }


    @Inject(method = "processPlayerBlockPlacement", at = @At(value = "HEAD"), cancellable = true)
    private void fixUsingItem(CallbackInfo ci)
    {
        if (((IGGMEntityPlayer) this.playerEntity).isDeniedUsingItems()) ci.cancel();
    }


    @Redirect(method = "processUseEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayerMP;attackTargetEntityWithCurrentItem(Lnet/minecraft/entity/Entity;)V"))
    private void fixProcessUseEntity(EntityPlayerMP playerMP, Entity entity)
    {
        IGGMEntityPlayer player = (IGGMEntityPlayer) playerEntity;

        if (!player.inFightStance())
        {
            player.attackTargetEntityWithCurrentItem(entity);
        }
    }

}
