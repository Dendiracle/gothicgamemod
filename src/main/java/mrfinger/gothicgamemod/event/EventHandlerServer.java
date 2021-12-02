package mrfinger.gothicgamemod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mrfinger.gothicgamemod.util.GGMTicks;

public class EventHandlerServer {


	
	@SubscribeEvent
	public void onServerTicks(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			++GGMTicks.gTicks;
		}
	}

}
