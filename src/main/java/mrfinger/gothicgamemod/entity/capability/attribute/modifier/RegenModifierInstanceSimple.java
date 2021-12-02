package mrfinger.gothicgamemod.entity.capability.attribute.modifier;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;

public class RegenModifierInstanceSimple implements IRegenModifierInstance
{

    protected final RegenModifier genericModifier;

    float amount;


    public RegenModifierInstanceSimple(RegenModifier genericModifier, float amount)
    {
        this(genericModifier);

        this.amount = amount;
    }

    public RegenModifierInstanceSimple(RegenModifier genericModifier)
    {
        this.genericModifier = genericModifier;
    }


    @Override
    public RegenModifier getGenericRegenModifier()
    {
        return this.genericModifier;
    }


    @Override
    public float getAddedValue(GGMDynamicAttributeInstance attributeInstance, float calculatedValue)
    {
        return this.amount;
    }


    @Override
    public boolean isSaved()
    {
        return true;
    }

    public static String uniqueClassID()
    {
        return "regen_modifier.simple";
    }

    @Override
    public String getUniqueClassID()
    {
        return uniqueClassID();
    }

    public void saveNBTData(NBTTagCompound nbt)
    {
        nbt.setFloat("Amount", this.amount);
    }

    public void loadNBTData(NBTTagCompound nbt)
    {
        this.amount = nbt.getFloat("Amount");
    }


    @Override
    public String toString() {
        return this.getClass() + "/" + this.getGenericRegenModifier().getUnlocolizedName() + ": " + amount;
    }

}
