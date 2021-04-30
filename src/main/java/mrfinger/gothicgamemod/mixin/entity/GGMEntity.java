package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class GGMEntity implements IGGMEntity {


    @Shadow private int entityId;

    @Shadow public World worldObj;

    @Shadow public double posX;

    @Shadow public double posY;

    @Shadow public double posZ;

    @Shadow public boolean isDead;

    @Shadow public double motionY;

    @Shadow public double motionZ;

    @Shadow public double motionX;

    @Shadow public abstract void setEating(boolean p_70019_1_);

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }


    @Inject(method = "moveEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateFallState(DZ)V"))
    private void debug(CallbackInfo ci)
    {
        //if (!this.worldObj.isRemote) System.out.println("debug in GGMEntity " + this.motionY);
    }

}
