
package mrfinger.gothicgamemod.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RuneModel extends ModelBase {
    ModelRenderer base;
    ModelRenderer top1;
    ModelRenderer top2;
    ModelRenderer bot1;
    ModelRenderer bot2;
    
    private float rY = 0.0F;
  
    public RuneModel() {
    	textureWidth = 64;
    	textureHeight = 32;
    
      base = new ModelRenderer(this, 20, 0);
      base.addBox(-6F, -16F, -5F, 12, 16, 10);
      base.setRotationPoint(0F, this.rY, 0F);
      base.setTextureSize(64, 32);
      base.mirror = true;
      setRotation(base, 0F, 0F, 0F);
      top1 = new ModelRenderer(this, 0, 23);
      top1.addBox(-4.5F, -17F, -4F, 9, 1, 8);
      top1.setRotationPoint(0F, this.rY, 0F);
      top1.setTextureSize(64, 32);
      top1.mirror = true;
      setRotation(top1, 0F, 0F, 0F);
      top2 = new ModelRenderer(this, 5, 26);
      top2.addBox(-3F, -18F, -2.5F, 6, 1, 5);
      top2.setRotationPoint(0F, this.rY, 0F);
      top2.setTextureSize(64, 32);
      top2.mirror = true;
      setRotation(top2, 0F, 0F, 0F);
      bot1 = new ModelRenderer(this, 0, 23);
      bot1.addBox(-4.5F, -1F, -4F, 9, 1, 8);
      bot1.setRotationPoint(0F, this.rY, 0F);
      bot1.setTextureSize(64, 32);
      bot1.mirror = true;
      setRotation(bot1, 3.141593F, 0F, 0F);
      bot2 = new ModelRenderer(this, 5, 26);
      bot2.addBox(-3F, -2F, -2.5F, 6, 1, 5);
      bot2.setRotationPoint(0F, this.rY, 0F);
      bot2.setTextureSize(64, 32);
      bot2.mirror = true;
      setRotation(bot2, 3.141593F, this.rY, 0F);
  }
  
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    base.render(f5);
    top1.render(f5);
    top2.render(f5);
    bot1.render(f5);
    bot2.render(f5);
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
