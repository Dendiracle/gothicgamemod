package mrfinger.gothicgamemod.entity.animations.episodes;

public abstract class AbstractAnimationEpisode implements IAnimationEpisode
{

    protected final String unlocalizedName;
    protected final int standartDuration;

    protected AbstractAnimationEpisode(String unlocalizedName, int standartDuration)
    {
        this.unlocalizedName = unlocalizedName;
        this.standartDuration = standartDuration;
    }


    @Override
    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }

    @Override
    public int getStandartDuration()
    {
        return standartDuration;
    }

    @Override
    public float getCulminationTickMultiplier()
    {
        return 0.0F;
    }


    @Override
    public String toString()
    {
        return "AnimationEpisode " + this.unlocalizedName;
    }
}
