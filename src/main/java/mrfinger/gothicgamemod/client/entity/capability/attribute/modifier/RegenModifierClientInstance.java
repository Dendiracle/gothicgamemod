package mrfinger.gothicgamemod.client.entity.capability.attribute.modifier;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.IRegenModifierInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifier;
import net.minecraft.nbt.NBTTagCompound;

@SideOnly(Side.CLIENT)
public class RegenModifierClientInstance implements IRegenModifierInstance
{

    protected final RegenModifier genericModifier;

    float amount;


    public RegenModifierClientInstance(RegenModifier genericModifier, float amount)
    {
        this(genericModifier);
        this.amount = amount;
    }

    public RegenModifierClientInstance(RegenModifier genericModifier)
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
        return false;
    }


    public static String uniqueClassID()
    {
        return "regen_modifier.client";
    }

    @Override
    public String getUniqueClassID()
    {
        return uniqueClassID();
    }

    public void saveNBTData(NBTTagCompound nbt)
    {
    }

    public void loadNBTData(NBTTagCompound nbt)
    {
    }


    @Override
    public String toString() {
        return this.getClass() + "/" + this.getGenericRegenModifier().getUnlocolizedName() + ": " + amount;
    }



}
