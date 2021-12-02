package mrfinger.gothicgamemod.entity.capability.abilities;

import net.minecraft.entity.Entity;

public interface IMeleeFight {
	
		

	void inLivingUpdate();
	
	

	void fightingStanceOn();

	void fightingStanceOff();
	
	
	
	void directHit(Entity target);
	
	void sideHit(Entity target);

}
