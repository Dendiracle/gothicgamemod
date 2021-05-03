package mrfinger.gothicgamemod.item.equipment;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Map;

public interface IItemRequiring {


    Map<IAttribute, Float> getRequiredsMap();


    IItemRequiring setRequireds(Map<IAttribute, Float> requireds);


    boolean isMayEquip(IGGMEntityLivingBase entity);

}
