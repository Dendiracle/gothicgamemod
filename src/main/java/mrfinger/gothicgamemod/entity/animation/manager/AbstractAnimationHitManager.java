package mrfinger.gothicgamemod.entity.animation.manager;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationManager;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationWithCulmination;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationWithCulminationManager;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithStamina;

import java.util.UUID;

public abstract class AbstractAnimationHitManager<Entity extends IGGMEntityLivingBase, Animation extends AbstractAnimationWithCulmination<Entity>> extends AbstractAnimationWithCulminationManager<Entity, Animation>
{

    @Override
    public IStartAnimationData getAnimationData(Entity entity, IAnimationPlayer<Entity, Animation> player)
    {
        if (entity instanceof IGGMEntityWithStamina)
        {
            StartAnimationHitData data = new StartAnimationHitData();
            data.staminaSpending = ((IGGMEntityWithStamina) entity).getStaminaSpendingFromAnimatedAttack() * this.staminaSpendingMultiplier();

            return data;
        }

        return super.getAnimationData(entity, player);
    }

    protected abstract float staminaSpendingMultiplier();

    public static class StartAnimationHitData<Entity extends IGGMEntityWithStamina, Animation extends IAnimation<Entity>> implements IStartAnimationData<Entity, Animation>
    {

        protected Entity entity;
        protected float staminaSpending;

        @Override
        public boolean canStartAnimation()
        {
            return entity.getStamina() >= this.staminaSpending;
        }

        @Override
        public void onAnimationStarted(Animation animation)
        {
            this.entity.changeStamina(-this.staminaSpending);
        }

    }

}
