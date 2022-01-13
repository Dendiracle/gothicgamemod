package mrfinger.gothicgamemod.entity.animation.instance;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public abstract class AbstractAnimationWithDurationManager<Entity extends IGGMEntityLivingBase, Animation extends AbstractAnimationWithDuration<Entity>> extends AbstractAnimationManager<Entity, Animation>
{

    @Override
    protected IAnimationSnapshot<Animation> getNewSnapshot()
    {
        return new AnimationWithDurationSnapshot();
    }


    public class AnimationWithDurationSnapshot extends AnimationSnapshot
    {

        protected short duration;
        protected short countdown;


        public AnimationWithDurationSnapshot() {}

        public AnimationWithDurationSnapshot(AbstractAnimationWithDuration animation)
        {
            this.duration = animation.duration;
            this.countdown = animation.countdown;
        }


        @Override
        public void loadDataForAnimation(Animation animation)
        {
            super.loadDataForAnimation(animation);

            animation.duration = this.duration;
            animation.countdown = this.countdown;
        }

        @Override
        public void loadDataFromAnimation(Animation animation)
        {
            super.loadDataFromAnimation(animation);

            this.duration = animation.duration;
            this.countdown = animation.countdown;
        }


        @Override
        public void fromBytes(ByteBuf buf)
        {
            super.fromBytes(buf);

            this.duration = buf.readShort();
            this.countdown = buf.readShort();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            super.toBytes(buf);

            buf.writeShort(this.duration);
            buf.writeShort(this.countdown);
        }

    }

}
