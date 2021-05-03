package mrfinger.gothicgamemod.item.equipment;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;
import java.util.UUID;

public class ItemGGMEquipAttributeBonus extends ItemGGMEquip implements IItemGGMEquip {



    protected final Map<IAttribute, Float> requiredsMap;

    protected final Map<IAttribute, Float> bonusMap;


    public ItemGGMEquipAttributeBonus(String unlocalizedName, String texture, byte index, Map<IAttribute, Float> requiredsMap, Map<IAttribute, Float> bonusMap) {
        super(unlocalizedName, texture, index);

        this.requiredsMap = requiredsMap;
        this.bonusMap = bonusMap;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }


    @Override
    public Map<IAttribute, Float> getRequiredsMap() {
        return this.requiredsMap;
    }


    @Override
    public ItemGGMEquipAttributeBonus setRequireds(Map<IAttribute, Float> requireds) {
        this.requiredsMap.putAll(requireds);
        return this;
    }

    @Override
    public boolean isMayEquip(IGGMEntityLivingBase entity)
    {
        for (Map.Entry<IAttribute, Float> e : this.requiredsMap.entrySet())
        {
            if (entity.getEntityAttribute(e.getKey()) == null || e.getValue() > entity.getEntityAttribute(e.getKey()).getBaseValue()) return false;
        }

        return true;
    }

    @Override
    public void onItemEquiped(ItemStack itemStack, IGGMEntityLivingBase player) {

        if (!player.getEntityWorld().isRemote) {

            UUID id;

            if (itemStack.stackTagCompound == null) {

                itemStack.stackTagCompound = new NBTTagCompound();
                id = UUID.randomUUID();
                itemStack.stackTagCompound.setLong("GGMIDMS", id.getMostSignificantBits());
                itemStack.stackTagCompound.setLong("GGMIDLS", id.getLeastSignificantBits());
            }
            else {

                id = new UUID(itemStack.stackTagCompound.getLong("GGMIDMS"), itemStack.stackTagCompound.getLong("GGMIDLS"));
            }

            if (id == null) {

                id = UUID.randomUUID();
                itemStack.stackTagCompound.setLong("GGMIDMS", id.getMostSignificantBits());
                itemStack.stackTagCompound.setLong("GGMIDLS", id.getLeastSignificantBits());
            }

            for (Map.Entry<IAttribute, Float> e : this.bonusMap.entrySet()) {

                IAttributeInstance attributeInstance = player.getEntityAttribute(e.getKey());

                if (attributeInstance == null) continue;

                attributeInstance.applyModifier(new AttributeModifier(id, "BonusFromEquipment", e.getValue(), 0).setSaved(false));

            }
        }
    }

    @Override
    public void onItemRemoved(ItemStack itemStack, IGGMEntityLivingBase player) {

        if (!player.getEntityWorld().isRemote) {

            UUID id = new UUID(itemStack.stackTagCompound.getLong("GGMIDMS"), itemStack.stackTagCompound.getLong("GGMIDLS"));

            for (Map.Entry<IAttribute, Float> e : this.bonusMap.entrySet()) {

                IAttributeInstance attributeInstance = player.getEntityAttribute(e.getKey());

                if (attributeInstance != null) {

                    AttributeModifier am = attributeInstance.getModifier(id);

                    if (am != null) {

                        attributeInstance.removeModifier(am);
                    }
                }
            }
        }
    }

}
