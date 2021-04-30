package mrfinger.gothicgamemod.client.entity.capabilities;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMIncreasableAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;

public class GGMIncreasableAttributeHelper
{

	private static int counter = 0;

	
	public final IGGMIncreasableAttributeInstance 	attributeInstance;

	public final boolean							isDynamic;

	public final int								id;

	public final String 							name;

	public float									addedValue;
	
	public int 										upgradeAmounts;


	protected String str;

	protected double prevBV;
	protected double prevAV;


	
	public GGMIncreasableAttributeHelper(IGGMIncreasableAttributeInstance attributeInstance, String name)
	{

		this.attributeInstance = attributeInstance;
		this.isDynamic = attributeInstance instanceof IGGMDynamicAttributeInstance;
		this.id = counter++;
		this.name = name;

		this.prevBV = attributeInstance.getBaseValue();
		this.prevAV = attributeInstance.getAttributeValue();
		this.updateStr();
	}


	public IGGMIncreasableAttributeInstance getAttributeInstance()
	{
		return this.attributeInstance;
	}


	public String toDrawValue()
	{
		if (this.prevBV != this.attributeInstance.getBaseValue() || this.prevAV != this.attributeInstance.getAttributeValue())
		{
			this.prevBV = this.attributeInstance.getBaseValue();
			this.prevAV = this.attributeInstance.getAttributeValue();
			this.updateStr();
		}
		return this.str;
	}

	protected void updateStr()
	{
		this.str = (int) this.prevAV + "  (" + (String.format("%.1f", this.prevBV + this.addedValue)) + ")";
	}


	@Override
	public String toString()
	{
		String s = this.name + " (" + this.attributeInstance.getAttribute().getAttributeUnlocalizedName() + "): " + this.toDrawValue();
		return s;
	}


	public void increase()
	{
		++this.upgradeAmounts;
		this.addedValue += (float) this.attributeInstance.calculateIncreasingValueWithAdded(this.addedValue, this.attributeInstance.getIncreasingValue());
		this.updateStr();
	}

	public void increase(int amount)
	{
		this.upgradeAmounts += amount;

		for (int i = 0; i < amount; ++i)
		{
			this.addedValue += (float) this.attributeInstance.calculateIncreasingValueWithAdded(this.addedValue, this.attributeInstance.getIncreasingValue());
		}
		this.updateStr();
	}


	public void decrease()
	{
		--this.upgradeAmounts;
		this.addedValue -= (float) this.attributeInstance.calculateDecreasingValueWithAdded(this.addedValue, this.attributeInstance.getIncreasingValue());
		this.updateStr();
	}

	public void decrease(int amount)
	{
		--this.upgradeAmounts;
		for (int i = 0; i < amount; ++i)
		{
			this.addedValue -= (float) this.attributeInstance.calculateDecreasingValueWithAdded(this.addedValue, this.attributeInstance.getIncreasingValue());
		}
		this.updateStr();
	}



	public void nullify()
	{
		this.upgradeAmounts = 0;
		this.addedValue = 0.0F;
		this.updateStr();
	}


}
