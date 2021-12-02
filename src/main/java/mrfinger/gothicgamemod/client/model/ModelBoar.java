

package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoar extends ModelAnimal
{

    GGMModelRenderer corpusBack;
    GGMModelRenderer corpus;
    GGMModelRenderer corpusCrest;
    GGMModelRenderer headCorpus;
    GGMModelRenderer maxilla;
    GGMModelRenderer mandibula;
    GGMModelRenderer hornBaseRight;
    GGMModelRenderer hornTipRight;
    GGMModelRenderer hornBaseLeft;
    GGMModelRenderer hornTipLeft;
    GGMModelRenderer hip1Right;
    GGMModelRenderer hip2Right;
    GGMModelRenderer shinRight;
    GGMModelRenderer hoofBackRight;
    GGMModelRenderer hip1Left;
    GGMModelRenderer hip2Left;
    GGMModelRenderer shinLeft;
    GGMModelRenderer hoofBackLeft;
    GGMModelRenderer shoulderRight;
    GGMModelRenderer forearmRight;
    GGMModelRenderer hoofFrontRight;
    GGMModelRenderer shoulderLeft;
    GGMModelRenderer forearmLeft;
    GGMModelRenderer hoofFrontLeft;
    GGMModelRenderer tail;
  
  public ModelBoar()
  {
      textureWidth = 64;
      textureHeight = 32;
    
      corpusBack = new GGMModelRenderer(this, 25, 0);
      corpusBack.addBox(-3F, -4.5F, -2F, 6, 7, 4);
      corpusBack.setRotationPoint(0F, 16F, 6F);
      corpusBack.setTextureSize(64, 32);
      corpusBack.mirror = true;
      setRotation(corpusBack, 0.4014257F, 0F, 0F);
      corpus = new GGMModelRenderer(this, 0, 0);
      corpus.addBox(-3.5F, -5F, -11F, 7, 8, 11);
      corpus.setRotationPoint(0F, 16F, 6F);
      corpus.setTextureSize(64, 32);
      corpus.mirror = true;
      setRotation(corpus, 0F, 0F, 0F);
      corpusCrest = new GGMModelRenderer(this, 24, 11);
      corpusCrest.addBox(-2F, -6F, -12F, 4, 2, 12);
      corpusCrest.setRotationPoint(0F, 16F, 5.5F);
      corpusCrest.setTextureSize(64, 32);
      corpusCrest.mirror = true;
      setRotation(corpusCrest, -0.0349066F, 0F, 0F);
      headCorpus = new GGMModelRenderer(this, 45, 0);
      headCorpus.addBox(-2.5F, -2.6F, -3F, 5, 7, 4);
      headCorpus.setRotationPoint(0F, 14F, -4F);
      headCorpus.setTextureSize(64, 32);
      headCorpus.mirror = true;
      setRotation(headCorpus, -0.1396263F, 0F, 0F);
      maxilla = new GGMModelRenderer(this, 44, 18);
      maxilla.addBox(-1.5F, -0.5F, -5.5F, 3, 2, 3);
      maxilla.setRotationPoint(0F, 14F, -4F);
      maxilla.setTextureSize(64, 32);
      maxilla.mirror = true;
      setRotation(maxilla, 0F, 0F, 0F);
      mandibula = new GGMModelRenderer(this, 26, 19);
      mandibula.addBox(-1F, 0F, -2.4F, 2, 1, 3);
      mandibula.setRotationPoint(0F, 15.5F, -6.8F);
      mandibula.setTextureSize(64, 32);
      mandibula.mirror = true;
      setRotation(mandibula, 0F, 0F, 0F);
      hornBaseRight = new GGMModelRenderer(this, 0, 19);
      hornBaseRight.addBox(1F, 0.9333333F, -6.8F, 1, 1, 4);
      hornBaseRight.setRotationPoint(0F, 14F, -4F);
      hornBaseRight.setTextureSize(64, 32);
      hornBaseRight.mirror = true;
      setRotation(hornBaseRight, -0.122173F, 0.7853982F, 0F);
      hornTipLeft = new GGMModelRenderer(this, 5, 19);
      hornTipLeft.addBox(-2.5F, 5.6F, -7.5F, 1, 1, 5);
      hornTipLeft.setRotationPoint(0F, 14F, -4F);
      hornTipLeft.setTextureSize(64, 32);
      hornTipLeft.mirror = true;
      setRotation(hornTipLeft, -1.047198F, 0.2617994F, 0F);
      hornBaseLeft = new GGMModelRenderer(this, 0, 19);
      hornBaseLeft.addBox(-2F, 0.9F, -6.8F, 1, 1, 4);
      hornBaseLeft.setRotationPoint(0F, 14F, -4F);
      hornBaseLeft.setTextureSize(64, 32);
      hornBaseLeft.mirror = true;
      setRotation(hornBaseLeft, -0.122173F, -0.7853982F, 0F);
      hornTipLeft = new GGMModelRenderer(this, 5, 19);
      hornTipLeft.addBox(1.5F, 5.6F, -7.5F, 1, 1, 5);
      hornTipLeft.setRotationPoint(0F, 14F, -4F);
      hornTipLeft.setTextureSize(64, 32);
      hornTipLeft.mirror = true;
      setRotation(hornTipLeft, -1.047198F, -0.2617994F, 0F);
      hip1Right = new GGMModelRenderer(this, 0, 24);
      hip1Right.addBox(-1F, 0F, -0.5F, 1, 3, 2);
      hip1Right.setRotationPoint(-3F, 16F, 6F);
      hip1Right.setTextureSize(64, 32);
      hip1Right.mirror = true;
      setRotation(hip1Right, 0.2617994F, 0F, 0F);
      hip2Right = new GGMModelRenderer(this, 5, 25);
      hip2Right.addBox(-1F, 3F, -0.7F, 1, 3, 1);
      hip2Right.setRotationPoint(-3F, 16F, 6F);
      hip2Right.setTextureSize(64, 32);
      hip2Right.mirror = true;
      setRotation(hip2Right, 0.6283185F, 0F, 0.0174533F);
      shinRight = new GGMModelRenderer(this, 10, 25);
      shinRight.addBox(-0.5F, 0F, -0.3F, 1, 3, 1);
      shinRight.setRotationPoint(-3.6F, 20.8F, 9F);
      shinRight.setTextureSize(64, 32);
      shinRight.mirror = true;
      setRotation(shinRight, 0.1047198F, 0F, 0F);
      hoofBackRight = new GGMModelRenderer(this, 0, 29);
      hoofBackRight.addBox(-0.5F, 2.1F, 0.2F, 1, 1, 1);
      hoofBackRight.setRotationPoint(-3.6F, 20.8F, 9F);
      hoofBackRight.setTextureSize(64, 32);
      hoofBackRight.mirror = true;
      setRotation(hoofBackRight, -0.1047198F, 0F, 0.0174533F);
      hip1Left = new GGMModelRenderer(this, 0, 24);
      hip1Left.addBox(0F, 0F, -0.5F, 1, 3, 2);
      hip1Left.setRotationPoint(3F, 16F, 6F);
      hip1Left.setTextureSize(64, 32);
      hip1Left.mirror = true;
      setRotation(hip1Left, 0.2617994F, 0F, 0F);
      hip2Left = new GGMModelRenderer(this, 6, 25);
      hip2Left.addBox(0F, 3F, -0.7F, 1, 3, 1);
      hip2Left.setRotationPoint(3F, 16F, 6F);
      hip2Left.setTextureSize(64, 32);
      hip2Left.mirror = true;
      setRotation(hip2Left, 0.6283185F, 0F, -0.0174533F);
      shinLeft = new GGMModelRenderer(this, 10, 25);
      shinLeft.addBox(-0.5F, 0F, -0.3F, 1, 3, 1);
      shinLeft.setRotationPoint(3.6F, 20.8F, 9F);
      shinLeft.setTextureSize(64, 32);
      shinLeft.mirror = true;
      setRotation(shinLeft, 0.1047198F, 0F, 0F);
      hoofBackLeft = new GGMModelRenderer(this, 0, 29);
      hoofBackLeft.addBox(-0.5F, 2.1F, 0.2F, 1, 1, 1);
      hoofBackLeft.setRotationPoint(3.6F, 20.8F, 9F);
      hoofBackLeft.setTextureSize(64, 32);
      hoofBackLeft.mirror = true;
      setRotation(hoofBackLeft, -0.1047198F, 0F, 0.0174533F);
      shoulderRight = new GGMModelRenderer(this, 17, 19);
      shoulderRight.addBox(-1F, 0F, -1F, 1, 4, 2);
      shoulderRight.setRotationPoint(-3.5F, 16F, -2F);
      shoulderRight.setTextureSize(64, 32);
      shoulderRight.mirror = true;
      setRotation(shoulderRight, 0F, 0F, 0F);
      forearmRight = new GGMModelRenderer(this, 12, 19);
      forearmRight.addBox(-0.5F, -0.2F, -0.5F, 1, 4, 1);
      forearmRight.setRotationPoint(-4F, 20F, -2F);
      forearmRight.setTextureSize(64, 32);
      forearmRight.mirror = true;
      setRotation(forearmRight, -0.1570796F, 0F, 0F);
      hoofFrontRight = new GGMModelRenderer(this, 4, 29);
      hoofFrontRight.addBox(-0.5F, 3.2F, 0F, 1, 1, 1);
      hoofFrontRight.setRotationPoint(-4F, 20F, -2F);
      hoofFrontRight.setTextureSize(64, 32);
      hoofFrontRight.mirror = true;
      setRotation(hoofFrontRight, -0.3490659F, 0F, 0.0174533F);
      shoulderLeft = new GGMModelRenderer(this, 17, 19);
      shoulderLeft.addBox(0F, 0F, -1F, 1, 4, 2);
      shoulderLeft.setRotationPoint(3.5F, 16F, -2F);
      shoulderLeft.setTextureSize(64, 32);
      shoulderLeft.mirror = true;
      setRotation(shoulderLeft, 0F, 0F, 0F);
      forearmLeft = new GGMModelRenderer(this, 12, 19);
      forearmLeft.addBox(-0.5F, -0.2F, -0.5F, 1, 4, 1);
      forearmLeft.setRotationPoint(4F, 20F, -2F);
      forearmLeft.setTextureSize(64, 32);
      forearmLeft.mirror = true;
      setRotation(forearmLeft, -0.1570796F, 0F, 0F);
      hoofFrontLeft = new GGMModelRenderer(this, 4, 29);
      hoofFrontLeft.addBox(-0.5F, 3.2F, 0F, 1, 1, 1);
      hoofFrontLeft.setRotationPoint(4F, 20F, -2F);
      hoofFrontLeft.setTextureSize(64, 32);
      hoofFrontLeft.mirror = true;
      setRotation(hoofFrontLeft, -0.3490659F, 0F, -0.0174533F);
      tail = new GGMModelRenderer(this, 44, 11);
      tail.addBox(-0.5F, 0F, 0F, 1, 4, 1);
      tail.setRotationPoint(0F, 16F, 8F);
      tail.setTextureSize(64, 32);
      tail.mirror = true;
      setRotation(tail, 0.4014257F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    corpusBack.render(f5);
    corpus.render(f5);
    corpusCrest.render(f5);
    headCorpus.render(f5);
    maxilla.render(f5);
    mandibula.render(f5);
    hornBaseRight.render(f5);
    hornTipLeft.render(f5);
    hornBaseLeft.render(f5);
    hornTipLeft.render(f5);
    hip1Right.render(f5);
    hip2Right.render(f5);
    shinRight.render(f5);
    hoofBackRight.render(f5);
    hip1Left.render(f5);
    hip2Left.render(f5);
    shinLeft.render(f5);
    hoofBackLeft.render(f5);
    shoulderRight.render(f5);
    forearmRight.render(f5);
    hoofFrontRight.render(f5);
    shoulderLeft.render(f5);
    forearmRight.render(f5);
    hoofFrontLeft.render(f5);
    tail.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }


    @Override
    public float corpusHeight()
    {
        return this.corpus.defaultRotationPointY;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
