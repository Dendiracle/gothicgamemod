package mrfinger.gothicgamemod.mixin.client.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.network.play.IGGMNetHandlerPlayClient;
import mrfinger.gothicgamemod.network.play.server.EntityDynamicProperties;
import mrfinger.gothicgamemod.network.play.server.IGGMS20PacketEntityProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S30PacketWindowItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@SideOnly(Side.CLIENT)
@Mixin(NetHandlerPlayClient.class)
public abstract class GGMNetHandlerPlayClient implements IGGMNetHandlerPlayClient {


    @Shadow private WorldClient clientWorldController;

    @Shadow public Minecraft gameController;

    @Inject(method = "handleEntityProperties", at = @At("RETURN"))
    private void setDP(S20PacketEntityProperties packet, CallbackInfo ci) {

        Entity entity = this.clientWorldController.getEntityByID(packet.func_149442_c());

        if (entity != null)
        {
            for (EntityDynamicProperties dp : ((IGGMS20PacketEntityProperties) packet).getDp()) {

                ((IGGMDynamicAttributeInstance) ((EntityLivingBase) entity).getAttributeMap().getAttributeInstanceByName(dp.getUnlocalizedName())).setDynamicValue(dp.getCurrentValue());
            }
        }
    }


    @Inject(method = "handleWindowItems", at = @At(value = "JUMP"), cancellable = true)
    private void fixGGMCOntainerSync(S30PacketWindowItems packetWindowItems, CallbackInfo ci) {

        /*Container container = ((IGGMAbstractClientPlayer) this.gameController.thePlayer).getGGMContainer();

        if (packetWindowItems.func_148911_c() == container.windowId) {

            container.putStacksInSlots(packetWindowItems.func_148910_d());
            ci.cancel();
        }*/
    }

}
