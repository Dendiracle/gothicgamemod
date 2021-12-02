package mrfinger.gothicgamemod.client.entity.capability;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;

public class GGMDynamicAttributeHelper extends GGMIncreasableAttributeHelper
{


    public int upgradeRegenAmount;

    public float addedRegenBefore;

    public float addedRegen;


    public GGMDynamicAttributeHelper(IGGMDynamicAttributeInstance attributeInstance, String name) {
        super(attributeInstance, name);
    }


    @Override
    public IGGMDynamicAttributeInstance getAttributeInstance() {
        return (IGGMDynamicAttributeInstance) super.getAttributeInstance();
    }

    @Override
    public String toDrawValue() {

        return (int) this.getAttributeInstance().getDynamicValue() + "(+" +
                this.getAttributeInstance().getCachedRegenValue() * 2.0F + ")/" + super.toDrawValue() +
                "/(" + this.getAttributeInstance().getNaturalRegen() + ")";
    }


    public void increaseRegen()
    {
        ++this.upgradeRegenAmount;
        this.addedRegenBefore = this.getAttributeInstance().calculateIncreasingRegenValueWithAdded(this.addedRegen, this.getAttributeInstance().getNaturalRegenIncreasingValue());
        this.addedRegen += this.addedRegenBefore;
    }

    public void increaseRegen(int amount)
    {
        this.upgradeRegenAmount += amount;
        for (int i = 0; i < amount; ++i)
        {
            this.addedRegenBefore = this.getAttributeInstance().calculateIncreasingRegenValueWithAdded(this.addedRegen, this.getAttributeInstance().getNaturalRegenIncreasingValue());
            this.addedRegen += this.addedRegenBefore;
        }
    }


    @Override
    public void nullify() {
        super.nullify();

        this.addedRegenBefore = 0.0F;
        this.addedRegen = 0.0F;
    }
}
