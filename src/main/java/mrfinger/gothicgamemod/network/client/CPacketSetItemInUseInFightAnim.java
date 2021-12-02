package mrfinger.gothicgamemod.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.server.AbstractServerMessageHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketSetItemInUseInFightAnim implements IMessage {

    private boolean bool;


    public CPacketSetItemInUseInFightAnim() {}


    public CPacketSetItemInUseInFightAnim(boolean bool)
    {
        this.bool = bool;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.bool = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.bool);
    }


    public static class Handler extends AbstractServerMessageHandler<CPacketSetItemInUseInFightAnim>
    {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, CPacketSetItemInUseInFightAnim message, MessageContext ctx)
        {
            IGGMEntityPlayer playerG = (IGGMEntityPlayer) player;

            if (playerG.inFightStance())
            {
                if (message.bool)
                {
                    playerG.getGGMEquipment().setUseItem();
                }
                else
                {
                    playerG.getGGMEquipment().endUseItem();
                }
            }

            return null;
        }

    }
}
