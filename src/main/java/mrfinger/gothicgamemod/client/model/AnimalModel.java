package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AnimalModel extends ModelBase {
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float f8) {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity, f8);	    
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity, float f8) {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);	    
	}
	
	public void attachX (ModelRenderer baseShape, ModelRenderer attachingShape, float distance, float A, float B) {		
		
		attachingShape.rotationPointY = baseShape.rotationPointY + (float) (distance * Math.sin(B - baseShape.rotateAngleX));
		attachingShape.rotationPointZ = baseShape.rotationPointZ + (float) (distance * Math.sin(A + baseShape.rotateAngleX));
		
	}
	
	/*public void attachX (ModelRenderer baseShape, ModelRenderer attachingShape, float differenceY, float differenceZ) {
		
		float artsDistance = (float) Math.sqrt(differenceY * differenceY + differenceZ * differenceZ);
		
		attachingShape.rotationPointY = baseShape.rotationPointY + (float) (artsDistance * Math.sin(Math.PI / 2 - (Math.asin(differenceZ / artsDistance) + baseShape.rotateAngleX)));
		attachingShape.rotationPointZ = baseShape.rotationPointZ + (float) (artsDistance * Math.sin(Math.asin(differenceZ / artsDistance) + baseShape.rotateAngleX));
	
	/*public void attachX (ModelRenderer baseShape, ModelRenderer attachingShape, float differenceY, float differenceZ) {
		
		attachingShape.rotationPointY = baseShape.rotationPointY + differenceY * MathHelper.sin((float) Math.PI/2 - baseShape.rotateAngleX) + differenceZ * MathHelper.sin(baseShape.rotateAngleX);
		
	}*/
	

}
