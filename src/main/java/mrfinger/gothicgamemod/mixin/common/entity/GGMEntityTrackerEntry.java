package mrfinger.gothicgamemod.mixin.common.entity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.server.SPacketEntityAttributes;
import mrfinger.gothicgamemod.network.server.SPacketSyncAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;


@Mixin(EntityTrackerEntry.class)
public abstract class GGMEntityTrackerEntry
{

    @Shadow
    public Set trackingPlayers;
    @Shadow
    public Entity myEntity;


    @Inject(method = "sendMetadataToAllAssociatedPlayers", at = @At(value = "JUMP", ordinal = 1), cancellable = true)
    private void remakeSendMetadataToAllAssociatedPlayers(CallbackInfo callbackInfo)
    {
        if (this.myEntity instanceof EntityLivingBase)
        {
            ServersideAttributeMap serversideattributemap = (ServersideAttributeMap) ((EntityLivingBase) this.myEntity).getAttributeMap();
            Set set = serversideattributemap.getAttributeInstanceSet();

            if (!set.isEmpty())
            {
                this.sendMetadata(new SPacketEntityAttributes());
            }

            set.clear();

            IGGMEntityLivingBase entity = (IGGMEntityLivingBase) this.myEntity;

            if (entity.isNeedSyncAnimation())
            {
                String episodeName = entity.getActiveAnimationHelper().getAnimationEpisode() != null ? entity.getActiveAnimationHelper().getAnimationEpisode().getUnlocalizedName() : null;

                if (entity instanceof IGGMEntityPlayerMP)
                {
                    SPacketSyncAnimation packet = new SPacketSyncAnimation(entity.getEntityId(), entity.getActiveAnimationHelper().getUnlocalizedName(), episodeName, entity.getActiveAnimationHelper().getEpisodeCountdown());
                    PacketDispatcher.sendTo(packet, (IGGMEntityPlayerMP) this.myEntity);
                }

                for (Object o : this.trackingPlayers)
                {
                    SPacketSyncAnimation packet = new SPacketSyncAnimation(entity.getEntityId(), entity.getActiveAnimationHelper().getUnlocalizedName(), episodeName, entity.getActiveAnimationHelper().getEpisodeCountdown());
                    PacketDispatcher.sendTo(packet, (IGGMEntityPlayerMP) o);
                }

            }
        }

        callbackInfo.cancel();
    }


    protected void sendMetadata(IMessage message)
    {
        if (myEntity instanceof EntityPlayerMP)
        {
            PacketDispatcher.sendTo(message, (IGGMEntityPlayerMP) this.myEntity);
        }

        for (Object o : this.trackingPlayers)
        {

            PacketDispatcher.sendTo(message, (IGGMEntityPlayerMP) o);
        }
    }


/*
    @Redirect(method = "sendMetadataToAllAssociatedPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityTrackerEntry;func_151261_b(Lnet/minecraft/network/Packet;)V", ordinal = 1, remap = false))
    private Packet redirectEntityAttributePacket(Packet packet)
    {
        return new SPacketEntityAttributes(id, set);
    }

    @Redirect(method = {"sendMetadataToAllAssociatedPlayers", "tryStartWachingThis}"}, at = @At(value = "NEW", target = "net/minecraft/network/play/server/S20PacketEntityProperties", ordinal = 0, remap = false))
    private Packet removeSendingVanillaAttributesPacket(int id, Collection set)
    {
        return null;
    }
*/

/*
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Entity trackedEntity, int blocksDistanceThreshold, int updateFrequency, boolean sendVelocityUpdates,CallbackInfo ci) {


    }

    @Inject(method = "sendMetadataToAllAssociatedPlayers", at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V", remap = false))
    private void updateDynamicProperties(CallbackInfo ci)
    {
        IGGMEntityLivingBase entity = (IGGMEntityLivingBase) this.trackedEntity;

        Collection<IGGMDynamicAttributeInstance> dpiColl = ((IGGMBaseAttributeMap) entity.getAttributeMap()).getToUpdateSet();

        if (dpiColl != null && !dpiColl.isEmpty()) {

            if (this.trackedEntity instanceof IGGMEntityPlayerMP) {

                PacketDispatcher.sendTo(new SPacketEntityAttributes(this.trackedEntity.getEntityId(), dpiColl), (IGGMEntityPlayerMP) this.trackedEntity);
            }

            for (Object o : this.trackingPlayers) {

                PacketDispatcher.sendTo(new SPacketEntityAttributes(this.trackedEntity.getEntityId(), dpiColl), (IGGMEntityPlayerMP) o);
            }

            dpiColl.clear();
        }

        if (entity.isNeedExpUpdate()) {

            if (this.trackedEntity instanceof IGGMEntityPlayerMP) {

                PacketDispatcher.sendTo(new SPacketExpValues(entity), (IGGMEntityPlayerMP) this.trackedEntity);
            }

            for (Object o : this.trackingPlayers) {

                PacketDispatcher.sendTo(new SPacketExpValues(entity), (IGGMEntityPlayerMP) o);
            }
        }
    }*/

    /*@Inject(method = "sendMetadataToAllAssociatedPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getAttributeMap()Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;"))
    private void onSendLivingEntityMetadata(CallbackInfo ci)
    {
        IGGMEntityLivingBase entity = (IGGMEntityLivingBase) this.trackedEntity;

        if (entity.isNeedSyncAnimation())
        {
            String episodeName = entity.getActiveAnimationHelper().getAnimationEpisode() != null ? entity.getActiveAnimationHelper().getAnimationEpisode().getUnlocalizedName() : null;

            if (entity instanceof IGGMEntityPlayerMP)
            {
                SPacketSyncAnimation packet = new SPacketSyncAnimation(entity.getEntityId(), entity.getActiveAnimationHelper().getUnlocalizedName(), episodeName, entity.getActiveAnimationHelper().getEpisodeCountdown());
                PacketDispatcher.sendTo(packet, (IGGMEntityPlayerMP) this.trackedEntity);
            }

            for (Object o : this.trackingPlayers)
            {
                SPacketSyncAnimation packet = new SPacketSyncAnimation(entity.getEntityId(), entity.getActiveAnimationHelper().getUnlocalizedName(), episodeName, entity.getActiveAnimationHelper().getEpisodeCountdown());
                PacketDispatcher.sendTo(packet, (IGGMEntityPlayerMP) o);
            }

        }

    }

    /*@Inject(method = "tryStartWachingThis", at = @At(value = "RETURN"))// "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/ai/attribute/ServersideAttributeMap;getWatchedAttributes()Ljava/util/Collection;"))
    private void onTryStartWachingThis(EntityPlayerMP player, CallbackInfo ci) {

        System.out.println("Debug in " + this.getClass() + "onTryStartWachingThis");
    }*/

}
