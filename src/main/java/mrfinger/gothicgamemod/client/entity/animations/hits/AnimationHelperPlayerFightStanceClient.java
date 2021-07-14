package mrfinger.gothicgamemod.client.entity.animations.hits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationHelperFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketSetItemInUseInFightAnim;

@SideOnly(Side.CLIENT)
public class AnimationHelperPlayerFightStanceClient extends GGMPlayerEquipmentAnimationHelperFightStance<IGGMAbstractClientPlayer, AbstractPlayerAnimationHit>
{

    public AnimationHelperPlayerFightStanceClient(IGGMEntityPlayer entity)
    {
        super(entity);
    }


    @Override
    public boolean denyUsingItems()
    {
        PacketDispatcher.sendToServer(new CPacketSetItemInUseInFightAnim(true));
        return super.denyUsingItems();
    }

    @Override
    protected void updateAttack()
    {
        if (this.count == this.attackTick)
        {
            this.hitEpisode.onCulmination(this.entity, this.attackDuration, this.count, this.attackSeries);
        }
    }

}
