package mrfinger.gothicgamemod.entity.capability.attributes;

public interface IGGMIncreasableAttributeInstance extends IGGMModifiableAttributeInstance {


	GGMIncreasableAttributeInfo getAttributeInfo();


	boolean isBonus();

	boolean setBonuses();


	float getDefaultValue();

	double getMaxValue();

	float getIncreasingValue();


	int increaseAttribute(int amount);


	double calculateIncreasingValue(double value);

	double calculateIncreasingValueWithAdded(double added, double value);


	double calculateDecreasingValue(double value);

	double calculateDecreasingValueWithAdded(double added, double value);

}


