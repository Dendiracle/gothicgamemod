package mrfinger.gothicgamemod.mixin.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attributes.*;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(BaseAttributeMap.class)
public abstract class GGMBaseAttributeMap implements IGGMBaseAttributeMap
{

    @Shadow @Final protected Map attributesByName;
    @Shadow @Final protected Map attributes;

    protected Map<IAttribute, IGGMDynamicAttributeInstance> dpiMap;

    private Set<IGGMDynamicAttributeInstance> toUpdateSet;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {

        this.dpiMap = new HashMap();
        this.toUpdateSet = new HashSet();
    }


    @Override
    public IGGMIncreasableAttributeInstance registerAttribute(IGGMAttribute attribute, GGMIncreasableAttributeInfo attributeInfo) {


        if (attributeInfo == null) {

            throw new NullPointerException("Attribute info must be not null");
        }
        else {

            IGGMIncreasableAttributeInstance ai;

            if (attribute.isHaveDP()) {

                ai = new GGMDynamicAttributeInstance(this, attribute, (GGMDPAttributeInfo) attributeInfo);
                this.dpiMap.put(attribute, (IGGMDynamicAttributeInstance) ai);
            }
            else {
                ai = new GGMIncreasableAttributeInstance(this, attribute, attributeInfo);
            }

            IAttributeInstance old = (IAttributeInstance) this.attributesByName.put(attribute.getAttributeUnlocalizedName(), ai);
            this.attributes.put(attribute, ai);

            return ai;
        }
    }

    @Override
    public IGGMBaseAttributeMap resizeCollections() {

        Map<IAttribute, IGGMDynamicAttributeInstance> old = this.dpiMap;
        this.dpiMap = new HashMap<>(old.size(), 1F);
        this.dpiMap.putAll(old);

        Set<IGGMDynamicAttributeInstance> oldSet = this.toUpdateSet;
        this.toUpdateSet = new HashSet<>(1, 1F);
        this.toUpdateSet.addAll(oldSet);

        return this;
    }

    @Override
    public Map<IAttribute, IAttributeInstance> getAllAttributesMap() {
        return this.attributes;
    }


    @Override
    public IGGMDynamicAttributeInstance getDPI(IGGMAttribute attribute) {
        return this.dpiMap.get(attribute);
    }

    @Override
    public Collection<IGGMDynamicAttributeInstance> getDPIColl() {
        return this.dpiMap != null ? this.dpiMap.values() : null;
    }

    @Override
    public Map<IAttribute, IGGMDynamicAttributeInstance> getDpiMap() {
        return dpiMap != null ? Collections.unmodifiableMap(this.dpiMap) : null;
    }


    @Override
    public void addDPToUpdate(IGGMDynamicAttributeInstance dp) {
        this.toUpdateSet.add(dp);
    }

    @Override
    public Set<IGGMDynamicAttributeInstance> getToUpdateSet() {
        return this.toUpdateSet;
    }

}
