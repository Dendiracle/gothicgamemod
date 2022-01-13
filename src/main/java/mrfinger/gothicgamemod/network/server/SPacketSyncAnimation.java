package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.data.entity.AnimationsData;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationManager;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class SPacketSyncAnimation implements IMessage
{

    protected int entityID;
    protected UUID uuid;
    IAnimationManager.IAnimationSnapshot snapshot;


    public SPacketSyncAnimation() {}

    public SPacketSyncAnimation(int entityID, IAnimation animation)
    {
        this.entityID = entityID;
        this.uuid = animation.getAnimationManager().getID();
        this.snapshot = animation.getAnimationManager().saveAnimationToSnapshot(animation);
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.uuid = new UUID(buf.readLong(), buf.readLong());
        IAnimationManager manager = AnimationsData.getManager(this.uuid);
        if (manager == null)
        {
            throw new NullPointerException("Animation Manager wasn't register");
        }
        this.snapshot = manager.getSnapshotFromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeLong(this.uuid.getMostSignificantBits());
        buf.writeLong(this.uuid.getLeastSignificantBits());
        this.snapshot.toBytes(buf);
    }


    public static class Handler extends AbstractClientMessageHandler<SPacketSyncAnimation>
    {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketSyncAnimation message, MessageContext ctx)
        {
            Entity entity = player.worldObj.getEntityByID(message.entityID);

            if (entity instanceof IGGMEntityLivingBase)
            {
                ((IGGMEntityLivingBase) entity).setActiveAnimationDirectly(message.snapshot.getInstance());
            }

            return null;
        }

    }
}
