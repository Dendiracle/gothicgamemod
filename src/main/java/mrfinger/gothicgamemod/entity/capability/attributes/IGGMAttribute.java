package mrfinger.gothicgamemod.entity.capability.attributes;

import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Map;
import java.util.UUID;

public interface IGGMAttribute extends IAttribute {



    IGGMAttribute setHaveDP(boolean b);

    IGGMAttribute setBonus(IGGMAttribute attribute, float mul);

    IGGMAttribute setBonus(IAttribute attribute, float mul);

    IGGMAttribute setBonuses(Map<IGGMAttribute, Float> map);


    boolean isHaveDP();

    Map<IGGMAttribute, Float> getAttributeModifiersMap();

    UUID getModifierID();

}
