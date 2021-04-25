package mrfinger.gothicgamemod.entity.capability.attributes;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class GGMIncreasableAttributeInfo {


	protected final float 	defaultValue;

	protected final double 	maxValue;

	protected final float 	increasingValue;

	protected final float	valueAfterIncreasingValueDecreases;

	protected final byte 	maxIncreasesNeedLP;


	public GGMIncreasableAttributeInfo(float defaultValue, double maxValue, float increasingValue, float valueAfterIncreasingValueDecreases, byte maxIncreasesNeedLP) {
		this.defaultValue = defaultValue;
		this.maxValue = maxValue;
		this.increasingValue = increasingValue;
		this.valueAfterIncreasingValueDecreases = valueAfterIncreasingValueDecreases;
		this.maxIncreasesNeedLP = maxIncreasesNeedLP;
	}


	public float getDefaultValue() {
		return this.defaultValue;
	}

	public double getMaxValue() {
		return this.maxValue;
	}

	public float getIncreasingValue() {
		return this.increasingValue;
	}

	public float getValueAfterIncreasingValueDecreases() {
		return this.valueAfterIncreasingValueDecreases;
	}

	public byte getMaxIncreasesNeedLP() {
		return this.maxIncreasesNeedLP;
	}


}
