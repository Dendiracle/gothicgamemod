package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.AnimationPlayerFightStanceMP;
import net.minecraft.inventory.Container;

public interface IGGMEntityPlayerMP extends IGGMEntityPlayer {


    void upgradeCapabilities();

    void sendContainerToPlayer(Container container);


    @Override
    AnimationPlayerFightStanceMP getGGMEquipment();

    void setFightAnimationTargets(IGGMEntity[] entities);

}
