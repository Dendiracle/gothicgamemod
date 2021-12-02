package mrfinger.gothicgamemod.mixin.common.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(BaseAttributeMap.class)
public abstract class GGMBaseAttributeMap implements IGGMBaseAttributeMap
{

    protected IGGMEntityLivingBase entity;
    @Shadow private Map attributes;
    @Shadow private Map attributesByName;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci)
    {
    }


    @Override
    public IGGMEntityLivingBase getEntity() {
        return entity;
    }

    @Override
    public void setEntity(IGGMEntityLivingBase entity)
    {
        IGGMEntityLivingBase old = this.entity;
        this.entity = entity;

        Map<IGGMAttribute, IGGMAttributeInstance> map = this.attributes;

        for (IGGMAttributeInstance attributeInstance : map.values())
        {
            attributeInstance.onSetEntity(old, entity);
        }

    }


    @Override
    public void resizeCollections()
    {
        Map old = this.attributes;
        this.attributes = new HashMap(old.size(), 1F);
        this.attributes.putAll(old);

        old = this.attributesByName;
        this.attributesByName = new HashMap(old.size(), 1F);
        this.attributesByName.putAll(old);
    }

}
