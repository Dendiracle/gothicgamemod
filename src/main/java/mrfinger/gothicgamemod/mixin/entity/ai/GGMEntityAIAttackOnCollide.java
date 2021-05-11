package mrfinger.gothicgamemod.mixin.entity.ai;

import mrfinger.gothicgamemod.entity.IGGMEntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityAIAttackOnCollide.class)
public class GGMEntityAIAttackOnCollide {


    @Shadow private int attackTick;


    @Redirect(method = "updateTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityCreature;attackEntityAsMob(Lnet/minecraft/entity/Entity;)Z"))
    private boolean preAttackEntity(EntityCreature tasker, Entity target)
    {
        ((IGGMEntityCreature) tasker).justAttack(target, 0.0F);
        return true;
    }

    /*@Inject(method = "updateTask", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ai/EntityAIAttackOnCollide;attackTick:I", ordinal = 0))
    private void aaa(CallbackInfo ci)
    {
        System.out.println("Debug in GGMEntityAIAttackOnCollide" + this.attackTick);
    }*/


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
