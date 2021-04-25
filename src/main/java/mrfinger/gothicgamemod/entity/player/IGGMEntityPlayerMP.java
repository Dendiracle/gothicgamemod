package mrfinger.gothicgamemod.entity.player;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;

public interface IGGMEntityPlayerMP extends IGGMEntityPlayer {


    void upgradeCapabilities();

    void setEntitiesToAttack(Entity[] entities);


    void sendContainerToPlayer(Container container);

}
