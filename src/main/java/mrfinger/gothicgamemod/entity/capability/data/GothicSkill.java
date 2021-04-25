package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.capability.EntitySkills;

public class GothicSkill {
	
	
	public final EntitySkills	 	name;	
	
	public final float  		nativeValue,
								maxCanLearnFromNotLP,
								increasingValue,
								valueAfterIncreasingValueDecreases;	
	
	
	public GothicSkill(EntitySkills name, float nativeValue, float maxCanLearnFromNotLP) {		
		this(name, nativeValue, maxCanLearnFromNotLP, 0.0F, 0.0F);
	}
	
	public GothicSkill(EntitySkills name, float nativeValue, float maxCanLearnFromNotLP, float increasingValue, float valueAfterLearningValueDecreases) {
		
		this.name = name;
		this.nativeValue = nativeValue;
		this.maxCanLearnFromNotLP = maxCanLearnFromNotLP;
		this.increasingValue = increasingValue;
		this.valueAfterIncreasingValueDecreases = valueAfterLearningValueDecreases;
	}

}
