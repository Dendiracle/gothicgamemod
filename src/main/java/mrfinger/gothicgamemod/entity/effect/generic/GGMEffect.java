package mrfinger.gothicgamemod.entity.effect.generic;

public class GGMEffect implements IGGMEffect
{

    protected final String unlocalizedName;


    public GGMEffect(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }


    @Override
    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof IGGMEffect && this.unlocalizedName.equals(((IGGMEffect) obj).getUnlocalizedName());
    }

}
