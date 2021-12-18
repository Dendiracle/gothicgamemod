package mrfinger.gothicgamemod.client.entity.animations.hits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.entity.animation.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketEntitiesToAttack;
import mrfinger.gothicgamemod.network.client.CPacketSetItemInUseInFightAnim;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class AnimationPlayerFightStanceClient extends GGMPlayerEquipmentAnimationFightStance<IGGMAbstractClientPlayer, AbstractPlayerAnimationHit>
{

    public AnimationPlayerFightStanceClient(IGGMAbstractClientPlayer entity)
    {
        super(entity);
    }


    @Override
    protected void updateAttack()
    {
        if (this.countdown == this.culminationTick)
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
    public boolean allowUsingItems()
    {
        PacketDispatcher.sendToServer(new CPacketSetItemInUseInFightAnim(true));
        return super.allowUsingItems();
    }
}
