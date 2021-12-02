package mrfinger.gothicgamemod.mixin.common.entity.ai;

import mrfinger.gothicgamemod.entity.IGGMEntityCreature;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.pathfinding.PathPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAIAttackOnCollide.class)
public class GGMEntityAIAttackOnCollide
{




    /*@Redirect(method = "updateTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityCreature;attackEntityAsMob(Lnet/minecraft/entity/Entity;)Z"))
    private boolean preAttackEntity(EntityCreature tasker, Entity target)
    {
        ((IGGMEntityCreature) tasker).meleeAttack(target, 0.0F);
        return true;
    }*/


    @Shadow int attackTick;
    @Shadow EntityCreature attacker;
    @Shadow private int field_75445_i;
    @Shadow boolean longMemory;
    @Shadow private double field_151497_i;
    @Shadow private double field_151495_j;
    @Shadow private double field_151496_k;
    @Shadow private int failedPathFindingPenalty;
    @Shadow double speedTowardsTarget;


    @Inject(method = "updateTask", at = @At("HEAD"), cancellable = true)
    private void onUpdateTask(CallbackInfo ci)
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
        --this.field_75445_i;

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || entitylivingbase.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
        {
            this.field_151497_i = entitylivingbase.posX;
            this.field_151495_j = entitylivingbase.boundingBox.minY;
            this.field_151496_k = entitylivingbase.posZ;
            this.field_75445_i = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

            if (this.attacker.getNavigator().getPath() != null)
            {
                PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
                {
                    failedPathFindingPenalty = 0;
                }
                else
                {
                    failedPathFindingPenalty += 10;
                }
            }
            else
            {
                failedPathFindingPenalty += 10;
            }

            if (d0 > 1024.0D)
            {
                this.field_75445_i += 10;
            }
            else if (d0 > 256.0D)
            {
                this.field_75445_i += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
            {
                this.field_75445_i += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);

        double d1 = (double)(this.attacker.width * 0.5F + entitylivingbase.width * 0.5F + ((IGGMEntityCreature) this.attacker).getMeleeAttackDistance());
        d1 *= d1;

        if (d0 <= d1 && this.attackTick <= 0)
        {
            this.attackTick = ((IGGMEntityCreature) this.attacker).attackBreak();

            if (this.attacker.getHeldItem() != null)
            {
                this.attacker.swingItem();
            }

            this.attacker.attackEntityAsMob(entitylivingbase);
        }
        ci.cancel();
    }




}
