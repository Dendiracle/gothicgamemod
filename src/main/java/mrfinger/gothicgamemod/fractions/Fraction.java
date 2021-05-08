package mrfinger.gothicgamemod.fractions;

import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

public class Fraction {


	private final String unlocalizedName;


	public Fraction(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}
	
	
	public final Set<Class<? extends EntityLivingBase>> fractionSet = new LinkedHashSet();
	
	public final Set<Class<? extends EntityLivingBase>> enemiesSet = new LinkedHashSet();


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

}
