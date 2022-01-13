package mrfinger.gothicgamemod.entity.capability.data;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.effect.AbstractGGMEffectManager;
import mrfinger.gothicgamemod.entity.effect.IGGMEffectManager;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

/*
* On implementing classes needs correct
* max air on underwater swimming, jump,
* sprint and animated attack.
*/
public interface IGGMEntityWithStamina extends IGGMEntityLivingBase
{

    IGGMEffectManager staminaSpending = new IGGMEffectManager()
    {
        @Override
        public String getUnlocalizedName()
        {
            return "stamina.spending";
        }
    };

    IGGMDynamicAttributeInstance getStaminaAttribute();


    default double getStamina()
    {
        return this.getStaminaAttribute().getDynamicValue();
    }

    default double getMaxStamina()
    {
        return this.getStaminaAttribute().getAttributeValue();
    }

    default void setStamina(double value)
    {
        this.getStaminaAttribute().setDynamicValue(value);
    }

    default void changeStamina(double value)
    {
        this.getStaminaAttribute().changeCurrentValue(value);
    }


    @Override
    default boolean isCanJump()
    {
        double stamina = this.getStaminaSpendingFromJump();

        if (this.getStamina() >= stamina && IGGMEntityLivingBase.super.isCanJump())
        {
            this.applyEffect(new GGMEffectStaminaToSpendInstance(stamina));
            return true;
        }

        return false;
    }

    float getStaminaSpendingFromJump();


    @Override
    default void moveUpdate()
    {
        if (((EntityLivingBase) this).isSprinting() && ((EntityLivingBase) this).moveForward > this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).getBaseValue())
        {
            double spendStamina = this.getStaminaSpendingOnSprint();

            if (this.getStamina() < spendStamina)
            {
                ((EntityLivingBase) this).setSprinting(false);
            }
            else if (!((EntityLivingBase) this).worldObj.isRemote)
            {
                this.changeStamina(-spendStamina);
            }
        }
    }

    float getStaminaSpendingOnSprint();


    /*@Override
    default boolean startAnimatedAttack(IAnimationHitManager manager)
    {
        double needStamina = this.getStaminaSpendingFromAnimatedAttack();
        System.out.println("Debug in IGGMEntityWithStamina startAttack!!!!!!!!!!!!!");
        if (needStamina <= this.getStamina() && IGGMEntityLivingBase.super.startAnimatedAttack(manager))
        {
            this.changeStamina(-needStamina);
            return true;
        }

        return false;
    }*/

    float getStaminaSpendingFromAnimatedAttack();


    class GGMEffectStaminaToSpendManager<Entity extends IGGMEntityWithStamina> extends AbstractGGMEffectManager<Entity>
    {

        public GGMEffectStaminaToSpendManager(int id, String unlocalizedName)
        {
            super(id, unlocalizedName);
        }

        @Override
        public boolean isCanEffectBeApplied(IGGMEntityWithStamina entity)
        {
            return true;
        }

        @Override
        public IGGMEffectInstance getNewEffectInstance(IGGMEntityWithStamina entity)
        {
            return null;
        }

        @Override
        public IGGMEffectSnapshot getEffectSnapshot(IGGMEffectInstance instance)
        {
            return null;
        }

        @Override
        public IGGMEffectSnapshot getSnapshotFromBytes(ByteBuf buf)
        {
            return null;
        }


        class GGMEffectStaminaToSpendInstance<Manager extends GGMEffectStaminaToSpendManager<Entity>> extends AbstractGGMEffectManager.AbstractGGMEffectInstance<Manager, Entity>
        {

            protected final double stamina;


            public GGMEffectStaminaToSpendInstance(IGGMEntityWithStamina entity, double stamina)
            {
                super(entity);

                this.stamina = stamina;
            }


            @Override
            public IGGMEffectManager getGenericEffect()
            {
                return staminaSpending;
            }


            @Override
            public void onSetsToEntity(IGGMEntityLivingBase entity, IGGMEffectInstance oldEffect) {}

            @Override
            public void onEntityUpdate() {}

            @Override
            public void onJumped()
            {
                this.entity.changeStamina(-this.stamina);
            }
        }
    }



}
