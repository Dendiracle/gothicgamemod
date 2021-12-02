package mrfinger.gothicgamemod.entity.capability;

public interface IIncreaseble {

	
	float getIncreasingValue();
	
	
	double increaseCapability(double value);

	int increaseCapability(int amount);

	
	double calculateIncreasingValue(double value);

	double calculateIncreasingValueWithAdded(double added, double value);

}
