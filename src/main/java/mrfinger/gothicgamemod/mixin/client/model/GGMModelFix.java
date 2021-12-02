package mrfinger.gothicgamemod.mixin.client.model;

import mrfinger.gothicgamemod.client.IGGMMinecraft;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelBiped.class, ModelBlaze.class, ModelChicken.class, ModelCreeper.class, ModelGhast.class, ModelIronGolem.class, ModelMagmaCube.class, ModelOcelot.class, ModelQuadruped.class, ModelSlime.class, ModelSnowMan.class, ModelSpider.class, ModelSquid.class, ModelVillager.class, ModelWolf.class})
public class GGMModelFix
{

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal = 0))
    private void addAnimations(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_, CallbackInfo ci)
    {
        if (entity instanceof IGGMEntityLivingBase)((IGGMEntityLivingBase) entity).getActiveAnimationHelper().modifyModel((ModelBase) (Object) this, p_78088_2_, p_78088_3_, ((IGGMMinecraft) Minecraft.getMinecraft()).getTimer().renderPartialTicks);
    }


}
