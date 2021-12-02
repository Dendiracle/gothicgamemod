package mrfinger.gothicgamemod.entity.animations.episodes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketEntitiesToAttack;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public abstract class AbstractPlayerAnimationHit<Player extends IGGMEntityPlayer> extends AbstractAnimationEpisodeWithDurAndMultiplier<Player, ModelBiped> implements IAnimationHit<Player, ModelBiped>
{

    public AbstractPlayerAnimationHit(String unlocalizedName, int standartDuration, float attackTickMultiplier)
    {
        super(unlocalizedName, standartDuration, attackTickMultiplier);
    }

}
