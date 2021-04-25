package mrfinger.gothicgamemod.battle;


import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class UseSpendings {


    IAttribute dynamicAttribute;

    protected float attributeMultiplier;

    protected float spendsFromD;


    public UseSpendings(IGGMAttribute dynamicAttribute, float attributeMultiplier, float spendsFromD) {

        if (dynamicAttribute == null) throw new IllegalArgumentException("Dynamic Attribute cannot be null");
        else if (!dynamicAttribute.isHaveDP()) throw new IllegalArgumentException("Attribute must be dynamic");
        this.dynamicAttribute = dynamicAttribute;
        this.attributeMultiplier = attributeMultiplier;
        this.spendsFromD = spendsFromD;
    }


    public IAttribute getDynamicAttribute() {
        return dynamicAttribute;
    }

    public float getAttributeMultiplier() {
        return attributeMultiplier;
    }

    public float getSpendsFromD() {
            return spendsFromD;
        }

}

