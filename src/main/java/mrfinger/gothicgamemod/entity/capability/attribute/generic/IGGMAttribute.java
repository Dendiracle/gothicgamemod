package mrfinger.gothicgamemod.entity.capability.attribute.generic;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.UUID;

public interface IGGMAttribute extends IAttribute
{

    default UUID getUUID()
    {
        return null;
    }


    default double getMaxValue()
    {
        return Double.MAX_VALUE;
    }

    default void setMaxValue(double value) {}


    IGGMAttributeInstance getNewAttributeInstance(IGGMBaseAttributeMap attributeMap);


    IGGMAttributeInstance.IAttributeSnapshot getAttributeInstanceSnapshotFromBytes(ByteBuf buf);

    void convertAttributeInstanceToBytes(IGGMAttributeInstance.IAttributeSnapshot attributeSnapshot, ByteBuf buf);

}
