package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithStamina;
import mrfinger.gothicgamemod.init.GGMBlocks;
import mrfinger.gothicgamemod.tileentity.TileEntityAnimalEgg;

public interface IEntityGothicAnimal extends IEntityHerd, IGGMEntityWithStamina
{

    @Override
    default boolean isCanJump()
    {
        return IGGMEntityWithStamina.super.isCanJump();
    }

    @Override
    default float getStaminaSpendingFromJump()
    {
        return this.getWidth() * this.getHeight() * 2F;
    }

    @Override
    default void moveUpdate()
    {
        IGGMEntityWithStamina.super.moveUpdate();
    }

    @Override
    default float getStaminaSpendingOnSprint()
    {
        return this.getWidth() * this.getHeight() * 0.15F;
    }


    @Override
    default float getStaminaSpendingFromAnimatedAttack() {
        return this.getWidth() * this.getHeight() * 3.3F;
    }



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
