package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedMonsterAttributes.class)
public abstract class GGMSharedMonsterAttributes {


    @Inject(method = "writeAttributeInstanceToNBT", at = @At(value = "TAIL"))
    private static void onWriteAttributeInstanceToNBT(IAttributeInstance attributeInstance, CallbackInfoReturnable<NBTTagCompound> cir) {

        ((IGGMAttributeInstance) attributeInstance).saveNBTData(cir.getReturnValue());
    }

    @Inject(method = "applyModifiersToAttributeInstance", at = @At(value = "TAIL"))
    private static void onReadAttributeInstanceFromNBT(IAttributeInstance attributeInstance, NBTTagCompound nbt, CallbackInfo ci) {

        ((IGGMAttributeInstance) attributeInstance).loadNBTData(nbt);
    }

}
