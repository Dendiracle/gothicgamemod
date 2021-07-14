package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithStamina;
import mrfinger.gothicgamemod.init.GGMBlocks;
import mrfinger.gothicgamemod.tileentity.TileEntityAnimalEgg;

public interface IEntityGothicAnimal extends IEntityHerd, IGGMEntityWithStamina, IGGMEntityWithAttackAnim
{

    IAnimationHit getHitAnimation(float distance);


    int getEpisodeDuration(IAnimationEpisode episode);


    int getNewBornGrowthAge();

    int getGrowingAge();

    int getMaxGrowth();

    void setGrowingAge(int value);

    void changeGrowth(int value);


    IEntityGothicAnimal getNewChild();

    int getChildBirthNeedsGrowth();

    void birthChild();

    boolean isViviparous();

    default BlockAnimalEggs getBlockEgg()
    {
        return GGMBlocks.eggGeneral;
    }

    default void setEggParametrs(TileEntityAnimalEgg egg) {}


    float getStandartWidth();

    float getStandartHeight();

    default float getNewBornSizeScale()
    {
        return 0.3F;
    }

}
