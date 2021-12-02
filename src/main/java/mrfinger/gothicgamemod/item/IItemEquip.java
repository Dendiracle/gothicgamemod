package mrfinger.gothicgamemod.item;

import mrfinger.gothicgamemod.item.equipment.IItemRequiring;

public interface IItemEquip extends IITem, IItemRequiring {


    float getWeight();

    float getSustainability();


    void setWeight(float value);

    void setSustainability(float value);

}
