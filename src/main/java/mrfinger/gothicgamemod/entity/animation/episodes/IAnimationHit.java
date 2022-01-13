package mrfinger.gothicgamemod.entity.animation.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import net.minecraft.client.model.ModelBase;

public interface IAnimationHit<Entity extends IGGMEntityLivingBase, Model extends ModelBase> extends IAnimationEpisode<Entity, Model>
{

    net.minecraft.entity.Entity[] getAttackTargets(Entity entity, IAnimation helper);

}
