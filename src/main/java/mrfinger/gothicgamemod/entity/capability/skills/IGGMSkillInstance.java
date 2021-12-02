package mrfinger.gothicgamemod.entity.capability.skills;

import mrfinger.gothicgamemod.entity.capability.EntitySkills;
import mrfinger.gothicgamemod.entity.capability.ICapabilitySaveable;
import mrfinger.gothicgamemod.entity.capability.IIncreaseble;
import mrfinger.gothicgamemod.entity.capability.IModifiable;

public interface IGGMSkillInstance extends ICapabilitySaveable, IModifiable {
	
	
	public void setValue(float value);	
	
	public void changeValue(float changeValue);
	
	
	public EntitySkills getName();
	
	public float getBaseValue();
	
	public float getValue();	
	
	
	public interface IGothicIncreasebleSkillInstance extends IGGMSkillInstance, IIncreaseble {
		
	}
	
}
