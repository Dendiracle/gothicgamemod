package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class NikitaModel extends ModelAnimal {
  
    ModelRenderer LL;
    ModelRenderer RL;
    ModelRenderer B;
    ModelRenderer leftArm;
    ModelRenderer rightArm;
    ModelRenderer head;
  
  public NikitaModel()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      LL = new ModelRenderer(this, 0, 32);
      LL.addBox(-4F, 0F, -4F, 8, 24, 8);
      LL.setRotationPoint(4F, 0F, 0F);
      LL.setTextureSize(128, 64);
      LL.mirror = true;
      setRotation(LL, 3F, 0F, 0F);
      RL = new ModelRenderer(this, 0, 32);
      RL.addBox(-4F, 0F, -4F, 8, 24, 8);
      RL.setRotationPoint(-4F, 0F, 0F);
      RL.setTextureSize(128, 64);
      RL.mirror = true;
      setRotation(RL, 0F, 0F, 0F);
      B = new ModelRenderer(this, 32, 32);
      B.addBox(-8F, 0F, -4F, 16, 24, 8);
      B.setRotationPoint(0F, -24F, 0F);
      B.setTextureSize(128, 64);
      B.mirror = true;
      setRotation(B, 0F, 0F, 0F);
      leftArm = new ModelRenderer(this, 80, 32);
      leftArm.addBox(0F, -4F, -4F, 8, 24, 8);
      leftArm.setRotationPoint(8F, -20F, 0F);
      leftArm.setTextureSize(128, 64);
      leftArm.mirror = true;
      setRotation(leftArm, 0F, 0F, 0F);
      rightArm = new ModelRenderer(this, 80, 32);
      rightArm.addBox(-8F, -4F, -4F, 8, 24, 8);
      rightArm.setRotationPoint(-8F, -20F, 0F);
      rightArm.setTextureSize(128, 64);
      rightArm.mirror = true;
      setRotation(rightArm, 0F, 0F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-8F, -16F, -8F, 16, 16, 16);
      head.setRotationPoint(0F, -24F, 0F);
      head.setTextureSize(128, 64);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
  }

  @Override
  public float corpusHeight() {
    return 0;
  }

  public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float f7)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity, f7);
    LL.render(f5);
    RL.render(f5);
    B.render(f5);
    leftArm.render(f5);
    rightArm.render(f5);
    head.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity, float f8)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    float f6 = (180F / (float)Math.PI);
    
    this.head.setRotationPoint(0F, (-24F) + 24F * f1 - MathHelper.sin(f8) * 10, 0F);
    this.head.rotateAngleX = f4 / (180F / (float)Math.PI);
    this.head.rotateAngleY = f3 / (180F / (float)Math.PI);
    
    this.leftArm.setRotationPoint(8F, (-20F) + 24F * f1, 4F * f1);
    this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) *1.4F * f1;
    this.rightArm.setRotationPoint(-8F, (-20F) + 24F * f1, 4F * f1);
    this.rightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) *1.4F * f1;
    
    this.B.setRotationPoint(0F * f1, (-24F) + 24F * f1, 0F * f1);
    this.B.rotateAngleX = MathHelper.cos(0F) *1.7F * f1;
    
    this.LL.setRotationPoint(4F, 0F, 28F * f1);
    this.LL.rotateAngleX = MathHelper.cos(f * 0.6662F) *1.4F * f1;
    this.RL.setRotationPoint(-4F, 0F, 28F * f1);
    this.RL.rotateAngleX = MathHelper.cos(f * 0.6662F) *1.4F * f1;
    
  }

}
