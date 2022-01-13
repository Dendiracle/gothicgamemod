package mrfinger.gothicgamemod.entity.effect.instance;

import mrfinger.gothicgamemod.entity.effect.IGGMEffectManager;

public abstract class GGMEffectInstance implements IGGMEffectInstance
{

    protected final IGGMEffectManager genericEffect;


    public GGMEffectInstance(IGGMEffectManager genericEffect)
    {
        this.genericEffect = genericEffect;
    }


    @Override
    public IGGMEffectManager getGenericEffect()
    {
        return this.genericEffect;
    }


}
