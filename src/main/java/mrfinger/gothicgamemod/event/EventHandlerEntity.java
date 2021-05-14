package mrfinger.gothicgamemod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.IGGMEntityExperienceable;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.init.GEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EventHandlerEntity {
	
	
	/*@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		Entity from = event.source.getEntity();

	    if (from instanceof IGGMEntityExperienceable)
	    {
			EntityLivingBase to = event.entityLiving;
			IGGMEntityExperienceable statsFrom = (IGGMEntityExperienceable) from;
			int gainExp = ((IGGMEntityLivingBase) to).getLvl() * GEntities.EXPModifier;
			if (to instanceof IGGMEntityPlayer) gainExp += 100;
			statsFrom.getExpCap().gainExp(gainExp);
	    }
	}*/


}
