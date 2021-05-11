package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.IGGMEntityExperienceable;
import mrfinger.gothicgamemod.entity.capability.IGGMExp;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketExpValues implements IMessage {


    protected boolean experiencable;

    protected int id, level, lp;

    protected long curr;


    public SPacketExpValues() {};

    public SPacketExpValues(IGGMEntityLivingBase entity)
    {
        this.id = entity.getEntityId();
        this.experiencable = entity instanceof IGGMEntityExperienceable;
        this.level = entity.getLvl();

        if (this.experiencable) {

            IGGMExp exp = ((IGGMEntityExperienceable) entity).getExpCap();
            curr = exp.getExp();
            this.lp = exp.getLP();
        }
    }


    @Override
    public void fromBytes(ByteBuf buf) {

        this.id = buf.readInt();
        this.experiencable = buf.readBoolean();
        this.level = buf.readInt();

        if (this.experiencable) {

            curr = buf.readLong();
            this.lp = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.id);
        buf.writeBoolean(this.experiencable);
        buf.writeInt(this.level);

        if (this.experiencable) {

            buf.writeLong(this.curr);
            buf.writeInt(this.lp);
        }
    }


    public static class Handler extends AbstractClientMessageHandler<SPacketExpValues> {


        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketExpValues message, MessageContext ctx) {

            Entity entityE = player.worldObj.getEntityByID(message.id);

            if (entityE != null && entityE instanceof IGGMEntityLivingBase) {

                IGGMEntityLivingBase entity = (IGGMEntityLivingBase) entityE;

                entity.setLvl(message.level);

                if (message.experiencable && entity instanceof IGGMEntityExperienceable) {

                    IGGMExp exp = ((IGGMEntityExperienceable) entity).getExpCap();

                    exp.setExp(message.curr);
                    exp.setLP(message.lp);
                }
            }

            return null;
        }

    }

}
