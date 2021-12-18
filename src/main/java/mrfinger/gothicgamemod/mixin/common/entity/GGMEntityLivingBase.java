package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.effect.generic.IGGMEffect;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;
import mrfinger.gothicgamemod.entity.properties.IEntityProperties;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.init.GGMBattleSystem;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMEntities;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(EntityLivingBase.class)
public abstract class GGMEntityLivingBase extends GGMEntity implements IGGMEntityLivingBase
{

    protected Fraction fraction;

    protected int lvl;
    private boolean needExpUpdate;

    protected IAnimation activeAnimationHelper;

    private boolean needAnimSync;

    @Shadow
    protected BaseAttributeMap attributeMap;

    protected Map<IGGMEffect, IGGMEffectInstance> effectsMap;
    protected Map<IGGMEffect, IGGMEffectInstance> attackEffectsMap;
    protected Map<IGGMEffect, IGGMEffectInstance> otherEffectsMap;

    @Shadow
    protected float lastDamage;

    @Shadow
    public float moveForward;
    @Shadow
    public float moveStrafing;
    @Shadow
    protected float landMovementFactor;

    @Shadow
    public abstract float getEyeHeight();

    @Shadow
    public abstract float getRotationYawHead();

    @Shadow
    protected abstract void damageArmor(float p_70675_1_);

    @Inject(method = "entityInit", at = @At("HEAD"))
    protected void onEntityOriginalInit(CallbackInfo callbackInfo)
    {
        this.fraction = this.getStandartFraction();

        this.effectsMap = new HashMap<>();
        this.otherEffectsMap = new HashMap<>();
        this.attackEffectsMap = new HashMap<>();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setHealth(F)V"))
    private void deleteHealthing(EntityLivingBase entity, float value)
    {
        this.restoreCurrentValuesFull();
    }

    @Inject(method = "<init>*", at = @At(value = "RETURN"))
    private void inInit(CallbackInfo ci)
    {
        this.lvl = this.initialLevel();
        this.flagForLvlUpdate();
    }


    @Override
    public Fraction getCurrentFraction()
    {
        return this.fraction;
    }

    @Override
    public Fraction getStandartFraction()
    {
        return GGMFractions.neutralFraction;
    }


    @ModifyArg(method = "applyEntityAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;registerAttribute(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;", ordinal = 0))
    private IAttribute redirectMaxHealthAttributePutting(IAttribute attribute)
    {
        return this.getGenericMaxHealthAttribute();
    }

    protected IAttribute getGenericMaxHealthAttribute()
    {
        return SharedMonsterAttributes.maxHealth;
    }


    @Override
    public void restoreCurrentValuesFull()
    {
        this.setHealth(this.getMaxHealth());
    }


	/*@Inject(method = "getAttributeMap", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/ai/attributes/ServersideAttributeMap;<init>()V"))
	private void onCreateNewMap(CallbackInfo callbackInfo)
	{
		((IGGMBaseAttributeMap) this.attributeMap).setEntity(this);
		System.out.println(((IGGMBaseAttributeMap) this.attributeMap).getEntity());
	}*/

    @Shadow
    public abstract BaseAttributeMap getAttributeMap();

    @Inject(method = "getAttributeMap", at = @At(value = "RETURN"))
    private void onCreateNewMap(CallbackInfoReturnable callbackInfo)
    {
        if (((IGGMBaseAttributeMap) this.attributeMap).getEntity() == null)
        {
            ((IGGMBaseAttributeMap) this.attributeMap).setEntity(this);
        }
    }

    @Shadow
    public abstract IAttributeInstance getEntityAttribute(IAttribute p_110148_1_);

    @Override
    public IGGMAttributeInstance getEntityAttributeInstance(IAttribute attribute)
    {
        return (IGGMAttributeInstance) this.getAttributeMap().getAttributeInstance(attribute);
    }

    @Override
    public IGGMAttributeInstance getEntityAttributeInstance(String name)
    {
        return (IGGMAttributeInstance) this.getAttributeMap().getAttributeInstanceByName(name);
    }


    @Override
    public int initialLevel()
    {
        double l = 0.0F;
        Collection<IAttributeInstance> attributes = this.getAttributeMap().getAllAttributes();

        for (IAttributeInstance attributeInstance : attributes)
        {
            Float o = GGMCapabilities.expGainFromAttributesMap.get(attributeInstance.getAttribute());
            if (o != null && o > 0.0F) l += attributeInstance.getBaseValue() * o;
        }

        return (int) Math.round(l);
    }

    @Override
    public int getLvl()
    {
        return this.lvl;
    }

