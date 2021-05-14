package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public class AnimantionMeleeFight extends AbstractAnimation {



    protected short count;


    public AnimantionMeleeFight(IGGMEntityLivingBase entity, short count)
    {
        super(entity);
        this.count = count;
    }


    @Override
    public void onUpdate() {

    }

    @Override
    public boolean canEndAnimation() {
        return false;
    }

    @Override
    public void onEndAnimation() {

    }
}
