package mrfinger.gothicgamemod.entity.effect.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.GGMDynamicRangedAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.init.GGMEffects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GGMEffectInstanceDynamicAttributeController extends GGMEffectInstance
{

    protected Map<GGMDynamicRangedAttribute, GGMDynamicAttributeInstance> attributesMap = new HashMap<>();
    protected Set<GGMDynamicAttributeInstance> toUpdateSet = new HashSet<>();

    protected int ticksExisted;


    public GGMEffectInstanceDynamicAttributeController()
    {
        super(GGMEffects.DynamicAttributeController);
    }


    @Override
    public void onSetsToEntity(IGGMEntityLivingBase entity, IGGMEffectInstance oldEffect)
    {
    }


    public void addAttribute(GGMDynamicAttributeInstance attributeInstance)
    {
        this.attributesMap.put((GGMDynamicRangedAttribute) attributeInstance.getAttribute(), attributeInstance);
    }

    public Set<GGMDynamicAttributeInstance> getAttributesToUpdate()
    {
        return this.toUpdateSet;
    }


    /*
    * For registered modifier attribute only.
     */
    public void addToUpdate(GGMDynamicAttributeInstance attributeInstance)
    {
        this.toUpdateSet.add(attributeInstance);
    }


    @Override
    public void onEntityUpdate(IGGMEntityLivingBase entity)
    {
        System.out.println("Debug in GGMEffectInstanceDynamicAttributeController 111:   " + ticksExisted);
        for (Map.Entry<GGMDynamicRangedAttribute, GGMDynamicAttributeInstance> entry : this.attributesMap.entrySet())
        {
            if (this.ticksExisted % entry.getKey().getUpdateInfrequency() == 0)
            {
                System.out.println("Debug in GGMEffectInstanceDynamicAttributeController 222: " + entry.getKey().getUpdateInfrequency() + " " + entry.getValue().getAttribute().getAttributeUnlocalizedName() + " " + entry.getValue().getDynamicValue() + " " + entry.getValue().getCachedRegenValue());
                entry.getValue().changeCurrentValue(entry.getValue().getCachedRegenValue());
            }
        }

        ++this.ticksExisted;
    }

    public void restoreDP()
    {
        for (GGMDynamicAttributeInstance attributeInstance : this.attributesMap.values())
        {
            attributeInstance.restore();
        }
    }


    /*@Override
    public String toString()
    {
        String keks = "";

        for (IGGMDynamicAttributeInstance attributeInstance : this.attributesMap.values())
        {
            keks += attributeInstance.getAttribute().getAttributeUnlocalizedName() + ": " + attributeInstance.getDynamicValue() + " / ";
        }

        return keks;
    }*/
}
