package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GiantRatModel extends ModelAnimal {
	
	private float tail1X = 1.047198F,
				  tail2X = 0.6981317F,
				  tail3X = 1.48353F;
	
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
    ModelRenderer shinUpper2Right;
    ModelRenderer footRight;
    ModelRenderer hipLeft;
    ModelRenderer shinUpper1Left;
    ModelRenderer shinUpper2Left;
    ModelRenderer shinLowerLeft;
    ModelRenderer footLeft;
    ModelRenderer tail1;
    ModelRenderer tail2;
    ModelRenderer tail3;
    ModelRenderer earRight;
    ModelRenderer earLeft;
  
  public GiantRatModel() {
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
      forearmRight = new ModelRenderer(this, 40, 9);
      forearmRight.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
      forearmRight.setRotationPoint(-2.5F, 21F, -0.8F);
      forearmRight.setTextureSize(64, 32);
      forearmRight.mirror = true;
      setRotation(forearmRight, -0.5585054F, 0F, 0F);
      handRight = new ModelRenderer(this, 44, 9);
      handRight.addBox(-0.5F, 0F, -2F, 1, 1, 2);
      handRight.setRotationPoint(-2.5F, 23F, -2F);
      handRight.setTextureSize(64, 32);
      handRight.mirror = true;
      setRotation(handRight, 0F, 0F, 0F);
      
      shoulderLeft = new ModelRenderer(this, 32, 9);
      shoulderLeft.addBox(-1.5F, 0F, -1F, 2, 3, 2);
      shoulderLeft.setRotationPoint(3F, 18.5F, -1F);
      shoulderLeft.setTextureSize(64, 32);
      shoulderLeft.mirror = true;
      setRotation(shoulderLeft, 0F, 0F, 0F);
      forearmLeft = new ModelRenderer(this, 40, 9);
      forearmLeft.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
      forearmLeft.setRotationPoint(2.5F, 21F, -0.8F);
      forearmLeft.setTextureSize(64, 32);
      forearmLeft.mirror = true;
      setRotation(forearmLeft, -0.5585054F, 0F, 0F);
      handLeft = new ModelRenderer(this, 44, 9);
      handLeft.addBox(-0.5F, 0F, -2F, 1, 1, 2);
      handLeft.setRotationPoint(2.5F, 23F, -2F);
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
      shinUpper1Right.addBox(-0.999F, 0F, -1F, 2, 2, 2);
      shinUpper1Right.setRotationPoint(-2.5F, 21.5F, 5F);
      shinUpper1Right.setTextureSize(64, 32);
      shinUpper1Right.mirror = true;
      setRotation(shinUpper1Right, 1.658063F, 0F, 0F);
      shinUpper2Right = new ModelRenderer(this, 40, 18);
      shinUpper2Right.addBox(-0.5F, 1.9F, -0.7F, 1, 2, 1);
      shinUpper2Right.setRotationPoint(-2.5F, 21.5F, 5F);
      shinUpper2Right.setTextureSize(64, 32);
      shinUpper2Right.mirror = true;
      setRotation(shinUpper2Right, 1.658063F, 0F, 0F);
      shinLowerRight = new ModelRenderer(this, 44, 18);
      shinLowerRight.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
      shinLowerRight.setRotationPoint(-2.5F, 21.4F, 8.49F);
      shinLowerRight.setTextureSize(64, 32);
      shinLowerRight.mirror = true;
      setRotation(shinLowerRight, -0.3490659F, 0F, 0F);      
      footRight = new ModelRenderer(this, 48, 14);
      footRight.addBox(-0.5F, 0F, -2F, 1, 1, 2);
      footRight.setRotationPoint(-2.5F, 23F, 8.3F);
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
      shinUpper1Left.addBox(-1.001F, 0F, -1F, 2, 2, 2);
      shinUpper1Left.setRotationPoint(2.5F, 21.5F, 5F);
      shinUpper1Left.setTextureSize(64, 32);
      shinUpper1Left.mirror = true;
      setRotation(shinUpper1Left, 1.658063F, 0F, 0F);
      shinUpper2Left = new ModelRenderer(this, 40, 18);
      shinUpper2Left.addBox(-0.5F, 1.9F, -0.7F, 1, 2, 1);
      shinUpper2Left.setRotationPoint(2.5F, 21.5F, 5F);
      shinUpper2Left.setTextureSize(64, 32);
      shinUpper2Left.mirror = true;
      setRotation(shinUpper2Left, 1.658063F, 0F, 0F);
      shinLowerLeft = new ModelRenderer(this, 44, 18);
      shinLowerLeft.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
      shinLowerLeft.setRotationPoint(2.5F, 21.4F, 8.5F);
      shinLowerLeft.setTextureSize(64, 32);
      shinLowerLeft.mirror = true;
      setRotation(shinLowerLeft, -0.3490659F, 0F, 0F);      
      footLeft = new ModelRenderer(this, 48, 14);
      footLeft.addBox(-0.5F, 0F, -2F, 1, 1, 2);
      footLeft.setRotationPoint(2.5F, 23F, 8.3F);
      footLeft.setTextureSize(64, 32);
      footLeft.mirror = true;
      setRotation(footLeft, 0F, 0F, 0F);
      
      tail1 = new ModelRenderer(this, 0, 16);
      tail1.addBox(-1F, -1F, -1F, 2, 4, 2);
      tail1.setRotationPoint(0F, 16F, 7F);
      tail1.setTextureSize(64, 32);
      tail1.mirror = true;
      setRotation(tail1, this.tail1X, 0F, 0F);
      tail2 = new ModelRenderer(this, 0, 22);
      tail2.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
      tail2.setRotationPoint(0F, 17.25F, 9.165F);
      tail2.setTextureSize(64, 32);
      tail2.mirror = true;
      setRotation(tail2, this.tail3X, 0F, 0F);
      tail3 = new ModelRenderer(this, 0, 22);
      tail3.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
      tail3.setRotationPoint(0F, 20.697F, 12.055F);
      tail3.setTextureSize(64, 32);
      tail3.mirror = true;
      setRotation(tail3, this.tail2X, 0F, 0F);
      
      earRight = new ModelRenderer(this, 10, 16);
      earRight.addBox(-1F, -2F, 0F, 2, 2, 0);
      earRight.setRotationPoint(-2.2F, 14F, -4F);
      earRight.setTextureSize(64, 32);
      earRight.mirror = true;
      setRotation(earRight, -0.2792527F, 0.5235988F, -1.117011F);
      earLeft = new ModelRenderer(this, 14, 16);
      earLeft.addBox(-1F, -2F, 0F, 2, 2, 0);
      earLeft.setRotationPoint(2.2F, 14F, -4F);
      earLeft.setTextureSize(64, 32);
      earLeft.mirror = true;
      setRotation(earLeft, -0.2792527F, -0.5235988F, 1.117011F);
      
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
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
    shinUpper2Right.render(f5);
    footRight.render(f5);
    hipLeft.render(f5);
    shinUpper1Left.render(f5);
    shinUpper2Left.render(f5);
    shinLowerLeft.render(f5);
    footLeft.render(f5);
    tail1.render(f5);
    tail2.render(f5);
    tail3.render(f5);
    earRight.render(f5);
    earLeft.render(f5);
  }
  
  	private void setRotation(ModelRenderer model, float x, float y, float z) {
  		model.rotateAngleX = x;
  		model.rotateAngleY = y;
  		model.rotateAngleZ = z;
  	}
  
  	@Override
  	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
	  super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    
	  float rightLegRotX = (MathHelper.cos(f * 0.6662F) *1.4F * f1);
	  float leftLegRotX = (MathHelper.cos(f * 0.6662F + (float)Math.PI) *1.4F * f1);
	  float tailRotX = (MathHelper.cos(f * 0.6662F) * f1 / 2);
	  
	  this.shoulderRight.rotateAngleX = leftLegRotX;
	  this.attachX(this.shoulderRight, this.forearmRight, 2.508F, 0.479F, 1.092F);
	  this.forearmRight.rotateAngleX = -0.5585054F + leftLegRotX;
	  this.attachX(this.forearmRight, this.handRight, 2.332F, 0.018F, 1.553F);
	  
	  this.shoulderLeft.rotateAngleX = rightLegRotX;
	  this.attachX(this.shoulderLeft, this.forearmLeft, 2.508F, 0.479F, 1.092F);
	  this.forearmLeft.rotateAngleX = -0.5585054F + rightLegRotX;
	  this.attachX(this.forearmLeft, this.handLeft, 2.332F, 0.018F, 1.553F);
	  
	  this.hipRight.rotateAngleX = rightLegRotX;
	  this.attachX(this.hipRight, this.shinUpper1Right, 3F, 0F, 1.570796F);	  
	  this.shinUpper1Right.rotateAngleX = 1.658063F;
	  this.attachX(this.hipRight, this.shinUpper2Right, 3F, 0F, 1.570796F);  
	  this.shinUpper2Right.rotateAngleX = 1.658063F;
	  this.attachX(this.shinUpper2Right, this.shinLowerRight, 3.49F, -0.116F, 1.687F);
	  this.shinLowerRight.rotateAngleX = -0.3490659F + rightLegRotX;
	  this.attachX(this.shinLowerRight, this.footRight, 1.61F, 0.23F, 1.34F);
	  
	  this.hipLeft.rotateAngleX = leftLegRotX;
	  this.attachX(this.hipLeft, this.shinUpper1Left, 3F, 0F, 1.570796F); ;	  
	  this.shinUpper1Left.rotateAngleX = 1.658063F;
	  this.attachX(this.hipLeft, this.shinUpper2Left, 3F, 0F, 1.570796F);   
	  this.shinUpper2Left.rotateAngleX = 1.658063F;
	  this.attachX(this.shinUpper2Left, this.shinLowerLeft, 3.49F, -0.116F, 1.687F);
	  this.shinLowerLeft.rotateAngleX = -0.3490659F + leftLegRotX;
	  this.attachX(this.shinLowerLeft, this.footLeft, 1.61F, 0.23F, 1.34F);
    
	  this.tail1.rotateAngleX = 1.047198F + f1;
	  this.attachX(this.tail1, this.tail2, 2.5F, 0F, 1.571F);
	  this.tail2.rotateAngleX = this.tail2X + f1 * 2.5F + tailRotX;
	  this.attachX(this.tail2, this.tail3, 4.498F, 0F, 1.571F);
	  this.tail3.rotateAngleX = this.tail3X + f1 * 2F + tailRotX;
	  
    
  }
  	
  	/*this.hipRight.rotateAngleX = rightLegRotX;
	  this.attachX(this.hipRight, this.shinUpper1Right, 3F, 1F);	  
	  this.shinUpper1Right.rotateAngleX = 1.658063F + leftLegRotX;
	  this.attachX(this.hipRight, this.shinUpper2Right, 3F, 1F);	  
	  this.shinUpper2Right.rotateAngleX = 1.658063F + leftLegRotX;
	  this.attachX(this.shinUpper2Right, this.shinLowerRight, 2.35F, -0.3F);
	  this.shinLowerRight.rotateAngleX = -0.3490659F + rightLegRotX;
	  this.attachX(this.shinLowerRight, this.footRight, 1.62F, 0.18F);*/
  	
  	//this.shinUpper2Right.rotationPointY = this.hipsPosY + (float) (this.shinsArtLongFromHipsArt * Math.sin(Math.PI / 2 - Math.asin((1 / this.shinsArtLongFromHipsArt) + rightLegRotX)));
  	//this.shinUpper2Right.rotationPointZ = this.hipsPosZ + (float) (this.shinsArtLongFromHipsArt * Math.sin(Math.asin((1 / this.shinsArtLongFromHipsArt) + rightLegRotX)));
  	//this.shinUpper1Right.rotationPointY = this.hipsPosY + (float) (this.shinsArtLongFromHipsArt * Math.sin(Math.PI / 2 - Math.asin((1 / this.shinsArtLongFromHipsArt) + rightLegRotX)));
	//this.shinUpper1Right.rotationPointZ = this.hipsPosZ + (float) (this.shinsArtLongFromHipsArt * Math.sin(Math.asin((1 / this.shinsArtLongFromHipsArt) + rightLegRotX)));

}
