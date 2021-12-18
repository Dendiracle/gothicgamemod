package mrfinger.gothicgamemod.entity.animation.episodes;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.model.ModelBiped;

public abstract class AbstractPlayerAnimationHit<Player extends IGGMEntityPlayer> extends AbstractAnimationEpisodeWithDurAndMultiplier<Player, ModelBiped> implements IAnimationHit<Player, ModelBiped>
{

    public AbstractPlayerAnimationHit(String unlocalizedName, int standartDuration, float attackTickMultiplier)
    {
        super(unlocalizedName, standartDuration, attackTickMultiplier);
    }

}
