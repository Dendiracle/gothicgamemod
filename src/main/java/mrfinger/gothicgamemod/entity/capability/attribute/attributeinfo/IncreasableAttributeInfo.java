package mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo;


import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;

public class IncreasableAttributeInfo<Instance extends IGGMAttributeInstance> extends BaseAttributeInfo<Instance>
{

	protected final float 	increasingValue;
	protected final float	valueAfterIncreasingValueDecreases;
	protected final byte 	maxIncreasesNeedLP;


	public IncreasableAttributeInfo(float defaultValue, double maxValue, float increasingValue, float valueAfterIncreasingValueDecreases, byte maxIncreasesNeedLP)
	{
		super(defaultValue, maxValue);

		this.increasingValue = increasingValue;
		this.valueAfterIncreasingValueDecreases = valueAfterIncreasingValueDecreases;
		this.maxIncreasesNeedLP = maxIncreasesNeedLP;
	}

	@Override
	public float getIncreasingValue() {
		return this.increasingValue;
	}

	@Override
	public float getValueAfterIncreasingValueDecreases() {
		return this.valueAfterIncreasingValueDecreases;
	}

	@Override
	public byte getMaxIncreasesNeedLP() {
		return this.maxIncreasesNeedLP;
	}

}
