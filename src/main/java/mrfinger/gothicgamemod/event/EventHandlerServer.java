package mrfinger.gothicgamemod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mrfinger.gothicgamemod.util.GGMTicks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

public class EventHandlerServer {


	
	@SubscribeEvent
	public void onServerTicks(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			++GGMTicks.gTicks;
		}
	}

}
