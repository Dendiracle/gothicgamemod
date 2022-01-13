package mrfinger.gothicgamemod.entity.animation.instance;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public abstract class AbstractAnimationWithCulminationManager<Entity extends IGGMEntityLivingBase, Animation extends AbstractAnimationWithCulmination<Entity>> extends AbstractAnimationWithDurationManager<Entity, Animation>
{

    @Override
    protected IAnimationSnapshot<Animation> getNewSnapshot()
    {
        return new AnimationWithCulminationSnapshot();
    }


    public class AnimationWithCulminationSnapshot extends AnimationWithDurationSnapshot
    {

        protected short culminationTick;


        public AnimationWithCulminationSnapshot() {}

        public AnimationWithCulminationSnapshot(Animation animation)
        {
            super(animation);

            this.culminationTick = animation.culminationTick;
        }


        @Override
        public void loadDataForAnimation(Animation animation)
        {
            super.loadDataForAnimation(animation);

            animation.culminationTick = this.culminationTick;
        }

        @Override
        public void loadDataFromAnimation(Animation animation)
        {
            super.loadDataFromAnimation(animation);

            this.culminationTick = animation.culminationTick;
        }


        @Override
        public void fromBytes(ByteBuf buf)
        {
            super.fromBytes(buf);

            this.culminationTick = buf.readShort();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            super.toBytes(buf);

            buf.writeShort(this.culminationTick);
        }

    }

}
