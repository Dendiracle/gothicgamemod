package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttackAndStamina;
import mrfinger.gothicgamemod.init.GGMBlocks;
import mrfinger.gothicgamemod.tileentity.TileEntityAnimalEgg;

public interface IEntityGothicAnimal extends IEntityHerd, IGGMEntityWithAnimAttackAndStamina, IGGMEntityWithAnimAttack
{

    IAnimationHit getHitAnimation(float distance);


    int getEpisodeDuration(IAnimationEpisode episode);


    int getNewBornGrowthAge();

    int getGrowingAge();

    int getMaxGrowth();

    void setGrowingAge(int value);

    void changeGrowth(int value);


    void playEatSound();


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


    default float getAnnoyersWarnDistance()
    {
        return 8F;
    }

    void playWarnSound();

}
