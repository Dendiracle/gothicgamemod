package mrfinger.gothicgamemod.mixin.entity.ai;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAIAttackOnCollide.class)
public class GGMEntityAIAttackOnCollide {


    /*@Shadow private EntityCreature attacker;

    @Inject(method = "updateTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityCreature;attackEntityAsMob(Lnet/minecraft/entity/Entity;)Z"), cancellable = true)
    private void setAttack(CallbackInfo ci) {

        if (thisAttacker().isAnimedAttack()) {

            thisAttacker().chargeAttack();

            ci.cancel();
        }
    }


    private IGGMEntityCreature thisAttacker() {
        return (IGGMEntityCreature) this.attacker;
    }*/

}
