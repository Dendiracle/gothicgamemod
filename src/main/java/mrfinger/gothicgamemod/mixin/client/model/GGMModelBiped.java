package mrfinger.gothicgamemod.mixin.client.model;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public class GGMModelBiped {


    @Shadow public ModelRenderer bipedRightArm;
    private float attackTick;
    private float attackDuration;


    @Inject(method = "setRotationAngles", at = @At("RETURN"))
    private void onSetRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity, CallbackInfo ci) {

        if (entity instanceof EntityPlayer) {

            IGGMEntityPlayer player = (IGGMEntityPlayer) entity;

            float ticksLeft = player.getAttackTicksLeft();

            if (ticksLeft > 0) {

                if (this.attackDuration <= 0) {
                    this.attackDuration = player.getNewAttackDuration();
                    this.attackTick = (this.attackDuration - player.getAttackTick());
                }

                float progress = (this.attackDuration - ticksLeft) / this.attackTick;
                if (progress > 1.5F) progress = 1.5F;

                this.bipedRightArm.rotateAngleY = -((float) Math.PI * 0.2F* progress);
                this.bipedRightArm.rotateAngleZ = -((float) Math.PI * 0.2F * progress);
                this.bipedRightArm.rotateAngleX = -((float) Math.PI * 0.15F * progress);
                this.bipedRightArm.rotationPointX += 1.5F * progress;
                this.bipedRightArm.rotationPointZ -= progress;
                //System.out.println(progress);
            }

            else {
                this.attackDuration = 0.0F;
                this.attackTick = 0.0F;
            }
        }
    }



}
