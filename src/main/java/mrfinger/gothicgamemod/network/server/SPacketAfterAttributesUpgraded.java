package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.client.gui.GGMGuiCharachterMenu;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketAfterAttributesUpgraded implements IMessage {


    public SPacketAfterAttributesUpgraded() {}


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler extends AbstractClientMessageHandler<SPacketAfterAttributesUpgraded> {


        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketAfterAttributesUpgraded message, MessageContext ctx) {

            if (Minecraft.getMinecraft().currentScreen instanceof GGMGuiCharachterMenu) ((GGMGuiCharachterMenu) Minecraft.getMinecraft().currentScreen).lStatNull();
            return null;
        }
    }
}
