package mrfinger.gothicgamemod.entity.effect.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.effect.generic.IGGMEffect;

public abstract class GGMEffectInstance implements IGGMEffectInstance
{

    protected final IGGMEffect genericEffect;


    public GGMEffectInstance(IGGMEffect genericEffect)
    {
        this.genericEffect = genericEffect;
    }


    @Override
    public IGGMEffect getGenericEffect()
    {
        return this.genericEffect;
    }


}
