package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.AnimationHelperPlayerFightStanceMP;
import net.minecraft.inventory.Container;

public interface IGGMEntityPlayerMP extends IGGMEntityPlayer {


    void upgradeCapabilities();

    void sendContainerToPlayer(Container container);


    @Override
    AnimationHelperPlayerFightStanceMP getGGMEquipment();

    void setFightAnimationTargets(IGGMEntity[] entities);

}
