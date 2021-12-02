package mrfinger.gothicgamemod.mixin.common.network.play.server;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.network.play.server.EntityDynamicProperties;
import mrfinger.gothicgamemod.network.play.server.IGGMS20PacketEntityProperties;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.*;

@Mixin(S20PacketEntityProperties.class)
public class GGMS20PacketEntityProperties implements IGGMS20PacketEntityProperties {


    List<EntityDynamicProperties> dp = new ArrayList<>();


    @Inject(method = "<init>(ILjava/util/Collection;)V", at = @At(value = "RETURN"))
    private void onInit(int id, Collection attributesSet, CallbackInfo ci) {

        Set<IAttributeInstance> set = (HashSet) attributesSet;

        for (IAttributeInstance ai : set) {
            if (ai instanceof IGGMDynamicAttributeInstance) {

                IGGMDynamicAttributeInstance dai = (IGGMDynamicAttributeInstance) ai;
                this.dp.add(new EntityDynamicProperties(dai.getAttribute().getAttributeUnlocalizedName(), dai.getDynamicValue(), dai.getCachedRegenValue()));
            }
        }

    }


    @Inject(method = "readPacketData", at = @At(value = "RETURN"))
    private void readDP(PacketBuffer packetBuffer, CallbackInfo ci)  throws IOException {

        int j = packetBuffer.readInt();

        for (int i = 0; i < j; ++i) {

            this.dp.add(new EntityDynamicProperties(packetBuffer.readStringFromBuffer(64), packetBuffer.readDouble(), packetBuffer.readFloat()));
        }
    }

    @Inject(method = "writePacketData", at = @At(value = "RETURN"))
    private void writeDP(PacketBuffer packetBuffer, CallbackInfo ci)  throws IOException {

        packetBuffer.writeInt(this.dp.size());

        for (EntityDynamicProperties dp : this.dp) {
            packetBuffer.writeStringToBuffer(dp.getUnlocalizedName());
            packetBuffer.writeDouble(dp.getCurrentValue());
            packetBuffer.writeFloat(dp.getRegenValue());
        }
    }

    @Override
    public List<EntityDynamicProperties> getDp() {
        return dp;
    }


    /*@Mixin(S20PacketEntityProperties.Snapshot.class)
    public static class GGMSnapshot implements IGGMS20PacketEntityProperties.IGGMSnapshot {


        private double dynamicValue;

        private float cachedRegenValue;

        @Override
        public void setDV(double dynamicValue, float cachedRegenValue) {
            this.dynamicValue = dynamicValue;
            this.cachedRegenValue = cachedRegenValue;
        }

        @Override
        public double getDynamicValue() {
            return dynamicValue;
        }

        @Override
        public float getCachedRegenValue() {
            return cachedRegenValue;
        }
    }*/

}
