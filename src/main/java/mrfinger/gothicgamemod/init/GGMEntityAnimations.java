package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.client.model.ModelAnimal;
import mrfinger.gothicgamemod.client.model.ModelPlayer;
import mrfinger.gothicgamemod.data.entity.AnimationsData;
import mrfinger.gothicgamemod.entity.animation.instance.AnimationGothicAnimalLiving;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.instance.*;
import mrfinger.gothicgamemod.entity.animation.episodes.*;
import mrfinger.gothicgamemod.entity.animation.manager.AbstractAnimationHitManager;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationManager;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationManager;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GGMEntityAnimations
{

    public static final IAnimationManager ManagerGothicAnimalLiving = new AbstractAnimationManager()
    {
        @Override
        public IAnimation getNewAnimationInstance()
        {
            return new AnimationGothicAnimalLiving();
        }
    };

    public static final IAnimationManager ManagerGothicAnimalFightStance = new AbstractAnimationManager()
    {
        @Override
        public IAnimation getNewAnimationInstance()
        {
            return new AnimationFSWithAIGothicMob();
        }
    };


    public static final Map<String, IAnimationEpisode> GothicAnimalLivingEpisodesMap = new HashMap<>();


    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>> AnimationManagerLookingAroundEntityGothicAnimal = new AbstractAnimationWithDurationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithDuration<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithDuration<IEntityGothicAnimal>((short) 40)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerLookingAroundEntityGothicAnimal;
                }

                @Override
                public boolean isCanAnimationWillChangedFor(IAnimation animation)
                {
                    return true;
                }

                @Override
                public void updateAnimation(IAnimationPlayer animationPlayer)
                {
                    super.updateAnimation(animationPlayer);

                    if (!this.entity.onGround())
                    {
                        animationPlayer.clearAnimation();
                    }
                }

                @Override
                public void onTakingDamage(IGGMDamageSource damageSource, float damage)
                {
                    this.entity.tryEndAnimation();
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationLookingAround(this.getAnimationProgress(tickRate));
                }
            };
        }

    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>> AnimationManagerEatEntityGothicAnimal = new AbstractAnimationWithCulminationManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithCulmination<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithCulmination<IEntityGothicAnimal>((short) 100, (short) 20)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerEatEntityGothicAnimal;
                }

                @Override
                public boolean isCanAnimationWillChangedFor(IAnimation animation)
                {
                    return true;
                }

                @Override
                public void updateAnimation(IAnimationPlayer animationPlayer)
                {
                    super.updateAnimation(animationPlayer);

                    if (!this.entity.onGround())
                    {
                        animationPlayer.clearAnimation();
                    }
                }

                @Override
                protected void onCulmination()
                {
                    if (!this.entity.getEntityWorld().isRemote())
                    {
                        entity.changeGrowth(1);
                    }

                    entity.playEatSound();
                }

                @Override
                public void onTakingDamage(IGGMDamageSource damageSource, float damage)
                {
                    this.entity.tryEndAnimation();
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationEat(this.getAnimationProgress(tickRate));
                }
            };
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>> AnimationManagerFallingAsleepEntityGothicAnimal = new AbstractAnimationWithDurationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithDuration<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithDuration<IEntityGothicAnimal>((short) 80)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerFallingAsleepEntityGothicAnimal;
                }

                @Override
                public boolean isCanAnimationWillChangedFor(IAnimation animation)
                {
                    if (animation.getAnimationManager() == AnimationManagerWakeUpEntityGothicAnimal)
                    {
                        return true;
                    }

                    IAnimation<IEntityGothicAnimal> wakeUp = AnimationManagerWakeUpEntityGothicAnimal.getNewAnimationInstance();
                    wakeUp.setDuration((int) (this.getCountdown() * 0.3F));
                    this.entity.tryChangeAnimation(wakeUp);
                    return false;
                }

                @Override
                public void onTakingDamage(IGGMDamageSource damageSource, float damage)
                {
                    IAnimation<IEntityGothicAnimal> wakeUp = AnimationManagerWakeUpEntityGothicAnimal.getNewAnimationInstance();
                    wakeUp.setDuration((int) (this.getCountdown() * 0.3F));
                    this.entity.tryChangeAnimation(wakeUp);
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationFallingAsleep(this.getAnimationProgress(tickRate));
                }
            };
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>> AnimationManagerSleepingEntityGothicAnimal = new AbstractAnimationWithDurationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithDuration<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithDuration<IEntityGothicAnimal>((short) 10000)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerSleepingEntityGothicAnimal;
                }

                @Override
                public void updateAnimation(IAnimationPlayer animationPlayer)
                {
                    super.updateAnimation(animationPlayer);

                    if (!this.entity.onGround())
                    {
                        animationPlayer.clearAnimation();
                    }
                }

                @Override
                public void onTakingDamage(IGGMDamageSource damageSource, float damage)
                {
                    IAnimation<IEntityGothicAnimal> wakeUp = AnimationManagerWakeUpEntityGothicAnimal.getNewAnimationInstance();
                    wakeUp.resizeDuration(0.3F);
                    this.entity.tryChangeAnimation(wakeUp);
                }

                @Override
                public boolean isCanAnimationWillChangedFor(IAnimation animation)
                {
                    if (animation.getAnimationManager() == AnimationManagerWakeUpEntityGothicAnimal)
                    {
                        return true;
                    }

                    IAnimation<IEntityGothicAnimal> wakeUp = AnimationManagerWakeUpEntityGothicAnimal.getNewAnimationInstance();
                    wakeUp.resizeDuration(0.3F);
                    this.entity.tryChangeAnimation(wakeUp);
                    return false;
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationSleeping(0.5F);
                }
            };
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>> AnimationManagerWakeUpEntityGothicAnimal = new AbstractAnimationWithDurationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithDuration<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithDuration<IEntityGothicAnimal>((short) 100)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerWakeUpEntityGothicAnimal;
                }

                @Override
                public void setCountdown(int countdown)
                {
                    if (countdown < 10)
                    {
                        countdown = 10;
                    }

                    super.setCountdown(countdown);
                }

                @Override
                public void onTakingDamage(IGGMDamageSource damageSource, float damage)
                {
                    if (this.duration > 10)
                    {
                        short newDuration = (short) (this.duration * 0.3F);
                        this.setDuration(newDuration > 10 ? newDuration : 10);
                    }
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationWakeUp(this.getAnimationProgress(tickRate));
                }
            };
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>> AnimationManagerChildBirthEntityGothicAnimal = new AbstractAnimationWithCulminationManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithCulmination<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithCulmination<IEntityGothicAnimal>((short) 600, (short) 240)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerChildBirthEntityGothicAnimal;
                }

                @Override
                public void updateAnimation(IAnimationPlayer animationPlayer)
                {
                    if (!this.entity.onGround())
                    {
                        animationPlayer.clearAnimation();
                    }
                    else
                    {
                        super.updateAnimation(animationPlayer);
                    }
                }

                @Override
                protected void onCulmination()
                {
                    this.entity.birthChild();
                }

                @Override
                public void onTakingDamage(IGGMDamageSource damageSource, float damage)
                {
                    this.entity.tryEndAnimation();
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationChildBirth(this.getAnimationProgress(tickRate));
                }
            };
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>> AnimationManagerAggrEntityGothicAnimal = new AbstractAnimationWithDurationManager<IEntityGothicAnimal, AbstractAnimationWithDuration<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithDuration<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithDuration<IEntityGothicAnimal>((short) 60)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerAggrEntityGothicAnimal;
                }

                @Override
                public void onSet(IEntityGothicAnimal iEntityGothicAnimal, IAnimationPlayer animationPlayer)
                {
                    super.onSet(iEntityGothicAnimal, animationPlayer);
                    iEntityGothicAnimal.playWarnSound();
                }

                @Override
                public void updateAnimation(IAnimationPlayer animationPlayer)
                {
                    super.updateAnimation(animationPlayer);

                    if (!this.entity.onGround())
                    {
                        animationPlayer.clearAnimation();
                    }
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationAggr(this.getAnimationProgress(tickRate));
                }
            };
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>> AnimationManagerHitGothicAnimal = new AbstractAnimationHitManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithCulmination<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithCulmination<IEntityGothicAnimal>((short) 10, (short) 4)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerHitGothicAnimal;
                }

                @Override
                protected void onCulmination()
                {
                    EntityLivingBase target = this.entity.getAttackTarget();

                    float attackDistanceSQ = this.entity.getMeleeAttackDistance();
                    attackDistanceSQ *= attackDistanceSQ;

                    if (target != null && target.isEntityAlive() && attackDistanceSQ >= target.getDistanceSqToEntity((Entity) this.entity))
                    {
                        this.entity.hitEntity(target, null, 1F);
                    }
                }

                @Override
                public void controlEntityMovement()
                {
                    ((EntityLivingBase) this.entity).moveForward = 0F;
                    ((EntityLivingBase) this.entity).moveStrafing = 0F;
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationHit(this.getAnimationProgress(tickRate));
                }
            };
        }

        @Override
        protected float staminaSpendingMultiplier()
        {
            return 1F;
        }
    };

    public static final IAnimationManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>> AnimationManagerHitOnRunGothicAnimal = new AbstractAnimationHitManager<IEntityGothicAnimal, AbstractAnimationWithCulmination<IEntityGothicAnimal>>()
    {
        @Override
        public AbstractAnimationWithCulmination<IEntityGothicAnimal> getNewAnimationInstance()
        {
            return new AbstractAnimationWithCulmination<IEntityGothicAnimal>((short) 20, (short) 7)
            {
                @Override
                public IAnimationManager getAnimationManager()
                {
                    return AnimationManagerHitOnRunGothicAnimal;
                }

                @Override
                protected void onCulmination()
                {
                    EntityLivingBase target = this.entity.getAttackTarget();

                    float attackDistanceSQ = this.entity.getMeleeAttackDistance();
                    attackDistanceSQ *= attackDistanceSQ;

                    if (target != null && target.isEntityAlive() && attackDistanceSQ >= target.getDistanceSqToEntity((Entity) this.entity))
                    {
                        this.entity.hitEntity(target, null, 1.2F);
                    }
                }

                @Override
                public void controlEntityMovement()
                {
                    ((EntityLivingBase) this.entity).moveForward = (float) entity.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                    ((EntityLivingBase) this.entity).moveStrafing = 0F;
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelAnimal) model).updateAnimationHit(this.getAnimationProgress(tickRate));
                }
            };
        }

        @Override
        protected float staminaSpendingMultiplier()
        {
            return 2F;
        }
    };

    public static final IAnimationManager<IGGMEntityPlayer, AbstractAnimationWithCulmination<IGGMEntityPlayer>> hitSplash = new AbstractAnimationWithCulminationManager<IGGMEntityPlayer, AbstractAnimationWithCulmination<IGGMEntityPlayer>>()
    {
        @Override
        public AbstractAnimationWithCulmination<IGGMEntityPlayer> getNewAnimationInstance()
        {
            return new AbstractAnimationWithCulmination<IGGMEntityPlayer>((short) 20, (short) 16)
            {
                ItemStack stack;

                @Override
                public IAnimationManager getAnimationManager()
                {
                    return hitSplash;
                }


                @Override
                public void onSet(IGGMEntityPlayer player, IAnimationPlayer animationPlayer)
                {
                    super.onSet(player, animationPlayer);

                    this.stack = player.getFirstHeldItem();
                }

                @Override
                public void onRemoveAnimation(IGGMEntityPlayer player, IAnimationPlayer animationPlayer)
                {
                    super.onRemoveAnimation(player, animationPlayer);

                    this.stack = null;
                }


                @Override
                protected void onCulmination()
                {
                    EntityLivingBase[] uArray;
                    float[] angles;
                    int size = 0;
                    boolean flag = false;
                    EntityLivingBase[] hArray;

                    {
                        float sl = (float) entity.getPosY() - entity.eyeHeight() - 0.1F;
                        float rad = 4.0F;
                        float s = 0.05F;
                        float pi = (float) Math.PI;
                        float semipi = pi * 0.5F;


                        float rotY = entity.getRotationYaw() * 0.017453292F;
                        if (rotY > pi) {
                            float dpi = pi * 2.0F;
                            while (rotY >= pi) rotY -= dpi;
                        } else if (rotY <= -pi) {
                            float dpi = pi * 2.0F;
                            while (rotY <= -pi) rotY += dpi;
                        }
                        float rotP = entity.getRotationPitch() * 0.017453292F;
                        if (rotP >= semipi - 0.1F) rotP = semipi - 0.1F;
                        else if (rotP <= -semipi + 0.1F) rotP = -semipi + 0.1F;

                        float sinY = MathHelper.sin(rotY);
                        float cosY = MathHelper.cos(rotY);
                        float sinP = MathHelper.sin(rotP);
                        float cosP = MathHelper.cos(rotP);


                        float dx = -s * sinY * sinP;
                        float dy = s * cosP;
                        float dz = s * cosY * sinP;

                        float maxY = sl + dy;
                        float minY = sl - dy;
                        {
                            float f = rad * sinP;
                            if (sinP < 0.0F) maxY -= f;
                            else minY -= f;
                        }

                        float minX = (float) entity.getPosX();
                        float maxX = minX;
                        if (dx < 0) {
                            minX += dx;
                            maxX -= dx;
                        } else {
                            minX -= dx;
                            maxX += dx;
                        }
                        {
                            float f = rad * cosY;
                            if (f < 0) f *= -1.0F;
                            minX -= f;
                            maxX += f;

                            float f1 = (rad - f) * cosP;
                            if (sinY > 0.0F) minX -= f1;
                            else maxX += f1;
                        }

                        float minZ = (float) entity.getPosZ();
                        float maxZ = minZ;
                        if (dz < 0) {
                            minZ += dz;
                            maxZ -= dz;
                        } else {
                            minZ -= dz;
                            maxZ += dz;
                        }
                        {
                            float f = rad * sinY;
                            if (f < 0) f *= -1.0F;
                            minZ -= f;
                            maxZ += f;
                            float f1 = (rad - f) * cosP;
                            if (cosY < 0.0F) minZ -= f1;
                            else maxZ += f1;
                        }

                        {

                            List<EntityLivingBase> eArray = entity.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
                            int e = eArray.size();
                            uArray = new EntityLivingBase[e];
                            angles = new float[e];


                            for (EntityLivingBase entity : eArray) {

                                if (entity == entity || (entity.ridingEntity == entity)) continue;

                                float mal = flag ? -9999.0F : 9999.0F;

                                boolean fits = false;

                                {
                                    float xz[] = new float[8];

                                    {
                                        float d;
                                        d = (float) entity.posX + dx;
                                        xz[0] = (float) entity.boundingBox.minX - d;
                                        xz[1] = (float) entity.boundingBox.maxX - d;
                                        d = (float) entity.posZ + dz;
                                        xz[2] = (float) entity.boundingBox.minZ - d;
                                        xz[3] = (float) entity.boundingBox.maxZ - d;
                                        d = (float) entity.posX - dx;
                                        xz[4] = (float) entity.boundingBox.minX - d;
                                        xz[5] = (float) entity.boundingBox.maxX - d;
                                        d = (float) entity.posZ - dz;
                                        xz[6] = (float) entity.boundingBox.minZ - d;
                                        xz[7] = (float) entity.boundingBox.maxZ - d;
                                    }

                                    for (int i = 0; i < 2; ++i) {

                                        for (int j = 2; j < 4; ++j) {

                                            float yt, yb, d, d1, al, al1, a, a1;
                                            al = rotY;
                                            al1 = rotY;

                                            d = MathHelper.sqrt_float(xz[i] * xz[i] + xz[j] * xz[j]);
                                            if (xz[i] <= 0.0F) al += (float) Math.asin(xz[j] / d);
                                            else {
                                                if (xz[j] >= 0.0F) al += semipi + (float) Math.asin(xz[i] / d);
                                                else al -= semipi + (float) Math.asin(xz[i] / d);
                                            }

                                            if (flag) {
                                                if (al > mal) mal = al;
                                            } else if (al < mal) mal = al;

                                            if (!fits) {

                                                i += 4;
                                                j += 4;
                                                d1 = MathHelper.sqrt_float(xz[i] * xz[i] + xz[j] * xz[j]);
                                                if (xz[i] <= 0.0F) al1 += (float) Math.asin(xz[j] / d);
                                                else {
                                                    if (xz[j] >= 0.0F) al1 += semipi + (float) Math.asin(xz[i] / d);
                                                    else al1 -= semipi + (float) Math.asin(xz[i] / d);
                                                }
                                                i -= 4;
                                                j -= 4;

                                                a = d * MathHelper.sin(al);
                                                yt = (a * -sinP) / cosP;
                                                a1 = d1 * MathHelper.sin(al1);
                                                yb = (a1 * -sinP) / cosP;

                                                if (sl + dy + yt >= entity.boundingBox.minY && sl - dy + yb <= entity.boundingBox.maxY
                                                        && (a >= 0.0F || a1 >= 0.0F)
                                                        && (MathHelper.sqrt_float(d * d + yt * yt) <= rad || MathHelper.sqrt_float(d1 * d1 + yb * yb) <= rad)) {
                                                    fits = true;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (!fits) {

                                    boolean x = maxX <= entity.boundingBox.maxX && minX >= entity.boundingBox.minX;
                                    boolean y = maxY <= entity.boundingBox.maxY && minY >= entity.boundingBox.minY;
                                    boolean z = maxZ <= entity.boundingBox.maxZ && minZ >= entity.boundingBox.minZ;

                                    if ((x && y) || (x && z) || (y && z)) fits = true;
                                }


                                if (fits) {
                                    uArray[size] = entity;
                                    angles[size] = mal;
                                    ++size;
                                }
                            }
                        }

                        hArray = new EntityLivingBase[size];

                        for (int i = 0; i < size; ++i) {
                            if (uArray[i] != null) {

                                float minan;
                                int index = 0;

                                if (flag) {

                                    minan = -9999998.0F;

                                    for (int ii = 0; ii < size; ++ii) {

                                        if (minan < angles[ii]) {
                                            index = ii;
                                            minan = angles[ii];
                                        }
                                    }
                                    angles[index] = -9999999.0F;
                                    hArray[i] = uArray[index];
                                } else {

                                    minan = 9999998.0F;

                                    for (int ii = 0; ii < size; ++ii) {

                                        if (minan > angles[ii]) {
                                            index = ii;
                                            minan = angles[ii];
                                        }
                                    }
                                    angles[index] = 9999999.0F;
                                    hArray[i] = uArray[index];
                                }
                            }
                        }
                    }

                    float multiplier = 1F;

                    for(EntityLivingBase entity : hArray)
                    {
                        this.entity.hitEntity(entity, this.stack, multiplier);
                        multiplier *= 0.5F;

                        if (multiplier < 0.1F)
                        {
                            break;
                        }
                    }
                }

                @Override
                public void animateModel(ModelBase model, float f0, float f1, float tickRate)
                {
                    ((ModelPlayer) model).updateAnimationHitSplash((IGGMEntityPlayer) entity, this.getAnimationProgress(tickRate));
                }
            };
        }
    };


    /*
    public static final AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal> AnimationManagerLookingAroundEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_LA0", 40)
    {
        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal modelBase, float progress)
        {
            modelBase.updateAnimationLookingAround(progress);
        }
    };

    public static final AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal> AnimationManagerEatEntityGothicAnimal = new AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal>("GA_Eat0", 100, 0.2F)
    {
        @Override
        public void onCulmination(IEntityGothicAnimal entity, IAnimation helpe)
        {
            if (!entity.getEntityWorld().isRemote()) entity.changeGrowth(1);

            entity.playEatSound();
        }

        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal model, float progress)
        {
            model.updateAnimationEat(progress);
        }
    };

    public static final AbstractAnimationEpisode<IEntityGothicAnimal, ModelAnimal> AnimationManagerSleepingEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_Sleep0", 10000)
    {
        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal model, float progress)
        {
            model.updateAnimationSleeping(progress);
        }

        @Override
        public boolean isCanBreak(IEntityGothicAnimal entity, IAnimation helper)
        {
            int i = helper.getAnimationDuration() - helper.getAnimationCountdown();
            helper.clearAnimationEpisode();

            if (i < entity.getEpisodeDuration(GGMEntityAnimations.AnimationManagerWakeUpEntityGothicAnimal))
            {
                entity.getActiveAnimation().setAnimationEpisode(GGMEntityAnimations.AnimationManagerWakeUpEntityGothicAnimal, i);
            }
            else
            {
                entity.getActiveAnimation().setAnimationEpisode(GGMEntityAnimations.AnimationManagerWakeUpEntityGothicAnimal);
            }
            return false;
        }
    };

    public static final AbstractAnimationEpisode<IEntityGothicAnimal, ModelAnimal> AnimationManagerWakeUpEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_WakeUp0", 100)
    {
        @Override
        public void updateModel(IEntityGothicAnimal entity, ModelAnimal model, float progress)
        {
            model.updateAnimationWakeUp(progress);
        }
    };

    public static final AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal> AnimationManagerChildBirthEntityGothicAnimal = new AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal>("GA_CB0", 600, 0.4F)
    {
        @Override
        public void onCulmination(IEntityGothicAnimal entity, IAnimation helpe)
        {
            entity.birthChild();
        }

        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal modelBase, float progress)
        {
            modelBase.updateAnimationChildBirth(progress);
        }
    };

    public static final AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal> AnimationManagerAggrEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_Aggr0", 60)
    {
        @Override
        public void onSet(IEntityGothicAnimal entity, IAnimation helper)
        {
            entity.playWarnSound();
        }

        @Override
        public void onCulmination(IEntityGothicAnimal entity, IAnimation helper)
        {
            if (!entity.getEntityWorld().isRemote()) entity.changeGrowth(1);
        }

        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal model, float progress)
        {
            model.updateAnimationAggr(progress);
        }
    };


    public static final AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal> AnimationManagerHitGothicAnimal = new AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal>("GA_Hit0")
    {

        @Override
        public int getStandartDuration()
        {
            return 10;
        }

        @Override
        public float getCulminationTickMultiplier()
        {
            return 0.4F;
        }

        @Override
        public void updateModel(IEntityGothicAnimal entity, ModelAnimal modelAnimal, float progress)
        {
            modelAnimal.updateAnimationHit(progress);
        }
    };

    public static final AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal> AnimationManagerHitOnRunGothicAnimal = new AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal>("GA_HitOR0")
    {
        @Override
        public int getStandartDuration()
        {
            return 20;
        }


        @Override
        public void controlEntityMotion(IEntityGothicAnimal entity, IAnimation helper)
        {
            ((EntityLivingBase) entity).moveForward = (float) entity.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            ((EntityLivingBase) entity).moveStrafing = 0F;
        }

        @Override
        public float getCulminationTickMultiplier()
        {
            return 0.35F;
        }

        @Override
        public void updateModel(IEntityGothicAnimal entity, ModelAnimal modelAnimal, float progress)
        {
            modelAnimal.updateAnimationHitOnRun(progress);
        }
    };


    public static final AbstractPlayerAnimationHit hitSplash = new AbstractPlayerAnimationHit("Hit_Player_Splash", 20, 0.8F)
    {

        @Override
        public Entity[] getAttackTargets(IGGMEntityWithAnimAttack player, IAnimation helper)
        {
            EntityLivingBase[] uArray;
            float[] angles;
            int size = 0;
            boolean flag = false;
            EntityLivingBase[] hArray;

            {
                float sl = (float) player.getPosY() - player.getEyeHeight() - 0.1F;
                float rad = 4.0F;
                float s = 0.05F;
                float pi = (float) Math.PI;
                float semipi = pi * 0.5F;


                float rotY = player.getRotationYaw() * 0.017453292F;
                if (rotY > pi) {
                    float dpi = pi * 2.0F;
                    while (rotY >= pi) rotY -= dpi;
                } else if (rotY <= -pi) {
                    float dpi = pi * 2.0F;
                    while (rotY <= -pi) rotY += dpi;
                }
                float rotP = player.getRotationPitch() * 0.017453292F;
                if (rotP >= semipi - 0.1F) rotP = semipi - 0.1F;
                else if (rotP <= -semipi + 0.1F) rotP = -semipi + 0.1F;

                float sinY = MathHelper.sin(rotY);
                float cosY = MathHelper.cos(rotY);
                float sinP = MathHelper.sin(rotP);
                float cosP = MathHelper.cos(rotP);


                float dx = -s * sinY * sinP;
                float dy = s * cosP;
                float dz = s * cosY * sinP;

                float maxY = sl + dy;
                float minY = sl - dy;
                {
                    float f = rad * sinP;
                    if (sinP < 0.0F) maxY -= f;
                    else minY -= f;
                }

                float minX = (float) player.getPosX();
                float maxX = minX;
                if (dx < 0) {
                    minX += dx;
                    maxX -= dx;
                } else {
                    minX -= dx;
                    maxX += dx;
                }
                {
                    float f = rad * cosY;
                    if (f < 0) f *= -1.0F;
                    minX -= f;
                    maxX += f;

                    float f1 = (rad - f) * cosP;
                    if (sinY > 0.0F) minX -= f1;
                    else maxX += f1;
                }

                float minZ = (float) player.getPosZ();
                float maxZ = minZ;
                if (dz < 0) {
                    minZ += dz;
                    maxZ -= dz;
                } else {
                    minZ -= dz;
                    maxZ += dz;
                }
                {
                    float f = rad * sinY;
                    if (f < 0) f *= -1.0F;
                    minZ -= f;
                    maxZ += f;
                    float f1 = (rad - f) * cosP;
                    if (cosY < 0.0F) minZ -= f1;
                    else maxZ += f1;
                }

                {

                    List<EntityLivingBase> eArray = player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
                    int e = eArray.size();
                    uArray = new EntityLivingBase[e];
                    angles = new float[e];

                    boolean isRiding = player.isRiding();

                    for (EntityLivingBase entity : eArray) {

                        if (entity == player || (player.getRidingEntity() == entity)) continue;

                        float mal = flag ? -9999.0F : 9999.0F;

                        boolean fits = false;

                        {
                            float xz[] = new float[8];

                            {
                                float d;
                                d = (float) player.getPosX() + dx;
                                xz[0] = (float) entity.boundingBox.minX - d;
                                xz[1] = (float) entity.boundingBox.maxX - d;
                                d = (float) player.getPosZ() + dz;
                                xz[2] = (float) entity.boundingBox.minZ - d;
                                xz[3] = (float) entity.boundingBox.maxZ - d;
                                d = (float) player.getPosX() - dx;
                                xz[4] = (float) entity.boundingBox.minX - d;
                                xz[5] = (float) entity.boundingBox.maxX - d;
                                d = (float) player.getPosZ() - dz;
                                xz[6] = (float) entity.boundingBox.minZ - d;
                                xz[7] = (float) entity.boundingBox.maxZ - d;
                            }

                            for (int i = 0; i < 2; ++i) {

                                for (int j = 2; j < 4; ++j) {

                                    float yt, yb, d, d1, al, al1, a, a1;
                                    al = rotY;
                                    al1 = rotY;

                                    d = MathHelper.sqrt_float(xz[i] * xz[i] + xz[j] * xz[j]);
                                    if (xz[i] <= 0.0F) al += (float) Math.asin(xz[j] / d);
                                    else {
                                        if (xz[j] >= 0.0F) al += semipi + (float) Math.asin(xz[i] / d);
                                        else al -= semipi + (float) Math.asin(xz[i] / d);
                                    }

                                    if (flag) {
                                        if (al > mal) mal = al;
                                    } else if (al < mal) mal = al;

                                    if (!fits) {

                                        i += 4;
                                        j += 4;
                                        d1 = MathHelper.sqrt_float(xz[i] * xz[i] + xz[j] * xz[j]);
                                        if (xz[i] <= 0.0F) al1 += (float) Math.asin(xz[j] / d);
                                        else {
                                            if (xz[j] >= 0.0F) al1 += semipi + (float) Math.asin(xz[i] / d);
                                            else al1 -= semipi + (float) Math.asin(xz[i] / d);
                                        }
                                        i -= 4;
                                        j -= 4;

                                        a = d * MathHelper.sin(al);
                                        yt = (a * -sinP) / cosP;
                                        a1 = d1 * MathHelper.sin(al1);
                                        yb = (a1 * -sinP) / cosP;

                                        if (sl + dy + yt >= entity.boundingBox.minY && sl - dy + yb <= entity.boundingBox.maxY
                                                && (a >= 0.0F || a1 >= 0.0F)
                                                && (MathHelper.sqrt_float(d * d + yt * yt) <= rad || MathHelper.sqrt_float(d1 * d1 + yb * yb) <= rad)) {
                                            fits = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (!fits) {

                            boolean x = maxX <= entity.boundingBox.maxX && minX >= entity.boundingBox.minX;
                            boolean y = maxY <= entity.boundingBox.maxY && minY >= entity.boundingBox.minY;
                            boolean z = maxZ <= entity.boundingBox.maxZ && minZ >= entity.boundingBox.minZ;

                            if ((x && y) || (x && z) || (y && z)) fits = true;
                        }


                        if (fits) {
                            uArray[size] = entity;
                            angles[size] = mal;
                            ++size;
                        }
                    }
                }

                hArray = new EntityLivingBase[size];

                for (int i = 0; i < size; ++i) {
                    if (uArray[i] != null) {

                        float minan;
                        int index = 0;

                        if (flag) {

                            minan = -9999998.0F;

                            for (int ii = 0; ii < size; ++ii) {

                                if (minan < angles[ii]) {
                                    index = ii;
                                    minan = angles[ii];
                                }
                            }
                            angles[index] = -9999999.0F;
                            hArray[i] = uArray[index];
                        } else {

                            minan = 9999998.0F;

                            for (int ii = 0; ii < size; ++ii) {

                                if (minan > angles[ii]) {
                                    index = ii;
                                    minan = angles[ii];
                                }
                            }
                            angles[index] = 9999999.0F;
                            hArray[i] = uArray[index];
                        }
                    }
                }
            }

            return hArray;
        }

        @Override
        public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
        {
            ((ModelPlayer) model).updateAnimationHitSplash((IGGMEntityPlayer) entity, this, progress);
        }
    };
     */


    public static void preLoad(FMLPreInitializationEvent event)
    {
        registerAnimationManagers();
    }

    private static void registerAnimationManagers()
    {
        AnimationsData.registerAnimationManager(ManagerGothicAnimalLiving);
        AnimationsData.registerAnimationManager(ManagerGothicAnimalFightStance);
        AnimationsData.registerAnimationManager(AnimationManagerEatEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerLookingAroundEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerFallingAsleepEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerSleepingEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerWakeUpEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerChildBirthEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerAggrEntityGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerHitGothicAnimal);
        AnimationsData.registerAnimationManager(AnimationManagerHitOnRunGothicAnimal);
       /* AnimationsData.registerAnimationManager();
        AnimationsData.registerAnimationManager();
        AnimationsData.registerAnimationManager();
        AnimationsData.registerAnimationManager();*/


        /*GothicAnimalLivingEpisodesMap.put(AnimationManagerLookingAroundEntityGothicAnimal.getUnlocalizedName(), AnimationManagerLookingAroundEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationManagerEatEntityGothicAnimal.getUnlocalizedName(), AnimationManagerEatEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationManagerChildBirthEntityGothicAnimal.getUnlocalizedName(), AnimationManagerChildBirthEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationManagerSleepingEntityGothicAnimal.getUnlocalizedName(), AnimationManagerSleepingEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationManagerWakeUpEntityGothicAnimal.getUnlocalizedName(), AnimationManagerWakeUpEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationManagerAggrEntityGothicAnimal.getUnlocalizedName(), AnimationManagerAggrEntityGothicAnimal);

        GothicAnimalLivingEpisodesMap.put(AnimationManagerHitGothicAnimal.getUnlocalizedName(), AnimationManagerHitGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationManagerHitOnRunGothicAnimal.getUnlocalizedName(), AnimationManagerHitOnRunGothicAnimal);*/
    }


}
