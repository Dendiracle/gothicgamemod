package mrfinger.gothicgamemod.data.entity;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.IRegenModifierInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifier;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifierInstanceSimple;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.function.Function;

public class EntityCapabilitiesData
{

    private static byte[] counter = {0, 0, 0, 0, 0, 0, 0 ,0};

    private static final Map<UUID, IGGMAttribute> GenericAttributesMapByUUID = new HashMap<>();
    private static final Map<UUID, IGGMAttribute> UnmodifiableGenericAttributesMapByUUID = Collections.unmodifiableMap(GenericAttributesMapByUUID);
    private static final Map<String, Set<IGGMAttribute>> GenericAttributesMapByUnlocolizedName = new HashMap<>();
    private static final Map<String, Set<IGGMAttribute>> UnmodifiableGenericAttributesMapByUnlocolizedName = Collections.unmodifiableMap(GenericAttributesMapByUnlocolizedName);

    private static final Map<String, RegenModifier> GenericRegenModifiersMap = new HashMap<>();
    private static final Map<String, Function<RegenModifier, IRegenModifierInstance>> RegenModifiersInstanceDispencer = new HashMap<>();


    public static void loadData()
    {
        registerRegenModifiersInstance(RegenModifierInstanceSimple.uniqueClassID(), regenModifier -> new RegenModifierInstanceSimple(regenModifier));
    }


    public static void registerGenericAttribute(IGGMAttribute attribute)
    {
        UUID id = UUID.nameUUIDFromBytes(counter);

        int i = 0;
        while (counter[i] >= 127)
        {
            if (counter[i] < 127)
            {
                counter[i]++;
                break;
            }
            else
            {
                i++;
            }
        }

        attribute.setUUID(id);
        GenericAttributesMapByUUID.put(attribute.getUUID(), attribute);

        Set<IGGMAttribute> attributes =  GenericAttributesMapByUnlocolizedName.get(attribute.getAttributeUnlocalizedName());
        if (attributes == null)
        {
            attributes = new HashSet<>();

        }
        attributes.add(attribute);
        GenericAttributesMapByUnlocolizedName.put(attribute.getAttributeUnlocalizedName(), attributes);
    }

    public static IGGMAttribute getRegisteredGenericAttribute(UUID id)
    {
        return GenericAttributesMapByUUID.get(id);
    }

    public static Map<UUID, IGGMAttribute> getGenericAttributesMapByUUID()
    {
        return UnmodifiableGenericAttributesMapByUUID;
    }

    public static Map<String, Set<IGGMAttribute>> getGenericAttributesMapByUnlocolizedName()
    {
        return UnmodifiableGenericAttributesMapByUnlocolizedName;
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
