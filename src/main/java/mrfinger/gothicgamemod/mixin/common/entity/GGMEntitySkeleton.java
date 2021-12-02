package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.init.GGMCapabilities;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySkeleton.class)
public abstract class GGMEntitySkeleton extends GGMEntityMob {


    @Inject(method = "applyEntityAttributes", at = @At("TAIL"))
    private void onApplyEntityAttributes(CallbackInfo ci) {

        thisEntity().getAttributeMap().registerAttribute(GGMCapabilities.dexterity).setBaseValue(3.0D);
    }


    @Redirect(method = "attackEntityWithRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityArrow;setDamage(D)V"))
    private void setArrowDamage(EntityArrow arrow, double value) {
        arrow.setDamage(thisEntity().getEntityAttribute(GGMCapabilities.dexterity).getAttributeValue());
    }


    public EntitySkeleton thisEntity() {
        return (EntitySkeleton) (Object) this;
    }

}
