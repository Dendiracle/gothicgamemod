package mrfinger.gothicgamemod.entity.capability.attributes;

import mrfinger.gothicgamemod.entity.capability.ICapabilitySaveable;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.UUID;

public interface IGGMModifiableAttributeInstance extends IAttributeInstance, ICapabilitySaveable {


    void changeBaseValue(double changeValue);


    double increaseAttribute(double value);


    default void setModifierAmount(UUID id, double amount) {}

}
