package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.Map;

public class AnimationHelperFSWithAIGothicMob<Animal extends IEntityGothicAnimal, Episode extends IAnimationHit> extends EntityAIBase implements IAnimationHelperFightStance<Animal, Episode>
{

    protected IEntityGothicAnimal entity;

    protected IAnimationEpisode hitType;
    protected short attackDuration;

    protected short count;
    protected short attackTick;

    protected byte attackSeries;

    protected byte isNeedControls;
    protected float moveForward;
    protected float moveStrafe;
    protected float rotationYaw;
    protected float rotationPitch;


    public AnimationHelperFSWithAIGothicMob(IEntityGothicAnimal entity)
    {
        this.entity = entity;
        this.attackDuration = 1;
        this.count = -20;
        this.attackTick = -20;
    }


    @Override
    public Animal getEntity() {
        return (Animal) this.entity;
    }

    @Override
    public void setEntity(Animal entity) {
        this.entity = (IEntityGothicAnimal) entity;
    }


    @Override
    public String getUnlocalizedName()
    {
        return "fight";
    }


    @Override
    public byte getAttackSeries()
    {
        return attackSeries;
    }

    @Override
    public boolean denyMovement()
    {
        return false;
    }

    @Override
    public boolean denyChangeItem()
    {
        return this.count >= 0 || this.isUsingItem();
    }

    @Override
    public boolean denyJump()
    {
        return this.count >= 0;
    }

    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return this.count >= 0 || this.isUsingItem();
    }


    @Override
    public void updateAnimation()
    {
        if (this.count > -20)
        {
            this.hitType.onUpdate(this.entity, this.attackDuration, this.count);

            if (this.count == this.attackTick)
            {
                this.hitType.onCulmination(this.entity, this.attackDuration, this.count, this.attackSeries);
            }

            --this.count;
        }
        else
        {
            this.attackSeries = 0;
        }
    }


    @Override
    public Episode getEpisode() {
        return (Episode) this.hitType;
    }

    @Override
    public int getEpisodeDuration()
    {
        return this.attackDuration;
    }

    @Override
    public int getEpisodeCount()
    {
        return this.count;
    }


    @Override
    public boolean setAnimationEpisode(Episode animationEpisode)
    {
        return this.setAnimationEpisode(animationEpisode, this.entity.getNewAttackDuration(animationEpisode));
    }

    @Override
    public boolean setAnimationEpisode(Episode animationEpisode, int count)
    {
        if (animationEpisode != null)
        {
            if (this.allowHit())
            {
                this.hitType = animationEpisode;
                this.attackDuration = (short) count;
                this.count = (short) count;
                this.attackTick = (short) (count * animationEpisode.getCulminationTickMultiplier());
                ++this.attackSeries;
                this.entity.flagForAnimSync();
                return true;
            }

            return false;
        }

        this.clearAnimationEpisode();
        return false;
    }

    @Override
    public boolean setAnimationEpisode(String episodeName, int duration)
    {
        if (episodeName == null)
        {
            this.setAnimationEpisode((Episode) null, 0);
            return true;
        }
        else
        {
            Map<String, IAnimationEpisode> map = this.entity.getAnimationEpisodesMap();

            if (map != null)
            {
                Episode episode = (Episode) map.get(episodeName);
                this.setAnimationEpisode(episode, duration);

                return episode != this.getEpisode();
            }
        }

        return false;
    }

    protected boolean allowHit()
    {
        return this.count <= 0 && !this.isUsingItem();
    }

    @Override
    public void clearAnimationEpisode()
    {
        this.count = -20;
        this.attackTick = -20;
        this.attackSeries = 0;
    }


    @Override
    public void setMoveControl(float forward, float strafe)
    {
        this.isNeedControls |= 0b1;
        this.moveForward = forward;
        this.moveStrafe = strafe;
    }

    @Override
    public void setRotationControl(float yaw, float pitch)
    {
        this.isNeedControls |= 0b10;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
    }

    @Override
    public void controlMove()
    {
        if ((this.isNeedControls & 0b1) == 0b1)
        {
            ((EntityLivingBase) this.entity).moveForward = this.moveForward;
            this.entity.setAIMoveSpeed(this.moveForward);
            ((EntityLivingBase) this.entity).moveStrafing = this.moveStrafe;
            this.isNeedControls &= 0b11111110;
        }
    }

    @Override
    public void controlRotation()
    {
        if ((this.isNeedControls & 0b10) == 0b10)
        {
            ((EntityLivingBase) this.entity).rotationYaw = this.rotationYaw;
            ((EntityLivingBase) this.entity).rotationPitch = this.rotationPitch;
            this.isNeedControls &= 0b11111101;
        }
    }


    @Override
    public boolean isUsingItem() {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }


    @Override
    public boolean tryEndAnimation()
    {
        return this.count < 0;
    }


    @Override
    public void onEndAnimation()
    {
        this.clearAnimationEpisode();
    }


    @Override
    public void modifyModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.count > -20)
        {
            short count = this.count > 0 ? this.count : 0;
            this.hitType.updateModel(this.entity, model, ((this.attackDuration - count) + tickRate) / this.attackDuration);
        }
    }

    @Override
    public String toString()
    {
        return "AnimationHelperFightStance:" + this.getUnlocalizedName();
    }


    @Override
    public boolean shouldExecute()
    {
        return this.entity.getEntityToAttack() != null && this.entity.getEntityToAttack().isEntityAlive() && (this.entity.getCurrentAnimation() == this || this.entity.tryEndAnimation(this));
    }

    @Override
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToEntityLiving((Entity) this.entity.getEntityToAttack(), 1.0D);
    }

    @Override
    public void resetTask()
    {
        this.clearAnimationEpisode();
        this.entity.getNavigator().clearPathEntity();
        this.entity.tryEndAnimation();
    }

    @Override
    public void updateTask()
    {
        IGGMEntity gtarget = this.entity.getEntityToAttack();
        Entity target = (Entity) gtarget;
        this.entity.getLookHelper().setLookPositionWithEntity(target, this.entity.getStandartRotationSpeed(), this.entity.getVerticalFaceSpeed());
        double d0 = this.entity.getDistanceSqToEntity(target);
        double d1 = this.entity.getMeleeAttackDistance() + (((Entity) this.entity).width + target.width) * 0.5F;
        d1 *= d1 * 3.0F;

        if (d0 <= d1)
        {
            this.entity.getNavigator().clearPathEntity();
            if (this.entity.getAttackCount() < 0)
            {
                this.entity.startAttack(this.entity.getHitAnimation((float) d0));
            }
        }
        else
        {
            this.entity.getNavigator().tryMoveToEntityLiving((Entity) this.entity.getEntityToAttack(), 1.0D);
        }
    }

}
