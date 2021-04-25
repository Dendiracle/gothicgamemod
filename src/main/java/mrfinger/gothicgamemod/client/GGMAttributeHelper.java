package mrfinger.gothicgamemod.client;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMIncreasableAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;

public class GGMAttributeHelper {


	private static int counter = 0;

	
	public final IGGMIncreasableAttributeInstance 	attributeInstance;

	public final int								id;

	public final String 							name;

	public int 										guiOffsetX, guiOffsetY;
	
	public float 									addedBefore,
													addedValue;
	
	public int 										upgradeAmounts;
	
	public GGMAttributeHelper(IGGMIncreasableAttributeInstance attributeInstance, String name) {
		this.attributeInstance = attributeInstance;
		this.id = counter++;
		this.name = name;
	}

	@Override
	public String toString() {

		String s = "";

		if (this.attributeInstance instanceof IGGMDynamicAttributeInstance) s += (int) ((IGGMDynamicAttributeInstance) this.attributeInstance).getCurrentValue() + "/";

		s += (int) (this.attributeInstance.getAttributeValue()) + "  (" + (String.format("%.1f", this.attributeInstance.getBaseValue() + this.addedValue)) + ")";

		return s;
	}

}
