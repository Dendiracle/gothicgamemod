package mrfinger.gothicgamemod.mixin.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(BaseAttribute.class)
public abstract class GGMBaseAttribute implements IGGMAttribute {


    @Shadow @Final private String unlocalizedName;

    protected boolean haveDP;

    protected Map<IGGMAttribute, Float> attributeModifiersMap;

    protected UUID id;


    @Override
    public IGGMAttribute setHaveDP(boolean b) {
        this.haveDP = b;
        return this;
    }

    @Override
    public boolean isHaveDP() {
        return haveDP;
    }


    @Override
    public IGGMAttribute setBonus(IGGMAttribute attribute, float mul) {

        if (this.attributeModifiersMap == null) this.attributeModifiersMap = new HashMap<>();
        this.attributeModifiersMap.put(attribute, mul);
        if (this.id == null && !this.attributeModifiersMap.isEmpty()) this.id = UUID.randomUUID();
        return this;
    }

    @Override
    public IGGMAttribute setBonus(IAttribute attribute, float mul) {

        return this.setBonus((IGGMAttribute) attribute,mul);
    }

    @Override
    public IGGMAttribute setBonuses(Map<IGGMAttribute, Float> map) {

        if (this.attributeModifiersMap == null) this.attributeModifiersMap = new HashMap<>();
        this.attributeModifiersMap.putAll(map);
        if (this.id == null && !this.attributeModifiersMap.isEmpty()) this.id = UUID.randomUUID();
        return this;
    }

    @Override
    public Map<IGGMAttribute, Float> getAttributeModifiersMap() {

        if (this.attributeModifiersMap == null) return null;
        return Collections.unmodifiableMap(this.attributeModifiersMap);
    }

    @Override
    public UUID getModifierID() {
        return this.id;
    }


    @Override
    public String toString() {
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
