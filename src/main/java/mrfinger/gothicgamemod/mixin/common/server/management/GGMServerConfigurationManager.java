package mrfinger.gothicgamemod.mixin.common.server.management;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.management.ServerConfigurationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerConfigurationManager.class)
public class GGMServerConfigurationManager {


    @Inject(method = "initializeConnectionToPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetHandlerPlayServer;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 2))
    private void onInitializeConnectionToPlayer(NetworkManager networkManager, EntityPlayerMP entityPlayerMP, NetHandlerPlayServer netHandlerPlayServer, CallbackInfo ci) {

        /*System.out.println("Debug in ServerConfigurationManager");
        Container ggmContainer = ((IGGMEntityPlayerMP) entityPlayerMP).getGGMContainer();
        netHandlerPlayServer.sendPacket(new S30PacketWindowItems(ggmContainer.windowId, ggmContainer.getInventory()));*/


    }


}
