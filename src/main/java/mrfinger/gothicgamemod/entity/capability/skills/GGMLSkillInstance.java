package mrfinger.gothicgamemod.entity.capability.skills;

/*public abstract class GGMLSkillInstance extends GGMSkillInstance implements IIncreaseble {
		
	
	protected float	 	increasingValue,
						valueAfterIncreasingValueDecreases;
	
	
	public GGMLSkillInstance(GothicSkill skill, EntityLivingBase entity) {
		super(skill, entity);
		
		this.increasingValue = skill.increasingValue;
		this.valueAfterIncreasingValueDecreases = skill.valueAfterIncreasingValueDecreases;
	}
	

	@Override
	public float getIncreasingValue() {
		return this.increasingValue;
	}
	

	@Override
	public boolean increaseAttribute(float increasingValue) {
		
		if (this.value >= this.maxValue || this.learnedFromNotLP >= this.maxCanLearnFromNotLP) return false;
		
		float maxCanIncrease = this.maxCanLearnFromNotLP - this.learnedFromNotLP;
		
		if (maxCanIncrease > increasingValue) increasingValue = maxCanIncrease;
		
		this.learnedFromNotLP += increasingValue;
		
		increasingValue = this.calculateIncreasingValueWithArgs(increasingValue);
			
		if ((this.maxValue - (this.value + increasingValue)) < 0.01F) this.setValue(this.maxValue);
		else this.changeValue(increasingValue);
			
		return true;
	}

	@Override
	public int increaseAttribute(int amount) {
		return 0;
	}


/*	@Override
	public float calculateIncreasingValueWithArgs(float value) {
		
		float step = this.valueAfterIncreasingValueDecreases;
			
		int separator = 1;		
		while (this.value >= step) {
			separator++;
			step += this.valueAfterIncreasingValueDecreases;
		}
			
		value /= separator;			
						
		while ((this.value + value) > step) {
			separator++;
			value -= (((this.value + value) - step) / separator);
		}
			
		return value;		
	}*/

/*	@Override
	public float calculateIncreasingValueWithAdded(float added, float value) {
		
		float step = this.valueAfterIncreasingValueDecreases;
		
		float valueWithAdded = this.value + added;						
			
		int separator = 1;		
		while (valueWithAdded >= step) {
			separator++;
			step += this.valueAfterIncreasingValueDecreases;
		}
		
		value /= separator;			
					
		while ((valueWithAdded + value) > step) {
			separator++;
			value -= (((valueWithAdded + value) - step) / separator);
		}
		
		return value;		
	}*/
	
//}
