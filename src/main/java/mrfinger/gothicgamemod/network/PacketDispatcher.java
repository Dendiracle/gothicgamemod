package mrfinger.gothicgamemod.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import mrfinger.gothicgamemod.network.client.*;
import mrfinger.gothicgamemod.network.server.SPacketAfterAttributesUpgraded;
import mrfinger.gothicgamemod.network.server.SPacketEntityDynamicAttributes;
import mrfinger.gothicgamemod.network.server.SPacketExpValues;
import mrfinger.gothicgamemod.network.server.SPacketSyncAnimation;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketDispatcher {

    private static byte packetQuantity = 0;

    private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(GothicMain.MODID);


    public static void registerClientPackets()
    {
        clientRegisterMessage(CPacketChangeFightMode.Handler.class, CPacketChangeFightMode.class);
        clientRegisterMessage(CPacketStartAttack.Handler.class, CPacketStartAttack.class);
        clientRegisterMessage(CPacketEntitiesToAttack.Handler.class, CPacketEntitiesToAttack.class);
        clientRegisterMessage(CPacketAttributesToUpgrade.Handler.class, CPacketAttributesToUpgrade.class);
        clientRegisterMessage(CPacketOpenGui.Handler.class, CPacketOpenGui.class);
        clientRegisterMessage(CPacketSetItemInUseInFightAnim.Handler.class, CPacketSetItemInUseInFightAnim.class);
    }

    public static void registerServerPackets()
    {
        servertRegisterMessage(SPacketEntityDynamicAttributes.Handler.class, SPacketEntityDynamicAttributes.class);
        servertRegisterMessage(SPacketExpValues.Handler.class, SPacketExpValues.class);
        servertRegisterMessage(SPacketAfterAttributesUpgraded.Handler.class, SPacketAfterAttributesUpgraded.class);
        servertRegisterMessage(SPacketSyncAnimation.Handler.class, SPacketSyncAnimation.class);
    }

    public static void registerBiPackets()
    {
        registerBiMessage(BPacketSyncCurrentItemInGGMSlot.Handler.class, BPacketSyncCurrentItemInGGMSlot.class);
    }


    private static <REQ extends IMessage, REPLY extends IMessage> void clientRegisterMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        PacketDispatcher.dispatcher.registerMessage(messageHandler, requestMessageType, packetQuantity++, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void servertRegisterMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        PacketDispatcher.dispatcher.registerMessage(messageHandler, requestMessageType, packetQuantity++, Side.CLIENT);
    }

    private static void registerBiMessage (Class handlerClass, Class messageClass)
    {
        PacketDispatcher.dispatcher.registerMessage (handlerClass, messageClass, packetQuantity, Side.CLIENT);
        PacketDispatcher.dispatcher.registerMessage (handlerClass, messageClass, packetQuantity++, Side.SERVER);
    }


    public static void sendTo(IMessage message, EntityPlayerMP player) {
        PacketDispatcher.dispatcher.sendTo(message, player);
    }

    public static void sendTo(IMessage message, IGGMEntityPlayerMP player) {
        PacketDispatcher.dispatcher.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        PacketDispatcher.dispatcher.sendToAllAround(message, point);
    }

    public static void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
        PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    public static void sendToDimension(IMessage message, int dimensionId) {
        PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
    }

    public static void sendToServer(IMessage message) {
        PacketDispatcher.dispatcher.sendToServer(message);
    }

}
