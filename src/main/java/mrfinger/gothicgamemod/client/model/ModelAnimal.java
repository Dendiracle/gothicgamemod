package mrfinger.gothicgamemod.client.model;

import mrfinger.gothicgamemod.client.IGGMMinecraft;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public abstract class ModelAnimal extends ModelEntityHerd
{
	
	public void attachX (ModelRenderer baseShape, ModelRenderer attachingShape, float distance, float A, float B)
	{
		attachingShape.rotationPointY = baseShape.rotationPointY + (float) (distance * Math.sin(B - baseShape.rotateAngleX));
		attachingShape.rotationPointZ = baseShape.rotationPointZ + (float) (distance * Math.sin(A + baseShape.rotateAngleX));
	}

	public abstract float corpusHeight();


	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);

		if (entity instanceof IGGMEntityLivingBase)((IGGMEntityLivingBase) entity).getActiveAnimationHelper().modifyModel(this, p_78087_1_, p_78087_2_, ((IGGMMinecraft) Minecraft.getMinecraft()).getTimer().renderPartialTicks);
	}


	protected void setRotation(GGMModelRenderer model, float x, float y, float z)
	{
		model.defaultRotateAngleX = x;
		model.defaultRotateAngleY = y;
		model.defaultRotateAngleZ = z;

		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


	public void updateAnimationEat(float progress) {}

	public void updateAnimationSleeping(float progress) {}

	public void updateAnimationWakeUp(float progress) {}

	public void updateAnimationChildBirth(float progress) {}

	public void updateAnimationAggr(float progress) {}


	public void updateAnimationHit(float progress) {}

	public void updateAnimationHitOnRun(float progress) {}


/*public void attachX (ModelRenderer baseShape, ModelRenderer attachingShape, float differenceY, float differenceZ) {
		
		float artsDistance = (float) Math.sqrt(differenceY * differenceY + differenceZ * differenceZ);
		
		attachingShape.rotationPointY = baseShape.rotationPointY + (float) (artsDistance * Math.sin(Math.PI / 2 - (Math.asin(differenceZ / artsDistance) + baseShape.rotateAngleX)));
		attachingShape.rotationPointZ = baseShape.rotationPointZ + (float) (artsDistance * Math.sin(Math.asin(differenceZ / artsDistance) + baseShape.rotateAngleX));
	
	/*public void attachX (ModelRenderer baseShape, ModelRenderer attachingShape, float differenceY, float differenceZ) {
		
		attachingShape.rotationPointY = baseShape.rotationPointY + differenceY * MathHelper.sin((float) Math.PI/2 - baseShape.rotateAngleX) + differenceZ * MathHelper.sin(baseShape.rotateAngleX);
		
	}*/
	

}
