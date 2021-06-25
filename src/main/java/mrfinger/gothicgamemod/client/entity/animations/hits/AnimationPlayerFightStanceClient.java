package mrfinger.gothicgamemod.client.entity.animations.hits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketEntitiesToAttack;
import mrfinger.gothicgamemod.network.client.CPacketSetItemInUseInFightAnim;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.Collection;
import java.util.List;

@SideOnly(Side.CLIENT)
public class AnimationPlayerFightStanceClient extends GGMPlayerEquipmentAnimationFightStance
{

    public AnimationPlayerFightStanceClient(IGGMEntityPlayer entity)
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
            this.hitType.onCulmination(this.entity, this.attackDuration, this.count, this.attackSeries);
        }
    }

}
