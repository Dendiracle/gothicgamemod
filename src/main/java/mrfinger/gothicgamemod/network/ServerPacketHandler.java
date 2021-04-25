package mrfinger.gothicgamemod.network;

import codechicken.lib.packet.PacketCustom;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;

public class ServerPacketHandler implements PacketCustom.IServerPacketHandler {
    
	@Override
    public void handlePacket(PacketCustom packetCustom, EntityPlayerMP playerMP, INetHandlerPlayServer iNetHandlerPlayServer) {
		IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) playerMP;
		switch(packetCustom.getType()){
		case 1:
			//((IGGMEntityExperienceable) player).increaseAttributes(packetCustom.readNBTTagCompound());
			break;
		case 2:
			System.out.println("Debug in " + this.getClass());
			player.startAttack();
			break;
		case 3:

			break;
        default:
        	break;
		}
    }

}