    @Override
    public void setLvl(int lvl)
    {
        if (lvl >= 0)
        {
            if (lvl != this.lvl)
            {
                this.flagForLvlUpdate();
                this.lvl = lvl;
            }
        } else
        {
            throw new IllegalArgumentException("Level of entity cannot be less than 0");
        }
    }

    @Override
    public int getGainingExperience()
    {
        return this.getLvl() * GGMEntities.EXPFromLvlModifier;
    }


    @Override
    public void setEntityProperties(IEntityProperties properties)
    {
        properties.setProperties(this);
    }


    @Inject(method = "onEntityUpdate", at = @At("HEAD"))
    private void onOnEntityUpdate(CallbackInfo ci)
    {
        if (this.isEntityAlive())
        {
            this.controlAnimationHelperUpdate();
        }
        else
        {
            this.clearAnimation();
        }

        for (IGGMEffectInstance effect : this.effectsMap.values())
        {
            effect.onEntityUpdate(this);
        }
    }

    protected void controlAnimationHelperUpdate()
    {
        if (this.activeAnimationHelper != null)
        {
            this.activeAnimationHelper.updateAnimation(this);
        }
    }


    @ModifyArg(method = "onEntityUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setAir(I)V", ordinal = 2))
    private int modifyMaxAir(int air)
    {
        return this.getMaxAir();
    }

    @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;moveStrafing:F", ordinal = 1))
    private void controlMove(CallbackInfo ci)
    {
        this.activeAnimationHelper.controlEntityMovement();
        this.moveUpdate();
    }


    @Override
    public IAnimation getActiveAnimation()
    {
        return this.activeAnimationHelper;
    }

    @Override
    public boolean tryChangeAnimation(IAnimation animation)
    {
        if (this.activeAnimationHelper == null || this.activeAnimationHelper.isCanAnimationHelperWillChanged())
        {
            this.setActiveAnimationDirectly(animation);
            return true;
        }

        return false;
    }

    @Override
    public void setActiveAnimationDirectly(IAnimation animation)
    {
        if (animation != null )
        {
            animation.setEntity(this);

            if (this.activeAnimationHelper != null)
            {
                IAnimation oldAnimation = this.activeAnimationHelper;
                this.activeAnimationHelper = animation;
                oldAnimation.onRemoveAnimation(this);
            }
            else
            {
                this.activeAnimationHelper = animation;
            }
        }
        else
        {
            if (this.activeAnimationHelper != null)
            {
                IAnimation oldAnimation = this.activeAnimationHelper;
                this.activeAnimationHelper = null;
                oldAnimation.onRemoveAnimation(this);
            }
        }

        this.flagForAnimSync();
    }

    @Override
    public void clearAnimation()
    {
        if (this.activeAnimationHelper != null)
        {
            this.setActiveAnimationDirectly(null);
        }
    }


    @Override
    public Map<String, IAnimationEpisode> getAnimationEpisodesMap()
    {
        return null;
    }

    @Override
    public void flagForAnimSync()
    {
        this.needAnimSync = true;
    }

    @Override
    public boolean checkToNeedSyncAnimation()
    {
        if (this.needAnimSync)
        {
            this.needAnimSync = false;
            return true;
        }

        return false;
    }


    @Override
    public void flagForLvlUpdate()
    {
        this.needExpUpdate = true;
    }

    @Override
    public boolean checkNeedExpUpdate()
    {
        if (this.needExpUpdate)
        {
            this.needExpUpdate = false;
            return true;
        }
        return false;
    }


    @Override
    public int getMaxAir()
    {
        return 300;
    }


    @Override
    public IGGMEffectInstance getEffect(IGGMEffect effect)
    {
        return this.effectsMap.get(effect);
    }

    @Override
    public IGGMEffectInstance applyEffect(IGGMEffectInstance effectInstance)
    {
        IGGMEffectInstance effectInstanceOld = this.effectsMap.put(effectInstance.getGenericEffect(), effectInstance);
        effectInstance.onSetsToEntity(this, effectInstanceOld);
        return effectInstanceOld;
    }

    @Override
    public IGGMEffectInstance removeEffect(IGGMEffect effect)
    {
        return this.effectsMap.remove(effect);
    }

    @Override
    public Map<IGGMEffect, IGGMEffectInstance> getEffectsMap()
    {
        return Collections.unmodifiableMap(this.effectsMap);
    }
