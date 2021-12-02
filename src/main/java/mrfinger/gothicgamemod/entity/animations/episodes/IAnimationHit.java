package mrfinger.gothicgamemod.entity.animations.episodes;

import mrfinger.gothicgamemod.entity.animations.IAnimationHelper;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;
import net.minecraft.client.model.ModelBase;

public interface IAnimationHit<Entity extends IGGMEntityWithAnimAttack, Model extends ModelBase> extends IAnimationEpisode<Entity, Model>
{

    net.minecraft.entity.Entity[] getAttackTargets(Entity entity, IAnimationHelper helper);

}
