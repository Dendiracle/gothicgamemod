package mrfinger.gothicgamemod.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.server.AbstractServerMessageHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketStartAttack implements IMessage {


    short i;


    public CPacketStartAttack() {};

    public CPacketStartAttack(short i) {
        this.i = i;
    };


    @Override
    public void fromBytes(ByteBuf buf) {
        this.i = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(i);
    }


    public static class Handler extends AbstractServerMessageHandler<CPacketStartAttack> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, CPacketStartAttack message, MessageContext ctx) {
            ((IGGMEntityPlayer) player).startAttack();
            return null;
        }
    }
}
