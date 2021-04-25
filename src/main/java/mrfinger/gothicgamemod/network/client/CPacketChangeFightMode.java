package mrfinger.gothicgamemod.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.server.AbstractServerMessageHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketChangeFightMode implements IMessage {


    private boolean b;


    public CPacketChangeFightMode() {};

    public CPacketChangeFightMode(boolean b) {
        this.b = b;
    };


    @Override
    public void fromBytes(ByteBuf buf) {
        this.b = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(b);
    }


    public static class Handler extends AbstractServerMessageHandler<CPacketChangeFightMode> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, CPacketChangeFightMode message, MessageContext ctx) {
            ((IGGMEntityPlayer) player).setInFightStance(message.b);
            return null;
        }
    }
}
