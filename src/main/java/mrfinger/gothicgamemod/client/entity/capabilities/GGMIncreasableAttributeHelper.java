package mrfinger.gothicgamemod.client;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMIncreasableAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;

public class GGMAttributeHelper {


	private static int counter = 0;

	
	public final IGGMIncreasableAttributeInstance 	attributeInstance;

	public final boolean							isDynamic;

	public final int								id;

	public final String 							name;
	
	public float 									addedBefore,
													addedValue;
	
	public int 										upgradeAmounts;

	
	public GGMAttributeHelper(IGGMIncreasableAttributeInstance attributeInstance, String name) {
		this.attributeInstance = attributeInstance;
		this.isDynamic = attributeInstance instanceof IGGMDynamicAttributeInstance;
		this.id = counter++;
		this.name = name;
	}

	@Override
	public String toString()
	{
		String s = "";

		if (this.isDynamic) s += (int) ((IGGMDynamicAttributeInstance) this.attributeInstance).getCurrentValue() + "/";
		s += (int) (this.attributeInstance.getAttributeValue()) + "  (" + (String.format("%.1f", this.attributeInstance.getBaseValue() + this.addedValue)) + ")";
		if (this.isDynamic) s += " +" + ((IGGMDynamicAttributeInstance) this.attributeInstance).getCachedRegenValue() * 2.0F + " (" + ((IGGMDynamicAttributeInstance) this.attributeInstance).getNaturalRegen() + ")";

		return s;
	}

}
