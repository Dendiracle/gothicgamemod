package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ScavengerModel extends AnimalModel
{
    ModelRenderer RightFoot;
    ModelRenderer RightShin;
    ModelRenderer RightHip;
    ModelRenderer LeftFoot;
    ModelRenderer Corpus;
    ModelRenderer LeftShin;
    ModelRenderer LeftHip;
    ModelRenderer Neck;
    ModelRenderer Head;   
  
  public ScavengerModel()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      RightFoot = new ModelRenderer(this, 12, 22);
      RightFoot.addBox(-3F, 14F, -7F, 5, 2, 7);
      RightFoot.setRotationPoint(-3F, 8F, 4F);
      RightFoot.setTextureSize(64, 32);
      RightFoot.mirror = true;
      setRotation(RightFoot, 0F, 0F, 0F);
      RightShin = new ModelRenderer(this, 48, 12);
      RightShin.addBox(-2F, 1.2F, 7F, 2, 11, 2);
      RightShin.setRotationPoint(-3F, 8F, 4F);
      RightShin.setTextureSize(64, 32);
      RightShin.mirror = true;
      setRotation(RightShin, -0.6283185F, 0F, 0F);
      RightHip = new ModelRenderer(this, 40, 12);
      RightHip.addBox(-2F, -1F, -1.2F, 2, 10, 2);
      RightHip.setRotationPoint(-3F, 8F, 4F);
      RightHip.setTextureSize(64, 32);
      RightHip.mirror = true;
      setRotation(RightHip, 0.9424778F, 0F, 0F);
      LeftFoot = new ModelRenderer(this, 12, 22);
      LeftFoot.addBox(-2F, 14F, -7F, 5, 2, 7);
      LeftFoot.setRotationPoint(3F, 8F, 4F);
      LeftFoot.setTextureSize(64, 32);
      LeftFoot.mirror = true;
      setRotation(LeftFoot, 0F, 0F, 0F);
      Corpus = new ModelRenderer(this, 0, 0);
      Corpus.addBox(-3F, -6F, -7F, 6, 8, 14);
      Corpus.setRotationPoint(0F, 8F, 4F);
      Corpus.setTextureSize(64, 32);
      Corpus.mirror = true;
      setRotation(Corpus, -0.1745329F, 0F, 0F);
      LeftShin = new ModelRenderer(this, 48, 12);
      LeftShin.addBox(0F, 1.2F, 7F, 2, 11, 2);
      LeftShin.setRotationPoint(3F, 8F, 4F);
      LeftShin.setTextureSize(64, 32);
      LeftShin.mirror = true;
      setRotation(LeftShin, -0.6283185F, 0F, 0F);
      LeftHip = new ModelRenderer(this, 40, 12);
      LeftHip.addBox(0F, -1F, -1.2F, 2, 10, 2);
      LeftHip.setRotationPoint(3F, 8F, 4F);
      LeftHip.setTextureSize(64, 32);
      LeftHip.mirror = true;
      setRotation(LeftHip, 0.9424778F, 0F, 0F);
      Neck = new ModelRenderer(this, 0, 22);
      Neck.addBox(-1F, -1F, -11F, 2, 2, 4);
      Neck.setRotationPoint(0F, 8F, 4F);
      Neck.setTextureSize(64, 32);
      Neck.mirror = true;
      setRotation(Neck, -0.1745329F, 0F, 0F);
      Head = new ModelRenderer(this, 40, 0);
      Head.addBox(-2F, -7.5F, -16F, 4, 4, 8);
      Head.setRotationPoint(0F, 8F, 4F);
      Head.setTextureSize(64, 32);
      Head.mirror = true;
      setRotation(Head, 0.0872665F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float f7)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity, f7);
    RightFoot.render(f5);
    RightShin.render(f5);
    RightHip.render(f5);
    LeftFoot.render(f5);
    Corpus.render(f5);
    LeftShin.render(f5);
    LeftHip.render(f5);
    Neck.render(f5);
    Head.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity, float f8)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    float f6 = (180F / (float)Math.PI);
    //this.Head.setRotationPoint(0F, (-24F) + 24F * f1, 0F);
    //this.Head.rotateAngleX = f4 / (180F / (float)Math.PI);
    //this.Head.rotateAngleY = f3 / (180F / (float)Math.PI);
    this.RightHip.rotateAngleX = 0.9424778F + (MathHelper.cos(f * 0.6662F) *1.4F * f1);
    this.RightShin.rotateAngleX = -0.6283185F + (MathHelper.cos(f * 0.6662F) *1.4F * f1);
    this.RightFoot.rotateAngleX = MathHelper.cos(f * 0.6662F) *1.4F * f1;
    this.LeftHip.rotateAngleX = 0.9424778F + (MathHelper.cos(f * 0.6662F + (float)Math.PI) *1.4F * f1);
    this.LeftShin.rotateAngleX = -0.6283185F + (MathHelper.cos(f * 0.6662F + (float)Math.PI) *1.4F * f1);
    this.LeftFoot.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) *1.4F * f1;
    
    this.Corpus.rotateAngleX = -0.1745329F + MathHelper.sin(f8) * 0.5F;
    this.Neck.rotateAngleX = -0.1745329F + MathHelper.sin(f8) * 0.5F;
    this.Head.rotateAngleX =  0.0872665F + MathHelper.sin(f8) * 0.5F;
    
  }
  
  

}
