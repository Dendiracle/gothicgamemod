package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMModifiableAttributeInstance;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedMonsterAttributes.class)
public abstract class GGMSharedMonsterAttributes {


    /*@Inject(method = "writeAttributeInstanceToNBT", at = @At(value = "TAIL"))
    private static void onWriteAttributeInstanceToNBT(IAttributeInstance attributeInstance, CallbackInfoReturnable<NBTTagCompound> cir) {
        if (attributeInstance instanceof IGGMDynamicAttributeInstance) {
            cir.getReturnValue().setDouble("Curr", ((IGGMDynamicAttributeInstance) attributeInstance).getCurrentValue());
        }
    }

    @Inject(method = "applyModifiersToAttributeInstance", at = @At(value = "TAIL"))
    private static void onReadAttributeInstanceFromNBT(IAttributeInstance attributeInstance, NBTTagCompound nbt, CallbackInfo ci) {
        if (attributeInstance instanceof IGGMDynamicAttributeInstance) {
            ((IGGMDynamicAttributeInstance) attributeInstance).setCurrentValue(nbt.getDouble("Curr"));
        }
    }*/

    @Inject(method = "writeAttributeInstanceToNBT", at = @At(value = "TAIL"))
    private static void onWriteAttributeInstanceToNBT(IAttributeInstance attributeInstance, CallbackInfoReturnable<NBTTagCompound> cir) {

        ((IGGMModifiableAttributeInstance) attributeInstance).saveNBTData(cir.getReturnValue());
    }

    @Inject(method = "applyModifiersToAttributeInstance", at = @At(value = "TAIL"))
    private static void onReadAttributeInstanceFromNBT(IAttributeInstance attributeInstance, NBTTagCompound nbt, CallbackInfo ci) {

        ((IGGMModifiableAttributeInstance) attributeInstance).loadNBTData(nbt);
    }

}
