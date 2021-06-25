package mrfinger.gothicgamemod.entity.animations.episodes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketEntitiesToAttack;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public abstract class AbstractPlayerAnimationHit extends AbstractAnimationHit
{

    public AbstractPlayerAnimationHit(String unlocalizedName, int standartDuration, float attackTickMultiplier)
    {
        super(unlocalizedName, standartDuration, attackTickMultiplier);
    }


    @Override
    public void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte attackSeries)
    {
        Entity[] targets = this.entitiesToAttack((IGGMEntityPlayer) entity, attackSeries);

        if (targets != null)
        {
            PacketDispatcher.sendToServer(new CPacketEntitiesToAttack(targets));

            IGGMEntityPlayer player = (IGGMEntityPlayer) entity;

            for (Entity target : targets)
            {
                player.attackTargetEntityWithCurrentItem(target);
            }
        }

    }


    @SideOnly(Side.CLIENT)
    protected abstract Entity[] entitiesToAttack(IGGMEntityPlayer attacker, byte attackSeries);

}
