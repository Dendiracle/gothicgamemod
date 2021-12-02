package mrfinger.gothicgamemod.data.entity;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.IRegenModifierInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifier;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifierInstanceSimple;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityCapabilitiesData
{

    private static final Map<UUID, IGGMAttribute> GenericAttributesMap = new HashMap<>();
    private static final Map<UUID, IGGMAttribute> UnmodifiableGenericAttributesMap = Collections.unmodifiableMap(GenericAttributesMap);

    private static final Map<String, RegenModifier> GenericRegenModifiersMap = new HashMap<>();
    private static final Map<String, Function<RegenModifier, IRegenModifierInstance>> RegenModifiersInstanceDispencer = new HashMap<>();


    public static void loadData()
    {
        registerRegenModifiersInstance(RegenModifierInstanceSimple.uniqueClassID(), regenModifier -> new RegenModifierInstanceSimple(regenModifier));
    }


    public static void registerGenericAttribute(IGGMAttribute attribute)
    {
        GenericAttributesMap.put(attribute.getUUID(), attribute);
    }

    public static IGGMAttribute getRegisteredGenericAttribute(UUID id)
    {
        return GenericAttributesMap.get(id);
    }

    public static Map<UUID, IGGMAttribute> getGenericAttributesMap()
    {
        return UnmodifiableGenericAttributesMap;
    }


    public static void registerRegenModifiersInstance(String uniqueClassID, Function<RegenModifier, IRegenModifierInstance> function)
    {
        RegenModifiersInstanceDispencer.put(uniqueClassID, function);
    }


    public static void saveRegenModifier(IRegenModifierInstance regenModifierInstance, NBTTagCompound nbt)
    {
        nbt.setString("Name", regenModifierInstance.getGenericRegenModifier().getUnlocolizedName());
        nbt.setInteger("Opr", regenModifierInstance.getGenericRegenModifier().getOperationType());
        nbt.setString("ICID", regenModifierInstance.getUniqueClassID());
        NBTTagCompound instanceNBT = new NBTTagCompound();
        regenModifierInstance.saveNBTData(instanceNBT);
        nbt.setTag("Ins", instanceNBT);
    }

    public static IRegenModifierInstance loadRegenModifierInstanceFromNBT(NBTTagCompound nbt)
    {

        String string = nbt.getString("Name");
        RegenModifier genericRegenModifier = GenericRegenModifiersMap.get(string);

        if (genericRegenModifier == null)
        {
            genericRegenModifier = new RegenModifier(string, nbt.getInteger("Opr"));
        }

        IRegenModifierInstance regenModifierInstance = RegenModifiersInstanceDispencer.get(nbt.getString("ICID")).apply(genericRegenModifier);
        regenModifierInstance.loadNBTData((NBTTagCompound) nbt.getTag("Ins"));

        return regenModifierInstance;
    }

}
