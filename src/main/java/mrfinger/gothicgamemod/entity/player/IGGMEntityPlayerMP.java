package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;

public interface IGGMEntityPlayerMP extends IGGMEntityPlayer {


    void upgradeCapabilities();

    void sendContainerToPlayer(Container container);


    void setFightAnimationTargets(IGGMEntity[] entities);

}
