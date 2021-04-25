package mrfinger.gothicgamemod.entity.capability.attributes;

import mrfinger.gothicgamemod.entity.capability.IIncreaseble;

public interface IGGMIncreasableAttributeInstance extends IGGMModifiableAttributeInstance {


	GGMIncreasableAttributeInfo getAttributeInfo();


	boolean isBonus();

	boolean setBonuses();

	
	void changeBaseValue(double changeValue);


	float getDefaultValue();

	double getMaxValue();

	float getIncreasingValue();

	float getValueAfterIncreasingValueDecreases();

	byte getMaxIncreasesNeedLP();


	double increaseAttribute(double value);

	int increaseAttribute(int amount);


	double calculateIncreasingValue(double value);

	double calculateIncreasingValueWithAdded(double added, double value);

}


