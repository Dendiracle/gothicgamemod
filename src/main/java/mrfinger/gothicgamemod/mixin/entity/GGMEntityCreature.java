package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.IGGMEntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityCreature.class)
public abstract class GGMEntityCreature extends GGMEntityLiving implements IGGMEntityCreature {


    @Shadow protected Entity            entityToAttack;

    @Shadow protected abstract void attackEntity(Entity p_70785_1_, float p_70785_2_);

    private int 						cAttDur;

    private int                         attTime;

    private int                         attTick;


    @Inject(method = "attackEntity", at = @At("RETURN"))
    private void fixAttackEntity(Entity entity, float distance, CallbackInfo ci)
    {

    }


    @Override
    public void justAttack(Entity entity, float distance) {
        super.justAttack(entity, distance);

        this.attackEntity(entity, distance);
    }

    @Inject(method = "updateLeashedState", at = @At(value = "TAIL"))
    private void attackUpdate(CallbackInfo ci) {

        if (this.attTime < this.cAttDur) {

            if (this.attTime == this.attTick) {

                if (this.entityToAttack.getDistanceToEntity(thisEntity()) <= this.attackDistance() * 1.2F) this.attackEntityAsMob(this.entityToAttack);
            }

            ++this.attTime;
        }
        else {

            this.attTime = 0;
            this.cAttDur = 0;
            this.attTick = 0;
        }
    }

    @Override
    public boolean isAnimedAttack() {
        return false;
    }

    @Override
    public int currentAttackDuration() {
        return this.cAttDur;
    }

    @Override
    public boolean chargeAttack() {

        if (this.attTime > 0) return false;
        System.out.println("CHARGING ATTACK! AAAAAAAAAAAA");
        this.cAttDur = this.attackDuration();

        this.attTick = this.cAttDur / 2;

        return true;
    }

    @Override
    public boolean isChargingAttack() {
        return this.attTime < this.attTick;
    }

    @Override
    public int getAttackTime() {
        return this.attTime;
    }

    @Override
    public int getAttackTick() {
        return this.attTick;
    }

    public EntityCreature thisEntity() {
        return (EntityCreature) (Object) this;
    }

}
