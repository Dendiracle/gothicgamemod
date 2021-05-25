package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.item.ItemStack;

public class AnimationEntityLiving extends AbstractAnimation {


    public AnimationEntityLiving()
    {
        super();
    }

    public AnimationEntityLiving(IGGMEntityLivingBase entityLivingBase)
    {
        super(entityLivingBase);
    }


    @Override
    public void onUpdate() {

    }


    @Override
    public IAnimation onSetNewAnimation(IAnimation animation) {
        return animation;
    }

    @Override
    public boolean tryEndAnimation()
    {
        return true;
    }

    @Override
    public void onEndAnimation()
    {
    }


    @Override
    public boolean denyDropItems()
    {
        return false;
    }

    @Override
    public boolean denyDigging()
    {
        return false;
    }

    @Override
    public boolean denyUsingItems()
    {
        return false;
    }

    @Override
    public boolean denySetItemInUse(ItemStack itemStack, int duration)
    {
        return false;
    }

    @Override
    public boolean denyChangeItem()
    {
        return false;
    }

    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return false;
    }

    @Override
    public boolean denyJump()
    {
        return false;
    }
}
