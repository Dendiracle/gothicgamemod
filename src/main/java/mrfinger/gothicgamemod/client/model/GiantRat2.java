// Date: 12.07.2020 19:59:41
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX





/*
package net.minecraft.src;

public class ModelNew extends ModelBase
{
  //fields
    ModelRenderer corpus;
    ModelRenderer head;
    ModelRenderer nose;
    ModelRenderer shoulderRight;
    ModelRenderer handRight;
    ModelRenderer forearmRight;
    ModelRenderer shoulderLeft;
    ModelRenderer forearmLeft;
    ModelRenderer handLeft;
    ModelRenderer hipRight;
    ModelRenderer shinUpper1Right;
    ModelRenderer shinLowerRight;
    ModelRenderer shipUpper2Right;
    ModelRenderer footRight;
    ModelRenderer hipLeft;
    ModelRenderer shinUpper1Left;
    ModelRenderer shipUpper2Left;
    ModelRenderer shinLowerLeft;
    ModelRenderer footLeft;
    ModelRenderer tail1;
    ModelRenderer tail2;
    ModelRenderer earRight;
    ModelRenderer earLeft;
    ModelRenderer tail3;
  
  public ModelNew()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      corpus = new ModelRenderer(this, 0, 0);
      corpus.addBox(-3F, -5F, -9F, 6, 6, 10);
      corpus.setRotationPoint(0F, 19F, 6F);
      corpus.setTextureSize(64, 32);
      corpus.mirror = true;
      setRotation(corpus, 0F, 0F, 0F);
      head = new ModelRenderer(this, 32, 0);
      head.addBox(-2.5F, -2F, -5F, 5, 4, 5);
      head.setRotationPoint(0F, 15.5F, -3F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      nose = new ModelRenderer(this, 52, 0);
      nose.addBox(-1.5F, -1F, -8F, 3, 3, 3);
      nose.setRotationPoint(0F, 15.5F, -3F);
      nose.setTextureSize(64, 32);
      nose.mirror = true;
      setRotation(nose, 0F, 0F, 0F);
      shoulderRight = new ModelRenderer(this, 32, 9);
      shoulderRight.addBox(-0.5F, 0F, -1F, 2, 3, 2);
      shoulderRight.setRotationPoint(-3F, 18.5F, -1F);
      shoulderRight.setTextureSize(64, 32);
      shoulderRight.mirror = true;
      setRotation(shoulderRight, 0F, 0F, 0F);
      handRight = new ModelRenderer(this, 44, 9);
      handRight.addBox(0F, 4.5F, -3F, 1, 1, 2);
      handRight.setRotationPoint(-3F, 18.5F, -1F);
      handRight.setTextureSize(64, 32);
      handRight.mirror = true;
      setRotation(handRight, 0F, 0F, 0F);
      forearmRight = new ModelRenderer(this, 40, 9);
      forearmRight.addBox(0F, 2.2F, 1F, 1, 3, 1);
      forearmRight.setRotationPoint(-3F, 18.5F, -1F);
      forearmRight.setTextureSize(64, 32);
      forearmRight.mirror = true;
      setRotation(forearmRight, -0.5585054F, 0F, 0F);
      shoulderLeft = new ModelRenderer(this, 32, 9);
      shoulderLeft.addBox(-1.5F, 0F, -1F, 2, 3, 2);
      shoulderLeft.setRotationPoint(3F, 18.5F, -1F);
      shoulderLeft.setTextureSize(64, 32);
      shoulderLeft.mirror = true;
      setRotation(shoulderLeft, 0F, 0F, 0F);
      forearmLeft = new ModelRenderer(this, 40, 9);
      forearmLeft.addBox(-1F, 2.2F, 1F, 1, 3, 1);
      forearmLeft.setRotationPoint(3F, 18.5F, -1F);
      forearmLeft.setTextureSize(64, 32);
      forearmLeft.mirror = true;
      setRotation(forearmLeft, -0.5585054F, 0F, 0F);
      handLeft = new ModelRenderer(this, 44, 9);
      handLeft.addBox(-1F, 4.5F, -3F, 1, 1, 2);
      handLeft.setRotationPoint(3F, 18.5F, -1F);
      handLeft.setTextureSize(64, 32);
      handLeft.mirror = true;
      setRotation(handLeft, 0F, 0F, 0F);
      hipRight = new ModelRenderer(this, 32, 14);
      hipRight.addBox(-0.5F, 0F, -1F, 2, 4, 2);
      hipRight.setRotationPoint(-3F, 18.5F, 5F);
      hipRight.setTextureSize(64, 32);
      hipRight.mirror = true;
      setRotation(hipRight, 0F, 0F, 0F);
      shinUpper1Right = new ModelRenderer(this, 40, 14);
      shinUpper1Right.addBox(-0.99F, 0F, -1F, 2, 2, 2);
      shinUpper1Right.setRotationPoint(-2.5F, 21.5F, 5F);
      shinUpper1Right.setTextureSize(64, 32);
      shinUpper1Right.mirror = true;
      setRotation(shinUpper1Right, 1.658063F, 0F, 0F);
      shinLowerRight = new ModelRenderer(this, 44, 17);
      shinLowerRight.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
      shinLowerRight.setRotationPoint(-2.5F, 21.4F, 8.5F);
      shinLowerRight.setTextureSize(64, 32);
      shinLowerRight.mirror = true;
      setRotation(shinLowerRight, -0.3490659F, 0F, 0F);
      shipUpper2Right = new ModelRenderer(this, 40, 17);
      shipUpper2Right.addBox(-0.5F, 1.9F, -0.7F, 1, 2, 1);
      shipUpper2Right.setRotationPoint(-2.5F, 21.5F, 5F);
      shipUpper2Right.setTextureSize(64, 32);
      shipUpper2Right.mirror = true;
      setRotation(shipUpper2Right, 1.658063F, 0F, 0F);
      footRight = new ModelRenderer(this, 48, 14);
      footRight.addBox(0F, 0F, -2F, 1, 1, 2);
      footRight.setRotationPoint(-3F, 23F, 8.3F);
      footRight.setTextureSize(64, 32);
      footRight.mirror = true;
      setRotation(footRight, 0F, 0F, 0F);
      hipLeft = new ModelRenderer(this, 32, 14);
      hipLeft.addBox(-1.5F, 0F, -1F, 2, 4, 2);
      hipLeft.setRotationPoint(3F, 18.5F, 5F);
      hipLeft.setTextureSize(64, 32);
      hipLeft.mirror = true;
      setRotation(hipLeft, 0F, 0F, 0F);
      shinUpper1Left = new ModelRenderer(this, 40, 14);
      shinUpper1Left.addBox(-1.01F, 0F, -1F, 2, 2, 2);
      shinUpper1Left.setRotationPoint(2.5F, 21.5F, 5F);
      shinUpper1Left.setTextureSize(64, 32);
      shinUpper1Left.mirror = true;
      setRotation(shinUpper1Left, 1.658063F, 0F, 0F);
      shipUpper2Left = new ModelRenderer(this, 40, 17);
      shipUpper2Left.addBox(-1F, 1.6F, -3.8F, 1, 2, 1);
      shipUpper2Left.setRotationPoint(3F, 18.5F, 5F);
      shipUpper2Left.setTextureSize(64, 32);
      shipUpper2Left.mirror = true;
      setRotation(shipUpper2Left, 1.658063F, 0F, 0F);
      shinLowerLeft = new ModelRenderer(this, 44, 17);
      shinLowerLeft.addBox(-1F, 1.9F, 3.8F, 1, 2, 1);
      shinLowerLeft.setRotationPoint(3F, 18.5F, 5F);
      shinLowerLeft.setTextureSize(64, 32);
      shinLowerLeft.mirror = true;
      setRotation(shinLowerLeft, -0.3490659F, 0F, 0F);
      footLeft = new ModelRenderer(this, 48, 14);
      footLeft.addBox(-1F, 4.5F, 1.2F, 1, 1, 2);
      footLeft.setRotationPoint(3F, 18.5F, 5F);
      footLeft.setTextureSize(64, 32);
      footLeft.mirror = true;
      setRotation(footLeft, 0F, 0F, 0F);
      tail1 = new ModelRenderer(this, 0, 16);
      tail1.addBox(-1F, -1F, -1F, 2, 4, 2);
      tail1.setRotationPoint(0F, 16F, 7F);
      tail1.setTextureSize(64, 32);
      tail1.mirror = true;
      setRotation(tail1, 1.047198F, 0F, 0F);
      tail2 = new ModelRenderer(this, 0, 22);
      tail2.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
      tail2.setRotationPoint(0F, 17.25F, 9.165F);
      tail2.setTextureSize(64, 32);
      tail2.mirror = true;
      setRotation(tail2, 0.6981317F, 0F, 0F);
      earRight = new ModelRenderer(this, 10, 16);
      earRight.addBox(-1F, -2F, 0F, 2, 2, 0);
      earRight.setRotationPoint(-2.2F, 14F, -4F);
      earRight.setTextureSize(64, 32);
      earRight.mirror = true;
      setRotation(earRight, -0.2792527F, 0.5235988F, -1.117011F);
      earLeft = new ModelRenderer(this, 12, 16);
      earLeft.addBox(-1F, -2F, 0F, 2, 2, 0);
      earLeft.setRotationPoint(2.2F, 14F, -4F);
      earLeft.setTextureSize(64, 32);
      earLeft.mirror = true;
      setRotation(earLeft, -0.2792527F, -0.5235988F, 1.117011F);
      tail3 = new ModelRenderer(this, 0, 22);
      tail3.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
      tail3.setRotationPoint(0F, 20.7F, 12.05F);
      tail3.setTextureSize(64, 32);
      tail3.mirror = true;
      setRotation(tail3, 1.48353F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    corpus.render(f5);
    head.render(f5);
    nose.render(f5);
    shoulderRight.render(f5);
    handRight.render(f5);
    forearmRight.render(f5);
    shoulderLeft.render(f5);
    forearmLeft.render(f5);
    handLeft.render(f5);
    hipRight.render(f5);
    shinUpper1Right.render(f5);
    shinLowerRight.render(f5);
    shipUpper2Right.render(f5);
    footRight.render(f5);
    hipLeft.render(f5);
    shinUpper1Left.render(f5);
    shipUpper2Left.render(f5);
    shinLowerLeft.render(f5);
    footLeft.render(f5);
    tail1.render(f5);
    tail2.render(f5);
    earRight.render(f5);
    earLeft.render(f5);
    tail3.render(f5);
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

}*/
