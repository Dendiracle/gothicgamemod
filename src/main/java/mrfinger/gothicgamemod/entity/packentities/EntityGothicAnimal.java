package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.animations.IAnimationFightStance;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import net.minecraft.world.World;

public class EntityGothicAnimal extends EntityHerd implements IEntityGothicAnimal
{

    protected final IAnimationFightStance animationFightStance;


    public EntityGothicAnimal(World world)
    {
        super(world);

        this.animationFightStance = this.getNewAnimationFightStance();
    }


    protected IAnimationFightStance getNewAnimationFightStance()
    {
        return null;
    }


    @Override
    public short getNewAttackDuration(IAnimationEpisode hitType)
    {
        return 20;
    }

    @Override
    public IAnimationEpisode getLastAttackHitTYpe() {
        return null;
    }

    @Override
    public short getLastAttackDuration() {
        return 0;
    }

    @Override
    public short getAttackCount() {
        return 0;
    }

    @Override
    public short getAttackTick() {
        return 0;
    }

    @Override
    public byte getAttackSeries() {
        return 0;
    }

    @Override
    public void startAttack(IAnimationEpisode hitType) {

    }

    @Override
    public IGGMDynamicAttributeInstance getStaminaAttribute() {
        return null;
    }

    @Override
    public float getStaminaSpendingFromJump() {
        return 0;
    }

    @Override
    public float getStaminaSpendingOnSprint() {
        return 0;
    }

    @Override
    public float getStaminaSpendingFromAttack() {
        return 0;
    }
}
