

package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MeatbugModel extends ModelBase {
	
    ModelRenderer corpusLower;
    ModelRenderer corpusUpper;
    ModelRenderer limbsRight;
    ModelRenderer limbsLeft;
    ModelRenderer mustaches;
  
  public MeatbugModel()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      corpusLower = new ModelRenderer(this, 0, 0);
      corpusLower.addBox(-2F, 1F, -3F, 4, 1, 6);
      corpusLower.setRotationPoint(0F, 21F, 0F);
      corpusLower.setTextureSize(64, 32);
      corpusLower.mirror = true;
      setRotation(corpusLower, 0F, 0F, 0F);
      corpusUpper = new ModelRenderer(this, 0, 7);
      corpusUpper.addBox(-1F, 0F, -3F, 2, 1, 6);
      corpusUpper.setRotationPoint(0F, 21F, 0F);
      corpusUpper.setTextureSize(64, 32);
      corpusUpper.mirror = true;
      setRotation(corpusUpper, 0F, 0F, 0F);
      limbsRight = new ModelRenderer(this, 20, 0);
      limbsRight.addBox(-2F, 0F, -3F, 2, 0, 6);
      limbsRight.setRotationPoint(-2F, 23F, 0F);
      limbsRight.setTextureSize(64, 32);
      limbsRight.mirror = true;
      setRotation(limbsRight, 0F, 0F, -0.5410521F);
      limbsLeft = new ModelRenderer(this, 20, 0);
      limbsLeft.addBox(0F, 0F, -3F, 2, 0, 6);
      limbsLeft.setRotationPoint(2F, 23F, 0F);
      limbsLeft.setTextureSize(64, 32);
      limbsLeft.mirror = true;
      setRotation(limbsLeft, 0F, 0F, 0.5410521F);//-5.7421331F
      mustaches = new ModelRenderer(this, 20, 6);
      mustaches.addBox(-2F, 1F, -7F, 4, 0, 4);
      mustaches.setRotationPoint(0F, 21F, 0F);
      mustaches.setTextureSize(64, 32);
      mustaches.mirror = true;
      setRotation(mustaches, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    corpusLower.render(f5);
    corpusUpper.render(f5);
    limbsRight.render(f5);
    limbsLeft.render(f5);
    mustaches.render(f5);
    limbsRight.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
