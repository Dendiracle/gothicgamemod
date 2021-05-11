package mrfinger.gothicgamemod.fractions;

import java.util.HashSet;
import java.util.Set;

public class Fraction {


	private final String unlocalizedName;


	public Fraction(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}
	
	
	public final Set<Fraction> alliesSet = new HashSet<>();
	
	public final Set<Fraction> enemiesSet = new HashSet<>();


	@Override
	public int hashCode()
	{
		return this.unlocalizedName.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Fraction && this.unlocalizedName.equals(((Fraction) obj).unlocalizedName);
	}


	@Override
	public String toString()
	{
		return "Fraction is " + this.unlocalizedName;
	}

}
