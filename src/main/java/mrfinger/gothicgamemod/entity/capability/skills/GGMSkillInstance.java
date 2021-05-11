package mrfinger.gothicgamemod.entity.capability.skills;

import mrfinger.gothicgamemod.entity.capability.EntitySkills;
import mrfinger.gothicgamemod.entity.capability.IModifiable;
import mrfinger.gothicgamemod.entity.capability.data.GothicSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

	
/*
public  class GGMSkillInstance implements IGGMSkillInstance {
		

	public final EntitySkills				name;
	
	public final EntityLivingBase 			entity;
	
	public static final int					maxValue = 100;
	
	public final float 						maxCanLearnFromNotLP;
	
	protected float 						value,
											learnedFromNotLP;

	
	
	public GGMSkillInstance(GothicSkill skill, EntityLivingBase entity) {
		this.name = skill.name;
		this.entity = entity;
		
		this.value = skill.nativeValue;
		this.maxCanLearnFromNotLP = skill.maxCanLearnFromNotLP;

	}
	
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		compound.setFloat("Value", this.value);
		compound.setFloat("FOS", this.learnedFromNotLP);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		
		this.setValue(compound.getFloat("Value"));
		this.learnedFromNotLP = compound.getFloat("FOS");
	}
	

	@Override
	public void setValue(float value) {
		this.value = value > 0.0F ? (value <= this.maxValue ? value : this.maxValue) : 0.0F;
	}

	@Override
	public void changeValue(float changeValue) {
		this.setValue(this.value + changeValue);
	}
	
	
	@Override
	public EntitySkills getName() {
		return this.name;
	}

	@Override
	public float getBaseValue() {
		return this.value;
	}

	@Override
	public float getValue() {
		float m = this.value;
		
		float[] a = new float[this.skillModifiersMap.size()];
		
		int v = 0;
		
		for (ICMap.IPair<IModifiable.ModifierType, Float> p : this.skillModifiersMap.values()) {
			
			float f = p.getValue2();
			
			switch(p.getValue1()) {	
			
			case bModif:
				f *= this.value;
				break;
			case sumModif:
				a[v] = f;
				v++;
				break;
			default:
				break;			
			}
			
			m += f;
		}
		
		for (int i = 0; i < v; ++i) {
			
			m *= a[i] + 1.0F;
		}
		
		return m;
	}
	
	
	public boolean increaseCapability(float increasingValue) {
		
		if (this.learnedFromNotLP >= this.maxCanLearnFromNotLP) return false;
		
		float maxCanIncrease = this.maxCanLearnFromNotLP - this.learnedFromNotLP;
		
		if (maxCanIncrease > increasingValue) increasingValue = maxCanIncrease;
		
		this.changeValue(increasingValue);
		this.learnedFromNotLP += increasingValue;
		
		return true;
	}


	@Override
	public int setModifier(ModifierType type, float value) {
		
		int s = this.skillModifiersMap.size();
		
		for (int i = 0; i < s; ++i) {
			if (!this.skillModifiersMap.containsKey(i)) {
				s = i;
				break;
			}
		}
		
		this.skillModifiersMap.put(s, new CMap.Pair<ModifierType, Float>(type, value));		
		
		return s;
	}

	@Override
	public void removeModifier(int type) {
		this.skillModifiersMap.remove(type);
	}

	@Override
	public ModifierType getModifierType(int value) {
		return this.skillModifiersMap.get(value).getValue1();
	}

	@Override
	public float getModifierValue(int value) {
		return this.skillModifiersMap.get(value).getValue2();
	}


}*/
