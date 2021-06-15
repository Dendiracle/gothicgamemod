package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketSyncAnimation implements IMessage
{

    protected int entityID;
    protected String animationName;
    protected String episodeName;
    protected int duration;
    protected int count;

    public SPacketSyncAnimation() {}

    public SPacketSyncAnimation(int entityID, String animationName, String episodeName, int duration)
    {
        this.entityID = entityID;
        this.animationName = animationName;
        this.episodeName = episodeName;
        this.duration = duration;
        this.count = duration;
    }


    public void setCount(int count)
    {
        this.count = count;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        int i = 0;
        char[] an = new char[buf.readInt()];

        for ( ; i < an.length; ++i)
        {
            an[i] = buf.readChar();
        }

        this.animationName = String.copyValueOf(an);
        this.duration = buf.readInt();

        if (this.duration > 0)
        {
            i = 0;
            an = new char[buf.readInt()];

            for ( ; i < an.length; ++i)
            {
                an[i] = buf.readChar();
            }

            this.episodeName = String.copyValueOf(an);
            this.count = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        char[] an = this.animationName.toCharArray();
        buf.writeInt(an.length);

        for (char c : an)
        {
            buf.writeChar(c);
        }

        buf.writeInt(this.duration);

        if (this.duration > 0)
        {
            an = this.episodeName.toCharArray();
            buf.writeInt(an.length);

            for (char c : an)
            {
                buf.writeChar(c);
            }

            buf.writeInt(this.count);
        }

    }


    public static class Handler extends AbstractClientMessageHandler<SPacketSyncAnimation>
    {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketSyncAnimation message, MessageContext ctx)
        {
            Entity entity = player.worldObj.getEntityByID(message.entityID);

            if (entity instanceof IGGMEntityLivingBase)
            {
                if (((IGGMEntityLivingBase) entity).setAnimation(message.animationName))
                {
                    ((IGGMEntityLivingBase) entity).getCurrentAnimation().setAnimationEpisode(message.episodeName, message.duration);

                    if (message.count != message.duration) ((IGGMEntityLivingBase) entity).getCurrentAnimation().setEpisodeCount(message.count);
                }
            }

            return null;
        }
    }
}
