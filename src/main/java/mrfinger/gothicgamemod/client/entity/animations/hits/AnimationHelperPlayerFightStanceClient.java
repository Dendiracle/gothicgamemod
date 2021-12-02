package mrfinger.gothicgamemod.client.entity.animations.hits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationHelperFightStance;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketEntitiesToAttack;
import mrfinger.gothicgamemod.network.client.CPacketSetItemInUseInFightAnim;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class AnimationHelperPlayerFightStanceClient extends GGMPlayerEquipmentAnimationHelperFightStance<IGGMAbstractClientPlayer, AbstractPlayerAnimationHit>
{

    public AnimationHelperPlayerFightStanceClient(IGGMAbstractClientPlayer entity)
    {
        super(entity);
    }


    @Override
    protected void updateAttack()
    {
        if (this.episodeCountdown == this.episodeCulminationTick)
        {
            this.animationEpisode.onCulmination(this.entity, this);

            Entity[] targets = this.animationEpisode.getAttackTargets(entity, this);

            if (targets != null)
            {
                PacketDispatcher.sendToServer(new CPacketEntitiesToAttack(targets));
            }
        }
    }


    @Override
    public boolean breakAnimationEpisode() {
        return false;
    }


    @Override
    public boolean allowUsingItems()
    {
        PacketDispatcher.sendToServer(new CPacketSetItemInUseInFightAnim(true));
        return super.allowUsingItems();
    }
}