/*
	@Override
	public Map<IGGMEffect, IGGMEffectInstance> getBattleEffectsMap()
	{
		return attackEffectsMap;
	}

	@Override
	public Map<IGGMEffect, IGGMEffectInstance> getOtherEffectsMap()
	{
		return otherEffectsMap;
	}
*/

    @Override
    public IGGMAttributeInstance getHealthAttribute()
    {
        return (IGGMAttributeInstance) this.attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth);
    }

    @Shadow public abstract float getHealth();

    @Shadow public abstract float getMaxHealth();

    @Shadow public abstract void setHealth(float p_70606_1_);

    @Override
    public void increaseAttribute(IAttribute attribute, float value)
    {
        if (value < 0F)
        {
            throw new IllegalArgumentException("Increasing value cannot be less than 0");
        }

        IGGMAttributeInstance ai = (IGGMAttributeInstance) this.getEntityAttribute(attribute);

        if (ai != null) ai.changeBaseValue(value);
    }


    @Override
    public boolean isEntityLookingFor(Entity target, float deltaLookYaw, float deltaLookPitch)
    {
        return this.isEntityLookingFor(target.posX, target.posY, target.posZ, deltaLookYaw, deltaLookPitch);
    }

    @Override
    public boolean isEntityLookingFor(double posX, double posY, double posZ, float deltaLookYaw, float deltaLookPitch)
    {
        posX = posX - this.getPosX();
        posZ = posZ - this.getPosZ();
        float f = MathHelper.wrapAngleTo180_float(((float) (Math.atan2(posZ, posX)) * 57.2957795131F) - 90.0F - this.getRotationYawHead());
        float f1 = MathHelper.wrapAngleTo180_float(-((float) Math.atan2(posY - this.getPosY() + this.getEyeHeight(), MathHelper.sqrt_double(posX * posX + posZ * posZ)) * 57.2957795131F) - this.getRotationPitch());

        return (f == 0F || f > 0F && f < deltaLookYaw || f < 0F && f > -deltaLookYaw) && (f1 == 0F || f1 > 0F && f1 < deltaLookPitch || f1 < 0F && f1 > -deltaLookPitch);
    }

    @Override
    public boolean isCanDropItems()
    {
        return this.activeAnimationHelper.allowDropItems();
    }

    @Override
    public boolean isCanDigging()
    {
        if (!this.activeAnimationHelper.allowDigging()) return false;

        for (IGGMEffectInstance effect : this.otherEffectsMap.values())
        {
            if (!effect.allowDigging()) return false;
        }

        return true;
    }

    @Override
    public boolean isCanUsingItems()
    {
        return this.activeAnimationHelper.allowUsingItems();
    }


    @Redirect(method = "moveEntityWithHeading", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;motionX:D", ordinal = 1))
    private double fixMotionXInWater(EntityLivingBase entity)
    {
        return entity.motionX *= this.waterMovementModifier();
    }

    @Redirect(method = "moveEntityWithHeading", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;motionY:D", ordinal = 1))
    private double fixMotionYInWater(EntityLivingBase entity)
    {
        return entity.motionY *= this.waterMovementModifier();
    }

    @Redirect(method = "moveEntityWithHeading", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;motionZ:D", ordinal = 1))
    private double fixMotionZInWater(EntityLivingBase entity)
    {
        return entity.motionZ *= this.waterMovementModifier();
    }


    @Override
    public float getStandartRotationSpeed()
    {
        return 30F;
    }


    @Override
    public boolean canJump()
    {
        return this.activeAnimationHelper.allowJump();
    }

    @Inject(method = "jump", at = @At(value = "HEAD"), cancellable = true)
    private void onJumpHead(CallbackInfo ci)
    {
        if (!this.activeAnimationHelper.allowJump()) ci.cancel();
    }

    @Redirect(method = "jump", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;motionY:D", ordinal = 0))
    private void jumpFix(EntityLivingBase entity, double motionY)
    {
        double origin = this.getJumpHeight();
        double newMotion = origin;

        for (IGGMEffectInstance effect : this.otherEffectsMap.values())
        {
            newMotion = effect.onJump(origin, newMotion);
        }

        entity.motionY = newMotion;
    }

    @Override
    public double getJumpHeight()
    {
        return 0.41999998688697815D;
    }

    @Inject(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeHooks;onLivingJump(Lnet/minecraft/entity/EntityLivingBase;)V", remap = false))
    private void onJumped(CallbackInfo ci)
    {
        this.onJump();
    }


    @Override
    public boolean isCanSprint()
    {
        return true;
    }

    @ModifyVariable(method = "setSprinting", at = @At(value = "HEAD", ordinal = 0))
    private boolean modifySprinting(boolean b)
    {
        return b && this.isCanSprint();
    }


    @Override
    public float waterMovementModifier()
    {
        return 0.800000011920929F;
    }


    @Override
    public boolean isEntitySleeping()
    {
        return false;
    }

    @Override
    public void wakeUpEntity()
    {
    }


    @Inject(method = "applyArmorCalculations", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;damageArmor(F)V"), cancellable = true)
    private void onApplyArmorCalculations(DamageSource ds, float damage, CallbackInfoReturnable<Float> cir)
    {
        IGGMDamageSource gds = (IGGMDamageSource) ds;

        if (gds.isSettedValues())
        {

            damage = 0.0F;
            int a = this.getTotalArmorValue();
            Map<DamageType, Float> map = gds.getDamageValuesMap();

            for (Map.Entry<DamageType, Float> e : map.entrySet())
            {

                float f = e.getValue() - a;
                if (f > 0.0F) damage += a;
            }

            this.damageArmor(damage);

            cir.setReturnValue(damage);
        }
    }

    @Inject(method = "damageEntity", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/EntityLivingBase;applyPotionDamageCalculations(Lnet/minecraft/util/DamageSource;F)F"))
    private void onDamageCalculated(DamageSource damageSource, float damage, CallbackInfo ci)
    {
        //this.getActiveAnimation().onTakingDamage((IGGMDamageSource) damageSource, damage);
    }

    @Inject(method = "attackEntityFrom", at = @At(value = "JUMP", ordinal = 1), cancellable = true)
    private void cancelCancel(DamageSource ds, float damage, CallbackInfoReturnable<Boolean> ci)
    {
        boolean cancel = false;

        for (IGGMEffectInstance effect : this.attackEffectsMap.values())
        {
            if (effect.onAttackEntityFrom((IGGMDamageSource) ds, damage)) cancel = true;
        }

        if (cancel) ci.setReturnValue(true);

        if (((IGGMDamageSource) ds).isSettedValues())
        {
            this.lastDamage = 0.0F;
        }
    }

    @Inject(method = "onDeath", at = @At(value = "JUMP", ordinal = 1))
    private void onEntityDeath(DamageSource damageSource, CallbackInfo ci)
    {
        for (IGGMEffectInstance effect : this.attackEffectsMap.values())
        {
            effect.onDeath((IGGMDamageSource) damageSource);
        }
    }


    @Override
    public DamageType getStandartMeleeDamageType()
    {
        return GGMBattleSystem.crushing;
    }

    @Shadow public abstract boolean attackEntityAsMob(Entity p_70652_1_);

    @Override
    public boolean meleeAttack(Entity entity, float distanceSQ)
    {
        float attackRange = this.getWidth() * 0.5F + this.getMeleeAttackDistance() + entity.width * 0.5F;
        attackRange *= attackRange;

        if (distanceSQ <= attackRange && this.attackEntityAsMob(entity))
        {
            this.onAnimatedMeleeAttack();
            return true;
        }

        return false;
    }

    @Override
    public short attackBreak()
    {
        return 20;
    }

    @Override
    public float getMeleeAttackDistance()
    {
        return 1F;
    }


    @Override
    public void onKilledEntityBase(EntityLivingBase entity)
    {
        for (IGGMEffectInstance effect : this.attackEffectsMap.values())
        {
            effect.onKillEntity((IGGMEntityLivingBase) entity);
        }
    }


    @Inject(method = "isMovementBlocked", at = @At("HEAD"), cancellable = true)
    private void fixIsMovementBlocked(CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(this.activeAnimationHelper.blockMovement() || this.getHealth() <= 0.0F || this.isEntitySleeping());
    }

    @Inject(method = "knockBack", at = @At("HEAD"), cancellable = true)
    private void onKnockback(Entity knockbacker, float power, double x, double y, CallbackInfo ci)
    {

    }


    @ModifyVariable(method = "fall", at = @At(value = "HEAD"), ordinal = 0)
    private float fixFallDamage(float distance)
    {
        return this.correctFallDistance(distance);
    }

    protected float correctFallDistance(float distance)
    {
        return distance;
    }


    @Override
    public boolean isCanMount(Entity entity)
    {
        if (this.activeAnimationHelper != null && !this.activeAnimationHelper.allowMount((IGGMEntity) entity))
        {
            return false;
        }

        for (IGGMEffectInstance effect : this.otherEffectsMap.values())
        {
            if (!effect.onMountEntity((IGGMEntity) entity)) return false;
        }

        return true;
    }


    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void onWriteEntityToNBT(NBTTagCompound nbt, CallbackInfo ci)
    {
        this.saveExp(nbt);
    }

    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void onReadEntityToNBT(NBTTagCompound nbt, CallbackInfo ci)
    {
        this.loadExp(nbt);
    }


    @Override
    public void saveExp(NBTTagCompound nbt)
    {
        nbt.setInteger("LVL", this.lvl);
    }

    @Override
    public void loadExp(NBTTagCompound nbt)
    {
        this.setLvl(nbt.getInteger("LVL"));
    }

}
