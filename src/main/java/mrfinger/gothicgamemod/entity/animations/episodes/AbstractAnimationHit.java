package mrfinger.gothicgamemod.entity.animations.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;
import net.minecraft.client.model.ModelBase;

public abstract class AbstractAnimationHit<Entity extends IGGMEntityWithAttackAnim, Model extends ModelBase> extends AbstractAnimationEpisode<Entity, Model> implements IAnimationHit<Entity, Model>
{

    public AbstractAnimationHit(String unlocalizedName)
    {
        super(unlocalizedName);
    }


    @Override
    public void onCulmination(Entity entity, int duration, int count, byte series)
    {
        IGGMEntity target = entity.getEntityToAttack();

        if (entity.isClientWorld() && target != null && target.isEntityAlive() && entity.getDistanceSqToEntity((net.minecraft.entity.Entity) target) < entity.getAttackRangeSquare())
        {
            entity.attackEntityAsMob((net.minecraft.entity.Entity) entity.getEntityToAttack());
        }
    }

}
