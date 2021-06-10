package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.server.SPacketEntityDynamicAttributes;
import mrfinger.gothicgamemod.network.server.SPacketExpValues;
import mrfinger.gothicgamemod.network.server.SPacketSyncAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Set;


@Mixin(EntityTrackerEntry.class)
public abstract class GGMEntityTrackerEntry {


    @Shadow public Entity myEntity;

    @Shadow public Set trackingPlayers;


    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Entity trackedEntity, int blocksDistanceThreshold, int updateFrequency, boolean sendVelocityUpdates,CallbackInfo ci) {


    }

    @Inject(method = "sendMetadataToAllAssociatedPlayers", at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V", remap = false))
    private void updateDynamicProperties(CallbackInfo ci)
    {
        IGGMEntityLivingBase entity = (IGGMEntityLivingBase) this.myEntity;

        Collection<IGGMDynamicAttributeInstance> dpiColl = ((IGGMBaseAttributeMap) entity.getAttributeMap()).getToUpdateSet();

        if (dpiColl != null && !dpiColl.isEmpty()) {

            if (this.myEntity instanceof IGGMEntityPlayerMP) {

                PacketDispatcher.sendTo(new SPacketEntityDynamicAttributes(this.myEntity.getEntityId(), dpiColl), (IGGMEntityPlayerMP) this.myEntity);
            }

            for (Object o : this.trackingPlayers) {

                PacketDispatcher.sendTo(new SPacketEntityDynamicAttributes(this.myEntity.getEntityId(), dpiColl), (IGGMEntityPlayerMP) o);
            }

            dpiColl.clear();
        }

        if (entity.isNeedExpUpdate()) {

            if (this.myEntity instanceof IGGMEntityPlayerMP) {

                PacketDispatcher.sendTo(new SPacketExpValues(entity), (IGGMEntityPlayerMP) this.myEntity);
            }

            for (Object o : this.trackingPlayers) {

                PacketDispatcher.sendTo(new SPacketExpValues(entity), (IGGMEntityPlayerMP) o);
            }
        }
    }

    @Inject(method = "sendMetadataToAllAssociatedPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getAttributeMap()Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;"))
    private void onSendLivingEntityMetadata(CallbackInfo ci)
    {
        IGGMEntityLivingBase entity = (IGGMEntityLivingBase) this.myEntity;

        if (entity.isNeedSyncAnimation())
        {
            String episodeName = entity.getCurrentAnimation().getEpisode() != null ? entity.getCurrentAnimation().getEpisode().getUnlocalizedName() : null;

            if (entity instanceof IGGMEntityPlayerMP)
            {
                SPacketSyncAnimation packet = new SPacketSyncAnimation(entity.getEntityId(), entity.getCurrentAnimation().getUnlocalizedName(), episodeName, entity.getCurrentAnimation().getEpisodeCount());
                PacketDispatcher.sendTo(packet, (IGGMEntityPlayerMP) this.myEntity);
            }

            for (Object o : this.trackingPlayers)
            {
                SPacketSyncAnimation packet = new SPacketSyncAnimation(entity.getEntityId(), entity.getCurrentAnimation().getUnlocalizedName(), episodeName, entity.getCurrentAnimation().getEpisodeCount());
                PacketDispatcher.sendTo(packet, (IGGMEntityPlayerMP) o);
            }

        }

    }

    /*@Inject(method = "tryStartWachingThis", at = @At(value = "RETURN"))// "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/ai/attributes/ServersideAttributeMap;getWatchedAttributes()Ljava/util/Collection;"))
    private void onTryStartWachingThis(EntityPlayerMP player, CallbackInfo ci) {

        System.out.println("Debug in " + this.getClass() + "onTryStartWachingThis");
    }*/

}
