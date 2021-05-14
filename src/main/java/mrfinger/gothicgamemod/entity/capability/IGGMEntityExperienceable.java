package mrfinger.gothicgamemod.entity.capability;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMIncreasableAttributeInstance;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.HashMap;
import java.util.Map;

public interface IGGMEntityExperienceable extends IGGMEntityLivingBase {



    IGGMExp getExpCap();


    default void inreaseAttributeForLP(IAttribute attribute, int amounts) {

        IAttributeInstance ai = this.getEntityAttribute(attribute);

        if (ai != null && ai instanceof IGGMIncreasableAttributeInstance) {

            IGGMIncreasableAttributeInstance gai = (IGGMIncreasableAttributeInstance) ai;

            if (this.getExpCap().getLP() >= amounts) {
                this.getExpCap().changeLP(-gai.increaseAttribute(amounts));
            }
        }
    }

    default void increaseAttributesByName(Map<String, Integer> mapByNames) {

        Map<IGGMIncreasableAttributeInstance, Integer> map = new HashMap<>(mapByNames.size(), 1.0F);

        int needLP = 0;

        for (Map.Entry<String, Integer> e : mapByNames.entrySet()) {

            IAttributeInstance ai = this.getAttributeMap().getAttributeInstanceByName(e.getKey());

            if (ai != null && ai instanceof IGGMIncreasableAttributeInstance) {

                map.put((IGGMIncreasableAttributeInstance) ai, e.getValue());

                needLP += e.getValue();
            }
            else {
                return;
            }
        }

        if (this.getExpCap().getLP() < needLP) {
            return;
        }

        needLP = 0;

        for (Map.Entry<IGGMIncreasableAttributeInstance, Integer> e : map.entrySet()) {

            needLP += e.getKey().increaseAttribute(e.getValue());
        }

        this.getExpCap().changeLP(-needLP);
    }


    default void gainExp(int value)
    {
        this.getExpCap().gainExp(value);
    }

}
