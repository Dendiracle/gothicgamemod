package mrfinger.gothicgamemod.entity.animation.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public abstract class AbstractAnimationHit<EntityAttacker extends IGGMEntityLivingBase, Model extends ModelBase> extends AbstractAnimationEpisode<EntityAttacker, Model> implements IAnimationHit<EntityAttacker, Model>
{

    public AbstractAnimationHit(String unlocalizedName)
    {
        super(unlocalizedName);
    }

    @Override
    public Entity[] getAttackTargets(EntityAttacker entity, IAnimation helper)
    {
        if (entity.getAttackTarget() != null)
        {
            EntityLivingBase entity1 = (EntityLivingBase) entity;

            Vec3 lookVec = entity1.getLook(entity1.width * 0.5F);
            double x = entity1.posX + lookVec.xCoord;
            double y = entity1.posY + lookVec.yCoord;
            double z = entity1.posZ + lookVec.zCoord;

            float f0 = entity.getMeleeAttackDistance() + entity.getAttackTarget().getWidth() * 0.5F;
            f0 *= f0;

            if (entity.getAttackTarget().getDistanceSq(x, y, z) <= f0)
            {
                return new Entity[] {(net.minecraft.entity.Entity) entity.getAttackTarget()};
            }
        }

        return null;
    }


}
