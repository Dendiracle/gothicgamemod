package mrfinger.gothicgamemod.entity.capability.attribute.generic;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.IRegenModifierInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class GGMDynamicRangedAttribute extends RangedAttribute implements IGGMAttribute
{

    protected int updateInfrequency;

    public GGMDynamicRangedAttribute(String description, double defaultValue, double minValue, double maxValue, int updateInfrequency)
    {
        super(description, defaultValue, minValue, maxValue);
        this.updateInfrequency = updateInfrequency;
    }


    @Override
    public IGGMAttributeInstance getNewAttributeInstance(IGGMBaseAttributeMap attributeMap)
    {
        return new GGMDynamicAttributeInstance(attributeMap, this);
    }

    @Override
    public IGGMAttributeInstance.IAttributeSnapshot getAttributeInstanceSnapshotFromBytes(ByteBuf buf)
    {
        double baseValue = buf.readDouble();
        int size = buf.readInt();
        Collection<AttributeModifier> attributeModifiers = new ArrayList(size);

        for (int i = 0; i < size; ++i)
        {
            attributeModifiers.add(new AttributeModifier(new UUID(buf.readLong(), buf.readLong()), "Unknown synced attribute modifier", buf.readDouble(), buf.readByte()));
        }

        return new GGMDynamicAttributeInstance.GGMDynamicAttributeShapshot(baseValue, attributeModifiers, buf.readDouble(), buf.readFloat(), new ArrayList<IRegenModifierInstance>());
    }

    @Override
    public void convertAttributeInstanceToBytes(IGGMAttributeInstance.IAttributeSnapshot attributeSnapshot, ByteBuf buf)
    {
        GGMDynamicAttributeInstance.GGMDynamicAttributeShapshot dynamicAttributeShapshot = (GGMDynamicAttributeInstance.GGMDynamicAttributeShapshot) attributeSnapshot;

        buf.writeDouble(dynamicAttributeShapshot.getBaseValue());
        buf.writeShort(dynamicAttributeShapshot.getAttributeModifiers().size());

        for (AttributeModifier attributeModifier : dynamicAttributeShapshot.getAttributeModifiers())
        {
            buf.writeLong(attributeModifier.getID().getMostSignificantBits());
            buf.writeLong(attributeModifier.getID().getLeastSignificantBits());
            buf.writeDouble(attributeModifier.getAmount());
            buf.writeByte(attributeModifier.getOperation());
        }

        buf.writeDouble(dynamicAttributeShapshot.getDynamicValue());
        buf.writeFloat(dynamicAttributeShapshot.getNaturalRegen());
    }


    public int getUpdateInfrequency()
    {
        return this.updateInfrequency;
    }

}
