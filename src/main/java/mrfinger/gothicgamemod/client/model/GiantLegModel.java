package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GiantLegModel extends AnimalModel {
	
	private float hipsPosY = 0F,
				  hipsPosZ = 0F,
				  shinsPosYWithoutHips = 12F,
				  shinsPosXWithoutHips = 0F,
				  shinsArtLongFromHipsArt = (float) Math.sqrt(this.shinsPosXWithoutHips * this.shinsPosXWithoutHips + this.shinsPosYWithoutHips * this.shinsPosYWithoutHips);
	
	ModelRenderer hip;
    ModelRenderer shin;
    
    public GiantLegModel() {
        textureWidth = 64;
        textureHeight = 32;
        
          hip = new ModelRenderer(this, 0, 0);
          hip.addBox(-0F, 0F, 0F, 6, 12, 6);
          hip.setRotationPoint(0F, 0F, 0F);
          hip.setTextureSize(64, 32);
          hip.mirror = true;
          setRotation(hip, 0F, 0F, 0F);
          
          shin = new ModelRenderer(this, 0, 0);
          shin.addBox(-0F, 0F, 0F, 6, 12, 6);
          shin.setRotationPoint(0F, this.shinsPosXWithoutHips, -3F);
          shin.setTextureSize(64, 32);
          shin.mirror = true;
          setRotation(shin, 0F, 0F, 0F);
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float f7) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      setRotationAngles(f, f1, f2, f3, f4, f5, entity, f7);
      hip.render(f5);
      shin.render(f5);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity, float f7) {
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      
    	float rightLegRotX = (MathHelper.cos(f * 0.6662F) *1.4F * f1);
    	
    }
    	

}
