package mrfinger.gothicgamemod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class BPacketSyncCurrentItemInGGMSlot implements IMessage
{

    byte index;


    public BPacketSyncCurrentItemInGGMSlot() {}

    public BPacketSyncCurrentItemInGGMSlot(byte index)
    {
        this.index = index;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.index = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.index);
    }


    public static class Handler extends AbstractMessageHandler<BPacketSyncCurrentItemInGGMSlot>
    {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, BPacketSyncCurrentItemInGGMSlot message, MessageContext ctx)
        {
            ((IGGMEntityPlayer) player).getGGMEquipment().setCurrentItem(message.index);
            return null;
        }

        @Override
        public IMessage handleServerMessage(EntityPlayer player, BPacketSyncCurrentItemInGGMSlot message, MessageContext ctx)
        {
            ((IGGMEntityPlayer) player).getGGMEquipment().setCurrentItem(message.index);
            return null;
        }

    }

}
