package mrfinger.gothicgamemod.battle;


import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;

public class UseSpendings {


    IGGMAttribute attribute;

    protected float attributeMultiplier;

    protected float spendsFromD;


    public UseSpendings(IGGMAttribute attribute, float attributeMultiplier, float spendsFromD) {

        if (attribute == null) throw new IllegalArgumentException("Attribute cannot be null");
        this.attribute = attribute;
        this.attributeMultiplier = attributeMultiplier;
        this.spendsFromD = spendsFromD;
    }


    public IGGMAttribute getAttribute() {
        return attribute;
    }

    public float getAttributeMultiplier() {
        return attributeMultiplier;
    }

    public float getSpendsFromD() {
            return spendsFromD;
        }


    @Override
    public String toString() {
        return this.getClass() + ": " + this.attribute.getAttributeUnlocalizedName() + " Attribute multiplier: " + this.attributeMultiplier + " Spends from DP multiplier: " + this.spendsFromD;
    }
}

