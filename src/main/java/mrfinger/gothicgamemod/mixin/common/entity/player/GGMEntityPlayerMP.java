package mrfinger.gothicgamemod.mixin.common.entity.player;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animation.AnimationPlayerFightStanceMP;
import mrfinger.gothicgamemod.entity.inventory.GGMContainerPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.ItemInWorldManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public abstract class GGMEntityPlayerMP extends GGMEntityPlayer implements IGGMEntityPlayerMP {


    @Shadow @Final public ItemInWorldManager theItemInWorldManager;


    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci)
    {
        this.equpmentAndFightAnim = new AnimationPlayerFightStanceMP(this);
        this.ggmContainerEquipment = new GGMContainerPlayer(this);
    }


    @Override
    public void upgradeCapabilities()
    {

    }


    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;detectAndSendChanges()V"))
    private void syncGGMContainer(CallbackInfo ci)
    {

    }


    @Override
    public AnimationPlayerFightStanceMP getGGMEquipment()
    {
        return (AnimationPlayerFightStanceMP) super.getGGMEquipment();
    }

    @Override
    public void setFightAnimationTargets(IGGMEntity[] entities)
    {
        this.getGGMEquipment().setTargets(entities);
    }

/*
    @Override
    public void updateAttack() {

        if (this.attackTicksLeft > -20) {

            if (this.entitiesToAttack != null && this.attackTicksLeft <= this.getAttackSeries()) {

                int a = this.entitiesToAttack.length;
                if (a > 2) a = 2;

                for (int i = 0; i < a; ++i) {

                    if (this.entitiesToAttack[i] != null) {

                        boolean flag = true;

                        for (int ii = 0; ii < i; ++ii) {
                            if (this.entitiesToAttack[i] == this.entitiesToAttack[ii]) flag = false;
                        }

                        if (flag) this.attackTargetEntityWithCurrentItem(this.entitiesToAttack[i]);
                    }
                }

                this.entitiesToAttack = null;
            }

            if (this.repeatStartAttack && this.attackTicksLeft <= 0) {

                this.startAttack();
                this.repeatStartAttack = false;
            }
        }

        super.updateAttack();
    }*/


    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void sendContainer(NBTTagCompound compound, CallbackInfo ci)
    {

    }


    @Override
    public boolean inCreative() {
        return this.theItemInWorldManager.isCreative();
    }

    @Override
    public EntityPlayerMP thisEntity()
    {
        return (EntityPlayerMP) (Object) this;
    }
}
