
package mrfinger.gothicgamemod.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelScavenger extends ModelAnimal
{

    GGMModelRenderer corpus;
    GGMModelRenderer neck0;
    GGMModelRenderer neck1;
    GGMModelRenderer head;
    GGMModelRenderer neb;
    GGMModelRenderer jaw;
    GGMModelRenderer hip0Right;
    GGMModelRenderer hip1Right;
    GGMModelRenderer shinRight;
    GGMModelRenderer foot0LateralRight;
    GGMModelRenderer foot0InnerRight;
    GGMModelRenderer foot1LateralRight;
    GGMModelRenderer foot1InnerRight;
    GGMModelRenderer hip0Left;
    GGMModelRenderer hip1Left;
    GGMModelRenderer shinLeft;
    GGMModelRenderer foot0LateralLeft;
    GGMModelRenderer foot0InnerLeft;
    GGMModelRenderer foot1LateralLeft;
    GGMModelRenderer foot1InnerLeft;
    GGMModelRenderer tail0;
    GGMModelRenderer tail1;


    //GGMModelRenderer test;



    public ModelScavenger()
    {
        textureWidth = 64;
        textureHeight = 32;

        /*this.test = new GGMModelRenderer(this, 10, 0);
        this.test.addQuad(10, 0, 6, 6, -3F, -3F, 0F, 3F, -3F, 0, 3F, 3F, 3F);
        this.test.setRotationPoint(0F, -2F, 0F);
        this.test.setTextureSize(6, 6);
        this.test.mirror = true;*/

        this.corpus = new GGMModelRenderer(this, "corpus");
        this.corpus.setTextureOffset(0, 0);
        this.corpus.addBox(-3F, -3.5F, -8F, 6, 6, 10);
        this.corpus.setRotationPoint(0F, 9.5F, 1F);
        this.corpus.setTextureSize(64, 32);
        this.corpus.defaultRotateAngleX = -0.1745329F;

        this.neck0 = new GGMModelRenderer(this, "neck_proximal");
        this.neck0.setTextureOffset(0, 16);
        this.neck0.addBox(-1.5F, -1F, -4.6F, 3, 3, 5);
        this.neck0.setRotationPoint(0F, 8.8F, -7F);
        this.neck0.setTextureSize(64, 32);

        this.neck1 = new GGMModelRenderer(this, "neck_distal");
        this.neck1.setTextureOffset(0, 24);
        this.neck1.addBox(-1.501F, -4F, -1.8F, 3, 5, 3);
        this.neck1.setRotationPoint(0F, 9.8F, -10F);
        this.neck1.setTextureSize(64, 32);
        this.neck1.defaultRotateAngleX = 0.1396263F;

        this.head = new GGMModelRenderer(this, "head");
        this.head.setTextureOffset(16, 16);
        this.head.addBox(-2F, -4F, -6.5F, 4, 4, 7);
        this.head.setRotationPoint(0F, 5.8F, -9.5F);
        this.head.setTextureSize(64, 32);
        this.head.defaultRotateAngleX = 0.1396263F;

        this.neb = new GGMModelRenderer(this, "neb");
        this.neb.setTextureOffset(35, 26);
        this.neb.addBox(-1.5F, -5.4F, -7.2F, 3, 3, 3);
        this.neb.setRotationPoint(0F, 5.8F, -9.5F);
        this.neb.setTextureSize(64, 32);
        this.neb.defaultRotateAngleX = 0.5201081F;

        this.jaw = new GGMModelRenderer(this, "jaw");
        this.jaw.setTextureOffset(12, 27);
        this.jaw.addBox(-1.5F, 0.5F, -4F, 3, 1, 4);
        this.jaw.setRotationPoint(0F, 5.6F, -12F);
        this.jaw.setTextureSize(64, 32);
        this.jaw.defaultRotateAngleX = 0.1396263F;

        this.head.addChild(this.neb);
        this.head.addChild(this.jaw);
        this.neck1.addChild(this.head);
        this.neck0.addChild(this.neck1);
        this.corpus.addChild(this.neck0);

        hip0Right = new GGMModelRenderer(this, "hip_proximal_right");
        this.hip0Right.setTextureOffset(32, 0);
        hip0Right.addBox(-2.2F, -1F, -1.5F, 3, 4, 3);
        hip0Right.setRotationPoint(-3F, 9.5F, 1F);
        hip0Right.setTextureSize(64, 32);
        this.hip0Right.defaultRotateAngleZ = 0.1396263F;

        hip1Right = new GGMModelRenderer(this, "hip_distal_right");
        this.hip1Right.setTextureOffset(32, 7);
        hip1Right.addBox(-1F, -1.5F, -0.8F, 2, 2, 5);
        hip1Right.setRotationPoint(-4.1F, 12F, 0.5F);
        hip1Right.setTextureSize(64, 32);
        hip1Right.defaultRotateAngleX = -0.3490659F;

        shinRight = new GGMModelRenderer(this, "shin_right");
        this.shinRight.setTextureOffset(32, 14);
        shinRight.addBox(-1.02F, -0.8F, -0.5F, 2, 7, 2);
        shinRight.setRotationPoint(-4.1F, 12.5F, 4F);
        shinRight.setTextureSize(64, 32);
        shinRight.defaultRotateAngleX = -0.4363323F;
        shinRight.defaultRotateAngleZ = 0.0523599F;

        foot0LateralRight = new GGMModelRenderer(this, "foot_proximal_lateral_right");
        this.foot0LateralRight.setTextureOffset(46, 0);
        foot0LateralRight.addBox(-1F, 0F, -1F, 2, 6, 2);
        foot0LateralRight.setRotationPoint(-4.46F, 18F, 2.2F);
        foot0LateralRight.setTextureSize(64, 32);
        setRotation(foot0LateralRight, -0.4886922F, 0.7853982F, -0.2268928F);

        foot0InnerRight = new GGMModelRenderer(this, "foot_proximal_inner_right");
        this.foot0InnerRight.setTextureOffset(54, 0);
        foot0InnerRight.addBox(-1F, 0F, -1F, 2, 6, 2);
        foot0InnerRight.setRotationPoint(-4.34F, 18F, 2.2F);
        foot0InnerRight.setTextureSize(64, 32);
        setRotation(foot0InnerRight, -0.3839724F, -0.7853982F, 0.296706F);

        foot1LateralRight = new GGMModelRenderer(this, "foot_distal_lateral_right");
        this.foot1LateralRight.setTextureOffset(46, 9);
        foot1LateralRight.addBox(-1.2F, 0F, -4F, 2, 2, 5);
        foot1LateralRight.setRotationPoint(-5.25F, 22F, 0F);
        foot1LateralRight.setTextureSize(64, 32);
        setRotation(foot1LateralRight, 0F, 0.4363323F, 0F);

        foot1InnerRight = new GGMModelRenderer(this, "foot_distal_inner_right");
        this.foot1InnerRight.setTextureOffset(46, 16);
        foot1InnerRight.addBox(-0.8F, 0F, -4F, 2, 2, 5);
        foot1InnerRight.setRotationPoint(-3.9F, 22F, 0F);
        foot1InnerRight.setTextureSize(64, 32);
        setRotation(foot1InnerRight, 0F, -0.3490659F, 0F);

        this.foot0LateralRight.addChild(this.foot1LateralRight);
        this.foot0InnerRight.addChild(this.foot1InnerRight);
        this.shinRight.addChild(this.foot0InnerRight);
        this.shinRight.addChild(this.foot0LateralRight);
        this.hip1Right.addChild(this.shinRight);
        this.hip0Right.addChild(this.hip1Right);
        this.corpus.addChild(this.hip0Right);

        hip0Left = new GGMModelRenderer(this, 32, 0);
        hip0Left.mirror = true;
        hip0Left.addBox(-0.8F, -1F, -1.5F, 3, 4, 3);
        hip0Left.setRotationPoint(3F, 9.5F, 1F);
        hip0Left.setTextureSize(64, 32);
        setRotation(hip0Left, 0F, 0F, -0.1396263F);

        hip1Left = new GGMModelRenderer(this, 32, 7);
        hip1Left.mirror = true;
        hip1Left.addBox(-1F, -1.5F, -0.8F, 2, 2, 5);
        hip1Left.setRotationPoint(4.1F, 12F, 0.5F);
        hip1Left.setTextureSize(64, 32);
        setRotation(hip1Left, -0.3490659F, 0F, 0F);

        shinLeft = new GGMModelRenderer(this, 32, 14);
        shinLeft.mirror = true;
        shinLeft.addBox(-1.02F, -0.8F, -0.5F, 2, 7, 2);
        shinLeft.setRotationPoint(4.1F, 12.5F, 4F);
        shinLeft.setTextureSize(64, 32);
        setRotation(shinLeft, -0.4363323F, 0F, -0.0523599F);

        foot0LateralLeft = new GGMModelRenderer(this, 46, 0);
        foot0LateralLeft.mirror = true;
        foot0LateralLeft.addBox(-1F, 0F, -1F, 2, 6, 2);
        foot0LateralLeft.setRotationPoint(4.46F, 18F, 2.2F);
        foot0LateralLeft.setTextureSize(64, 32);
        setRotation(foot0LateralLeft, -0.4537856F, -0.7853982F, 0.2268928F);

        foot0InnerLeft = new GGMModelRenderer(this, 54, 0);
        foot0InnerLeft.mirror = true;
        foot0InnerLeft.addBox(-1F, 0F, -1F, 2, 6, 2);
        foot0InnerLeft.setRotationPoint(4.34F, 18F, 2.2F);
        foot0InnerLeft.setTextureSize(64, 32);
        setRotation(foot0InnerLeft, -0.3839724F, 0.7853982F, -0.296706F);

        foot1LateralLeft = new GGMModelRenderer(this, 46, 9);
        foot1LateralLeft.mirror = true;
        foot1LateralLeft.addBox(-0.8F, 0F, -4F, 2, 2, 5);
        foot1LateralLeft.setRotationPoint(5.25F, 22F, 0F);
        foot1LateralLeft.setTextureSize(64, 32);
        setRotation(foot1LateralLeft, 0F, -0.4363323F, 0F);

        foot1InnerLeft = new GGMModelRenderer(this, 46, 16);
        foot1InnerLeft.mirror = true;
        foot1InnerLeft.addBox(-1.2F, 0F, -4F, 2, 2, 5);
        foot1InnerLeft.setRotationPoint(3.9F, 22F, 0F);
        foot1InnerLeft.setTextureSize(64, 32);
        setRotation(foot1InnerLeft, 0F, 0.3490659F, 0F);

        this.foot0LateralLeft.addChild(this.foot1LateralLeft);
        this.foot0InnerLeft.addChild(this.foot1InnerLeft);
        this.shinLeft.addChild(this.foot0InnerLeft);
        this.shinLeft.addChild(this.foot0LateralLeft);
        this.hip1Left.addChild(this.shinLeft);
        this.hip0Left.addChild(this.hip1Left);
        this.corpus.addChild(this.hip0Left);

        tail0 = new GGMModelRenderer(this, 47, 25);
        tail0.addBox(-2F, -0.5F, -1F, 4, 3, 4);
        tail0.setRotationPoint(0F, 7F, 3.45F);
        tail0.setTextureSize(64, 32);
        tail0.mirror = true;
        setRotation(tail0, -0.2094395F, 0F, 0F);

        tail1 = new GGMModelRenderer(this, 49, 26);
        tail1.addBox(-1.5F, -1F, 0F, 3, 2, 3);
        tail1.setRotationPoint(0F, 8.6F, 6.2F);
        tail1.setTextureSize(64, 32);
        tail1.mirror = true;
        setRotation(tail1, -0.2094395F, 0F, 0F);

        this.tail0.addChild(this.tail1);
        this.corpus.addChild(this.tail0);
    }

    @Override
    public float corpusHeight()
    {
        return this.corpus.defaultRotationPointY;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.corpus.renderWithRotation(f5);
        this.corpus.normalizeAngles();
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        float pi = (float)Math.PI;
        float f6 = (pi / 180F);

        this.head.rotateAngleY = f3 * f6;
        this.head.rotateAngleX = f4 * f6;

        float f7 = MathHelper.cos(f * 0.6662F);
        float f8 = MathHelper.cos(f * 0.6662F + pi);

        this.corpus.rotationPointX = this.corpus.defaultRotationPointX + f7;

        this.hip0Right.rotateAngleX = this.hip0Right.defaultRotateAngleX + (f7 * 1.4F * f1);
        this.hip0Right.rotateAngleZ = this.hip0Right.defaultRotateAngleZ + (f7 * f1 * 0.2F);
        this.hip1Right.rotateAngleX = this.hip1Right.defaultRotateAngleX + (f8 * 1.4F * f1);
        this.shinRight.rotateAngleX = this.shinRight.defaultRotateAngleX + (f7 * 1.4F * f1);
        this.foot1LateralRight.rotateAngleX = this.foot1LateralRight.defaultRotateAngleX + (f8 * 1.2F * f1);
        this.foot1InnerRight.rotateAngleX = this.foot1InnerRight.defaultRotateAngleX + (f8 * 1.2F * f1);

        this.hip0Left.rotateAngleX = this.hip0Left.defaultRotateAngleX + (f8 * 1.4F * f1);
        this.hip0Left.rotateAngleZ = this.hip0Left.defaultRotateAngleZ + (f7 * f1 * 0.2F);
        this.hip1Left.rotateAngleX = this.hip1Left.defaultRotateAngleX + (f7 * 1.4F * f1);
        this.shinLeft.rotateAngleX = this.shinLeft.defaultRotateAngleX + (f8 * 1.4F * f1);
        this.foot1LateralLeft.rotateAngleX = this.foot1LateralLeft.defaultRotateAngleX + (f7 * 1.2F  * f1);
        this.foot1InnerLeft.rotateAngleX = this.foot1InnerLeft.defaultRotateAngleX + (f7 * 1.2F * f1);

        //this.hip0Left.rotateAngleX = (MathHelper.cos(f * 0.6662F + pi) * 1.4F * f1);


        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }


    @Override
    public void updateAnimationLookingAround(float progress)
    {
        if (progress < 0.5F)
        {
            if (progress < 0.15F)
            {
                progress *= 6F;
            }
            else if (progress < 0.35F)
            {
                progress = 0.9F;
            }
            else
            {
                progress = (0.5F - progress) * 6F;
            }
        }
        else
        {
            if (progress < 0.65F)
            {
                progress = -((progress - 0.5F) * 6F);
            }
            else if (progress < 0.85F)
            {
                progress = -0.9F;
            }
            else
            {
                progress = -((1F - progress) * 6F);
            }
        }

        this.neck0.rotateAngleY = this.neck0.defaultRotateAngleY + progress;
        this.head.rotateAngleY = this.head.defaultRotateAngleY + progress;
    }


    @Override
    public void updateAnimationEat(float progress)
    {
        if (progress < 0.3F)
        {
            progress %= 0.1F;
            progress *= 10F;

            this.updateAnimationPecking(progress);
        }
        else
        {
            this.updateAnimationSwallowing((progress - 0.3F) * 1.42857142857F);

        }
    }

    public void updateAnimationPecking(float progress)
    {
        this.head.rotateAngleX = this.head.defaultRotateAngleX;
        this.head.rotateAngleY = this.head.defaultRotateAngleY;

        if (progress > 0.5F) progress = 1F - progress;
        progress *= 1.5F;

        this.corpus.rotateAngleX = this.corpus.defaultRotateAngleX + progress;
        this.hip0Right.rotateAngleX = this.hip0Left.defaultRotateAngleX - progress;
        this.hip0Left.rotateAngleX = this.hip0Left.defaultRotateAngleX - progress;
        this.neck1.rotateAngleX = this.neck1.defaultRotateAngleX + progress * 2.0F;
        this.head.rotateAngleX = this.head.defaultRotateAngleX - progress * 1.5F;
    }

    public void updateAnimationSwallowing(float progress)
    {
        this.head.rotateAngleX = this.head.defaultRotateAngleX;
        this.head.rotateAngleY = this.head.defaultRotateAngleY;

        if (progress < 0.1F)
        {
            progress *= 5F;
        }
        else if (progress > 0.9F)
        {
            progress = (1.0F - progress) * 5F;
        }
        else
        {
            progress -= 0.1F;
            float f0 = progress % 0.1F;
            if (f0 > 0.05F) f0 = 0.1F - f0;
            f0 *= 5F;
            if (progress % 0.2F >= 0.1F) f0 = -f0;

            //this.corpus.rotateAngleY = this.corpus.defaultRotateAngleX + f0;
            //this.hip0Right.rotateAngleY = this.hip0Right.defaultRotateAngleY - f0;
            //this.hip0Left.rotateAngleY = this.hip0Left.defaultRotateAngleY - f0;
            this.neck0.rotateAngleY = this.neck0.defaultRotateAngleY + f0;
            progress = 0.5F;
        }

        this.corpus.rotateAngleX = this.corpus.defaultRotateAngleX - progress;
        this.hip0Right.rotateAngleX = this.hip0Left.defaultRotateAngleX + progress;
        this.hip0Left.rotateAngleX = this.hip0Left.defaultRotateAngleX + progress;
        this.neck1.rotateAngleX = this.neck1.defaultRotateAngleX + progress;
        this.head.rotateAngleX = this.head.defaultRotateAngleX - progress;
    }

    public void updateAnimationSitDown(float progress)
    {
        this.corpus.rotateAngleX = this.corpus.defaultRotateAngleX + progress * 0.2F;//0.17453292519F;
        this.corpus.rotationPointY = this.corpus.defaultRotationPointY + progress * 10F;
        this.hip0Right.rotateAngleX = this.hip0Right.defaultRotateAngleX + 1.1F * progress;
        this.hip0Left.rotateAngleX = this.hip0Left.defaultRotateAngleX + 1.1F * progress;
        this.hip1Right.rotateAngleX = this.hip1Right.defaultRotateAngleX - 1.2F * progress;
        this.hip1Left.rotateAngleX = this.hip1Left.defaultRotateAngleX - 1.2F * progress;
        this.shinRight.rotateAngleX = this.shinRight.defaultRotateAngleX - 1.2F * progress;
        this.shinLeft.rotateAngleX = this.shinLeft.defaultRotateAngleX - 1.2F * progress;
        this.foot1LateralRight.rotateAngleX = this.foot1LateralRight.defaultRotateAngleX + 0.6F * progress;
        this.foot1InnerRight.rotateAngleX = this.foot1InnerRight.defaultRotateAngleX + 0.6F * progress;
        this.foot1LateralLeft.rotateAngleX = this.foot1LateralLeft.defaultRotateAngleX + 0.6F * progress;
        this.foot1InnerLeft.rotateAngleX = this.foot1InnerLeft.defaultRotateAngleX + 0.6F * progress;
    }

    public void updateAnimationSitting(float progress, float border)
    {
        if (progress < border)
        {
            progress *= 1F / border;
        }
        else if (progress < 1F - border)
        {
            progress = 1.0F;
        }
        else
        {
            progress = (1F - progress) * 1F / border;
        }

        this.updateAnimationSitDown(progress);
    }

    @Override
    public void updateAnimationSleeping(float progress)
    {
        updateAnimationSitting(progress, 0.007F);

        if (progress < 0.02F) progress *= 50F;
        else if (progress < 0.98F) progress = 1F;
        else
        {
            progress = (1F - progress) * 50F;
        }

        this.neck0.rotateAngleZ = this.neck0.defaultRotateAngleZ - progress * 1.7F;
    }

    @Override
    public void updateAnimationWakeUp(float progress)
    {
        progress = 1F - progress;
        this.updateAnimationSitDown(progress);
        this.neck0.rotateAngleZ = this.neck0.defaultRotateAngleZ - progress * 1.7F;
    }

    @Override
    public void updateAnimationChildBirth(float progress)
    {
        this.updateAnimationSitting(progress, 0.05F);
    }

    @Override
    public void updateAnimationAggr(float progress)
    {
        float tilt = progress;

        if (tilt < 0.15F)
        {
            tilt *= 3.333F;
        }
        else if (tilt > 0.85F)
        {
            tilt = (1F - tilt) * 3.333F;
        }
        else
        {
            tilt = 0.5F;
        }

        this.updateAnimationPecking(tilt);

        progress = (progress % 0.2F) * 5F;
        if (progress > 0.5F) progress = 1F - progress;

        this.corpus.rotateAngleY = this.corpus.defaultRotateAngleY + progress;
        this.hip0Right.rotateAngleY = this.hip0Right.defaultRotateAngleY - progress;
        this.hip0Left.rotateAngleY = this.hip0Left.defaultRotateAngleY - progress;
        this.neck0.rotateAngleY = this.neck0.defaultRotateAngleY + progress * 0.5F;
        this.head.rotateAngleZ = this.head.defaultRotateAngleZ - progress * 1.5F;
    }


    @Override
    public void updateAnimationHit(float progress)
    {
        this.head.rotateAngleX = this.head.defaultRotateAngleX;
        this.head.rotateAngleY = this.head.defaultRotateAngleY;

        if (progress >= 0.5F)
        {
            float f0 = (progress - 0.5F) * 2F;
            progress = 0.5F - f0;
        }

        this.corpus.rotateAngleX = this.corpus.defaultRotateAngleX - progress * 2F;
        this.hip0Right.rotateAngleX += progress;
        this.hip0Left.rotateAngleX += progress;
        this.neck0.rotateAngleX = this.neck0.defaultRotateAngleX - progress;
        this.neck1.rotateAngleX = this.neck1.defaultRotateAngleX + progress;
    }

    @Override
    public void updateAnimationHitOnRun(float progress)
    {
        this.updateAnimationHit(progress);
    }
}
