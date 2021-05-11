package mrfinger.gothicgamemod.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import mrfinger.gothicgamemod.client.gui.GGMGuiCharachterMenu;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.init.GGMGui;
import mrfinger.gothicgamemod.init.GGMKeyBindings;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketChangeFightMode;
import mrfinger.gothicgamemod.network.client.CPacketOpenGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class EventHandlerClient {
	
	@SubscribeEvent
	public void onUpdate(InputEvent.KeyInputEvent event) {
		if (!FMLClientHandler.instance().isGUIOpen(GGMGuiCharachterMenu.class)) {
			int key = Keyboard.getEventKey();
			boolean isDown = Keyboard.getEventKeyState();
			if (isDown) {
				if (key == GGMKeyBindings.GKeyBinding.getKeyCode()) Minecraft.getMinecraft().displayGuiScreen(new GGMGuiCharachterMenu());
				else if (key == GGMKeyBindings.GGMInventory.getKeyCode()) PacketDispatcher.sendToServer(new CPacketOpenGui(GGMGui.GGMGuiInvenory));
				else {

					if (key == GGMKeyBindings.changeStance.getKeyCode()) {
						IGGMEntityPlayer player = (IGGMEntityPlayer) Minecraft.getMinecraft().thePlayer;
						player.changeStance();
						boolean postInFightStance = player.inFightStance();
						boolean changeTo = player.isChangingStance() ? !postInFightStance : postInFightStance;
						PacketDispatcher.sendToServer(new CPacketChangeFightMode(changeTo));
					}
				}
			}			
		}
	}
		
}


