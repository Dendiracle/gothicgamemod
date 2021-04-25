package mrfinger.gothicgamemod.entity.capability.attributes;

import mrfinger.gothicgamemod.entity.capability.ICapabilitySaveable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.Collection;
import java.util.UUID;

public interface IGGMModifiableAttributeInstance extends IAttributeInstance, ICapabilitySaveable {


    default void setModifierAmount(UUID id, double amount) {}

}
