package mrfinger.gothicgamemod.mixin.common.entity.ai.attributes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.data.entity.EntityCapabilitiesData;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.MinecraftException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Mixin(BaseAttribute.class)
public abstract class GGMBaseAttribute implements IGGMAttribute
{

    @Shadow
    @Final
    private String unlocalizedName;

    protected UUID id;


    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(String p_i1607_1_, double p_i1607_2_,CallbackInfo callbackInfo)
    {
    }


    @Override
    public UUID getUUID()
    {
        return this.id;
    }

    @Override
    public void setUUID(UUID id)
    {
        if (this.id != null) throw new UnsupportedOperationException("Attribute Unique ID cannot be changed");
    }


    @Override
    public double getMaxValue()
    {
        return Double.MAX_VALUE;
    }

    @Override
    public void setMaxValue(double value)
    {
        throw new UnsupportedOperationException("BaseAttribute don't contains attribute's max value");
    }


    @Override
    public IGGMAttributeInstance getNewAttributeInstance(IGGMBaseAttributeMap attributeMap)
    {
        return (IGGMAttributeInstance) new ModifiableAttributeInstance((BaseAttributeMap) attributeMap, this);
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

        return new IGGMAttributeInstance.ModifiableAttributeShapshot(baseValue, attributeModifiers);
    }

    @Override
    public void convertAttributeInstanceToBytes(IGGMAttributeInstance.IAttributeSnapshot attributeSnapshot, ByteBuf buf)
    {
        buf.writeDouble(attributeSnapshot.getBaseValue());
        buf.writeShort(attributeSnapshot.getAttributeModifiers().size());

        for (AttributeModifier attributeModifier : attributeSnapshot.getAttributeModifiers())
        {
            buf.writeLong(attributeModifier.getID().getMostSignificantBits());
            buf.writeLong(attributeModifier.getID().getLeastSignificantBits());
            buf.writeDouble(attributeModifier.getAmount());
            buf.writeByte(attributeModifier.getOperation());
        }
    }

    @Override
    public String toString()
    {
        return this.getClass().toString() + ": " + this.unlocalizedName;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof IAttribute)
        {
            return this.unlocalizedName.equals(((IAttribute) obj).getAttributeUnlocalizedName());
        }

        return false;
    }

}
