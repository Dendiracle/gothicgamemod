package mrfinger.gothicgamemod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mrfinger.gothicgamemod.entity.capability.IGGMEntityExperienceable;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.init.GEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventHandlerEntity {
	
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {

		Entity from = event.source.getEntity();		
	    EntityLivingBase to = event.entityLiving;

	    if (from instanceof IGGMEntityExperienceable) {

			IGGMEntityExperienceable statsFrom = (IGGMEntityExperienceable) from;

			statsFrom.getExpCap().gainExp(((IGGMEntityLivingBase) to).getLvl() * GEntities.EXPModifier);
	    }
	}

}
