package mrfinger.gothicgamemod.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.network.server.AbstractServerMessageHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketOpenGui implements IMessage {


    short id;


    public CPacketOpenGui() {}

    public CPacketOpenGui(int id) {
        this.id = (short) id;
    }


    @Override
    public void fromBytes(ByteBuf buf) {

        this.id = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeChar(this.id);
    }


    public static class Handler extends AbstractServerMessageHandler<CPacketOpenGui> {


        @Override
        public IMessage handleServerMessage(EntityPlayer player, CPacketOpenGui message, MessageContext ctx) {

            player.openGui(GothicMain.instance, message.id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }

    }

}
