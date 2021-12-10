package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.client.model.ModelAnimal;
import mrfinger.gothicgamemod.client.model.ModelPlayer;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelper;
import mrfinger.gothicgamemod.entity.animations.episodes.*;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GGMEntityAnimations
{

    public static final Map<String, IAnimationEpisode> GothicAnimalLivingEpisodesMap = new HashMap<>();


    public static final AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal> AnimationLookingAroundEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_LA0", 40)
    {
        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal modelBase, float progress)
        {
            modelBase.updateAnimationLookingAround(progress);
        }
    };

    public static final AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal> AnimationEatEntityGothicAnimal = new AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal>("GA_Eat0", 100, 0.2F)
    {
        @Override
        public void onCulmination(IEntityGothicAnimal entity, IAnimationHelper helpe)
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

    public static final AbstractAnimationEpisode<IEntityGothicAnimal, ModelAnimal> AnimationSleepingEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_Sleep0", 10000)
    {
        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal model, float progress)
        {
            model.updateAnimationSleeping(progress);
        }

        @Override
        public boolean isCanBreak(IEntityGothicAnimal entity, IAnimationHelper helper)
        {
            int i = helper.getEpisodeDuration() - helper.getEpisodeCountdown();
            helper.clearAnimationEpisode();

            if (i < entity.getEpisodeDuration(GGMEntityAnimations.AnimationWakeUpEntityGothicAnimal))
            {
                entity.getActiveAnimationHelper().setAnimationEpisode(GGMEntityAnimations.AnimationWakeUpEntityGothicAnimal, i);
            }
            else
            {
                entity.getActiveAnimationHelper().setAnimationEpisode(GGMEntityAnimations.AnimationWakeUpEntityGothicAnimal);
            }
            return false;
        }
    };

    public static final AbstractAnimationEpisode<IEntityGothicAnimal, ModelAnimal> AnimationWakeUpEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_WakeUp0", 100)
    {
        @Override
        public void updateModel(IEntityGothicAnimal entity, ModelAnimal model, float progress)
        {
            model.updateAnimationWakeUp(progress);
        }
    };

    public static final AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal> AnimationChildBirthEntityGothicAnimal = new AbstractAnimationEpisodeWithDurAndMultiplier<IEntityGothicAnimal, ModelAnimal>("GA_CB0", 600, 0.4F)
    {
        @Override
        public void onCulmination(IEntityGothicAnimal entity, IAnimationHelper helpe)
        {
            entity.birthChild();
        }

        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal modelBase, float progress)
        {
            modelBase.updateAnimationChildBirth(progress);
        }
    };

    public static final AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal> AnimationAggrEntityGothicAnimal = new AbstractAnimationEpisodeWithDur<IEntityGothicAnimal, ModelAnimal>("GA_Aggr0", 60)
    {
        @Override
        public void onSet(IEntityGothicAnimal entity, IAnimationHelper helper)
        {
            entity.playWarnSound();
        }

        @Override
        public void onCulmination(IEntityGothicAnimal entity, IAnimationHelper helper)
        {
            if (!entity.getEntityWorld().isRemote()) entity.changeGrowth(1);
        }

        @Override
        public void updateModel(IEntityGothicAnimal entityLivingBase, ModelAnimal model, float progress)
        {
            model.updateAnimationAggr(progress);
        }
    };


    public static final AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal> AnimationHitGothicAnimal = new AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal>("GA_Hit0")
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

    public static final AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal> AnimationHitOnRunGothicAnimal = new AbstractAnimationHit<IEntityGothicAnimal, ModelAnimal>("GA_HitOR0")
    {
        @Override
        public int getStandartDuration()
        {
            return 20;
        }


        @Override
        public void controlEntityMotion(IEntityGothicAnimal entity, IAnimationHelper helper)
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
        public Entity[] getAttackTargets(IGGMEntityWithAnimAttack player, IAnimationHelper helper)
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


    public static void preLoad(FMLPreInitializationEvent event)
    {
        loadGothiAnimalLivingAnimations();
    }

    private static void loadGothiAnimalLivingAnimations()
    {
        GothicAnimalLivingEpisodesMap.put(AnimationLookingAroundEntityGothicAnimal.getUnlocalizedName(), AnimationLookingAroundEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationEatEntityGothicAnimal.getUnlocalizedName(), AnimationEatEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationChildBirthEntityGothicAnimal.getUnlocalizedName(), AnimationChildBirthEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationSleepingEntityGothicAnimal.getUnlocalizedName(), AnimationSleepingEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationWakeUpEntityGothicAnimal.getUnlocalizedName(), AnimationWakeUpEntityGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationAggrEntityGothicAnimal.getUnlocalizedName(), AnimationAggrEntityGothicAnimal);

        GothicAnimalLivingEpisodesMap.put(AnimationHitGothicAnimal.getUnlocalizedName(), AnimationHitGothicAnimal);
        GothicAnimalLivingEpisodesMap.put(AnimationHitOnRunGothicAnimal.getUnlocalizedName(), AnimationHitOnRunGothicAnimal);
    }


}
