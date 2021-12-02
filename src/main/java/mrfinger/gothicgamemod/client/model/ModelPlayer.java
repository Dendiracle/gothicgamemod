package mrfinger.gothicgamemod.client.model;

import mrfinger.gothicgamemod.client.IGGMMinecraft;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.Timer;

public class ModelPlayer extends ModelBiped
{

    public Timer mineTimer;


    public GGMModelRenderer forearmRight;
    public GGMModelRenderer forearmLeft;
    public GGMModelRenderer shinRight;
    public GGMModelRenderer shinLeft;

    public ModelPlayer()
    {
        this(0.0F);
    }

    public ModelPlayer(float f)
    {
        this(f, 0.0F, 64, 32);
    }

    public ModelPlayer(float f, float f1, int p_i1149_3_, int p_i1149_4_)
    {
        super(f, f1, p_i1149_3_, p_i1149_4_);

        this.mineTimer = ((IGGMMinecraft) Minecraft.getMinecraft()).getTimer();

        this.bipedRightArm = new GGMModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 6, 4, f);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + f, 0.0F);

        this.forearmRight = new GGMModelRenderer(this, 40, 22);
        this.forearmRight.addBox(-2.0F, 0.0F, -3.0F, 4, 6, 4, f);
        this.forearmRight.setRotationPoint(-6.0F, 6.0F + f, 1.0F);
        this.bipedRightArm.addChild(this.forearmRight);

        this.bipedLeftArm = new GGMModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 6, 4, f);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f, 0.0F);

        this.forearmLeft = new GGMModelRenderer(this, 40, 22);
        this.forearmLeft.mirror = true;
        this.forearmLeft.addBox(-2.0F, 0.0F, -3.0F, 4, 6, 4, f);
        this.forearmLeft.setRotationPoint(6.0F, 6.0F + f, 1.0F);
        this.bipedLeftArm.addChild(this.forearmLeft);

        /*this.bipedRightLeg = new GGMModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + f, 0.0F);

        this.bipedLeftLeg = new GGMModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + f, 0.0F);*/
    }


    @Override
    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);

        this.bipedHead.render(p_78088_7_);
        this.bipedBody.render(p_78088_7_);
        this.bipedRightArm.renderWithRotation(p_78088_7_);
        this.bipedLeftArm.renderWithRotation(p_78088_7_);
        this.bipedRightLeg.render(p_78088_7_);
        this.bipedLeftLeg.render(p_78088_7_);
        this.bipedHeadwear.render(p_78088_7_);
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);

        this.forearmRight.rotateAngleX = 0.0F;
        this.forearmRight.rotateAngleY = 0.0F;
        this.forearmRight.rotateAngleZ = 0.0F;
        this.forearmLeft.rotateAngleX = 0.0F;
        this.forearmLeft.rotateAngleY = 0.0F;
        this.forearmLeft.rotateAngleZ = 0.0F;

        /*float keks = (entity.ticksExisted + this.mineTimer.renderPartialTicks) * 0.02F * (float) Math.PI;
        this.forearmRight.rotateAngleY = keks;
        this.forearmLeft.rotateAngleY = -keks;

        keks = entity.ticksExisted % 100;
        keks = keks < 50.0F ? keks : 100.0F - keks;
        keks += this.mineTimer.renderPartialTicks;
        keks = keks * 0.04F * (float) Math.PI;

        this.forearmRight.rotateAngleX = keks;
        this.forearmLeft.rotateAngleZ = keks;*/

        ((IGGMEntityPlayer) entity).getActiveAnimationHelper().modifyModel(this, p_78087_1_, p_78087_2_, ((IGGMMinecraft) Minecraft.getMinecraft()).getTimer().renderPartialTicks);

    }


    public void updateAnimationHitSplash(IGGMEntityPlayer player, IAnimationEpisode animationEpisode, float progress)
    {
        if (progress > 1.5F) progress = 1.5F;

        float pi = (float) Math.PI;

        this.bipedRightArm.rotateAngleY = -(pi * 0.2F * progress);
        this.bipedRightArm.rotateAngleZ = -(pi * 0.2F * progress);
        this.bipedRightArm.rotateAngleX = -(pi * 0.1F * progress) - 0.5F;
        this.bipedRightArm.rotationPointX += 1.5F * progress;
        this.bipedRightArm.rotationPointZ -= progress;
    }

}
