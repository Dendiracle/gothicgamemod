package mrfinger.gothicgamemod.client.model;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelPlayer extends ModelBiped
{

    public float tickDur;

    public ModelPlayer() {}

    public ModelPlayer(float f)
    {
        super(f);
    }


    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);

        IGGMEntityPlayer player = (IGGMEntityPlayer) entity;

        float count = player.getAttackCount();

        if (count > 0)
        {
            float progress = ((player.getLastAttackDuration() - count) + this.tickDur) / (player.getLastAttackDuration() - player.getAttackTick());
            if (progress > 1.5F) progress = 1.5F;

            float pi = (float) Math.PI;

            this.bipedRightArm.rotateAngleY = -(pi * 0.2F * progress);
            this.bipedRightArm.rotateAngleZ = -(pi * 0.2F * progress);
            this.bipedRightArm.rotateAngleX = -(pi * 0.15F * progress);
            this.bipedRightArm.rotationPointX += 1.5F * progress;
            this.bipedRightArm.rotationPointZ -= progress;
        }
    }


}
