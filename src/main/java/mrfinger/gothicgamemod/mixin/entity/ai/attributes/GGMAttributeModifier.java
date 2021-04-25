package mrfinger.gothicgamemod.mixin.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AttributeModifier.class)
public abstract class GGMAttributeModifier implements IGGMAttributeModifier {


    @Shadow protected double amount;


    /*@ModifyArg(method = "<init>(Ljava/util/UUID;Ljava/lang/String;DI)V", at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/Validate;inclusiveBetween(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Comparable;Ljava/lang/String;[Ljava/lang/Object;)V"), index = 1)
    private Object fixInit(Object value) {
        return Integer.valueOf(5);
    }*/


    @Override
    public IGGMAttributeModifier setAmount(double amount) {

        this.amount = amount;
        return this;
    }
}
