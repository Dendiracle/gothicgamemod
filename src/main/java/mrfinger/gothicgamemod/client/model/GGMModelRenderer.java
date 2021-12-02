package mrfinger.gothicgamemod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ModelRenderer.class)
public class GGMModelRenderer extends ModelRenderer
{

    public static final float greedRadRatio = 180.0F / (float) Math.PI;

    //public List<IModelShape> shapeList;

    public float defaultRotationPointX;
    public float defaultRotationPointY;
    public float defaultRotationPointZ;

    public float defaultRotateAngleX;
    public float defaultRotateAngleY;
    public float defaultRotateAngleZ;


    public GGMModelRenderer(ModelBase p_i1172_1_, String p_i1172_2_)
    {
        super(p_i1172_1_, p_i1172_2_);
    }

    public GGMModelRenderer(ModelBase p_i1173_1_)
    {
        super(p_i1173_1_);
    }

    public GGMModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_)
    {
        super(p_i1174_1_, p_i1174_2_, p_i1174_3_);
    }

    @Override
    public void setRotationPoint(float x, float y, float z)
    {
        this.defaultRotationPointX = x;
        this.defaultRotationPointY = y;
        this.defaultRotationPointZ = z;

        super.setRotationPoint(x, y, z);
    }


    public void addChild(ModelRenderer child0)
    {
        GGMModelRenderer child = (GGMModelRenderer) child0;
        child.defaultRotationPointX -= this.defaultRotationPointX;
        child.defaultRotationPointY -= this.defaultRotationPointY;
        child.defaultRotationPointZ -= this.defaultRotationPointZ;

        /*float cosX = MathHelper.cos(child.rotateAngleX);
        float cosY = MathHelper.cos(child.rotateAngleY);
        float cosZ = MathHelper.cos(child.rotateAngleZ);

        if (this.defaultRotateAngleX != 0.0F)
        {
            //child.defaultRotateAngleX -= this.defaultRotateAngleX;// * cosY * cosZ;
            //child.rotateAngleX = child.defaultRotateAngleX;

            System.out.println("Debug in GGMModelRenderer " + child.boxName + " " + child.rotateAngleX + " " + cosY + " " + cosZ);

            if (child.defaultRotationPointY != 0.0F || child.defaultRotationPointZ != 0.0F )
            {
                float f0 = Math.abs(this.defaultRotateAngleX);
                float f1 = child.defaultRotationPointY * child.defaultRotationPointY + child.defaultRotationPointZ * child.defaultRotationPointZ;
                float f2 = MathHelper.sqrt_float(2.0F * (f1 - f1 * MathHelper.cos(f0)));
                {
                    float rpY = Math.abs(child.defaultRotationPointY);
                    float angleY = (float) (0.5D * (f0 + Math.PI) - Math.asin(rpY / Math.sqrt(f1)));
                    f0 = f2 * MathHelper.sin(angleY);
                    f1 = f2 * MathHelper.cos(angleY);
                }

                if (this.defaultRotateAngleX > 0.0F)
                {
                    f2 = child.defaultRotationPointY;

                    if (child.defaultRotationPointZ <= 0.0F)
                    {
                        child.defaultRotationPointY -= f0;
                    }
                    else
                    {
                        child.defaultRotationPointY += f0;
                    }

                    if (f2 <= 0.0F)
                    {
                        child.defaultRotationPointZ += f1;
                    }
                    else
                    {
                        child.defaultRotationPointZ -= f1;
                    }
                }
                else
                {
                    f2 = child.defaultRotationPointY;

                    if (child.defaultRotationPointZ <= 0.0F)
                    {
                        child.defaultRotationPointY += f0;
                    }
                    else
                    {
                        child.defaultRotationPointY -= f0;
                    }

                    if (f2 <= 0.0F)
                    {
                        child.defaultRotationPointZ -= f1;
                    }
                    else
                    {
                        child.defaultRotationPointZ += f1;
                    }
                }
            }
        }
        if (this.defaultRotateAngleY != 0.0F)
        {
            //child.defaultRotateAngleY -= this.defaultRotateAngleY * cosX * cosZ;
            //child.rotateAngleY = child.defaultRotateAngleY;

            if (child.defaultRotationPointX != 0.0F || child.defaultRotationPointZ != 0.0F )
            {
                float f0 = Math.abs(this.defaultRotateAngleY);
                float f1 = child.defaultRotationPointX * child.defaultRotationPointX + child.defaultRotationPointZ * child.defaultRotationPointZ;
                float f2 = MathHelper.sqrt_float(2.0F * (f1 - f1 * MathHelper.cos(f0)));
                {
                    float rpX = Math.abs(child.defaultRotationPointX);
                    float angleX = (float) (0.5D * (f0 + Math.PI) - Math.asin(rpX / Math.sqrt(f1)));
                    f0 = f2 * MathHelper.sin(angleX);
                    f1 = f2 * MathHelper.cos(angleX);
                }

                if (this.defaultRotateAngleY > 0.0F)
                {
                    f2 = child.defaultRotationPointZ;

                    if (child.defaultRotationPointX <= 0.0F)
                    {
                        child.defaultRotationPointZ -= f0;
                    }
                    else
                    {
                        child.defaultRotationPointZ += f0;
                    }

                    if (f2 <= 0.0F)
                    {
                        child.defaultRotationPointX += f1;
                    }
                    else
                    {
                        child.defaultRotationPointX -= f1;
                    }
                }
                else
                {
                    f2 = child.defaultRotationPointZ;

                    if (child.defaultRotationPointX <= 0.0F)
                    {
                        child.defaultRotationPointZ += f0;
                    }
                    else
                    {
                        child.defaultRotationPointZ -= f0;
                    }

                    if (f2 <= 0.0F)
                    {
                        child.defaultRotationPointX -= f1;
                    }
                    else
                    {
                        child.defaultRotationPointX += f1;
                    }
                }
            }
        }
        if (this.defaultRotateAngleZ != 0.0F)
        {
            //child.defaultRotateAngleZ -= this.defaultRotateAngleZ * cosX * cosY;
            //child.rotateAngleZ = child.defaultRotateAngleZ;

            if (child.defaultRotationPointX != 0.0F || child.defaultRotationPointY != 0.0F )
            {
                float f0 = Math.abs(this.defaultRotateAngleZ);
                float f1 = child.defaultRotationPointX * child.defaultRotationPointX + child.defaultRotationPointY * child.defaultRotationPointY;
                float f2 =  MathHelper.sqrt_float(2.0F * (f1 - f1 * MathHelper.cos(f0)));
                {
                    float rpX = Math.abs(child.defaultRotationPointX);
                    float angleX = (float) (0.5D * (f0 + Math.PI) - Math.asin(rpX / Math.sqrt(f1)));
                    f0 = f2 * MathHelper.sin(angleX);
                    f1 = f2 * MathHelper.cos(angleX);
                }

                if (this.defaultRotateAngleZ > 0.0F)
                {
                    f2 = child.defaultRotationPointY;

                    if (child.defaultRotationPointX <= 0.0F)
                    {
                        child.defaultRotationPointY += f0;
                    }
                    else
                    {
                        child.defaultRotationPointY -= f0;
                    }

                    if (f2 <= 0.0F)
                    {
                        child.defaultRotationPointX -= f1;
                    }
                    else
                    {
                        child.defaultRotationPointX += f1;
                    }
                }
                else
                {
                    f2 = child.defaultRotationPointY;

                    if (child.defaultRotationPointX <= 0.0F)
                    {
                        child.defaultRotationPointY -= f0;
                    }
                    else
                    {
                        child.defaultRotationPointY += f0;
                    }

                    if (f2 <= 0.0F)
                    {
                        child.defaultRotationPointX += f1;
                    }
                    else
                    {
                        child.defaultRotationPointX -= f1;
                    }
                }
            }
        }
*/
        super.addChild(child);

        child.rotationPointX = child.defaultRotationPointX;
        child.rotationPointY = child.defaultRotationPointY;
        child.rotationPointZ = child.defaultRotationPointZ;
    }

    @SideOnly(Side.CLIENT)
    public void renderWithRotation(float p_78791_1_)
    {
        if (!this.isHidden)
        {
            if (this.showModel)
            {
                if (!((IModelRenderer) this).getCompiled())
                {
                    ((IModelRenderer) this).invokerCompileDisplayList(p_78791_1_);
                }

                GL11.glPushMatrix();
                GL11.glTranslatef(this.rotationPointX * p_78791_1_, this.rotationPointY * p_78791_1_, this.rotationPointZ * p_78791_1_);


                if (this.rotateAngleY != 0.0F)
                {
                    GL11.glRotatef(this.rotateAngleY * greedRadRatio, 0.0F, 1.0F, 0.0F);
                }

                if (this.rotateAngleX != 0.0F)
                {
                    GL11.glRotatef(this.rotateAngleX * greedRadRatio, 1.0F, 0.0F, 0.0F);
                }

                if (this.rotateAngleZ != 0.0F)
                {
                    GL11.glRotatef(this.rotateAngleZ * greedRadRatio, 0.0F, 0.0F, 1.0F);
                }

                GL11.glCallList(((IModelRenderer) this).getDisplayList());


                if (this.defaultRotateAngleZ != 0.0F)
                {
                    GL11.glRotatef(-this.defaultRotateAngleZ * greedRadRatio, 0.0F, 0.0F, 1.0F);
                }

                if (this.defaultRotateAngleX != 0.0F)
                {
                    GL11.glRotatef(-this.defaultRotateAngleX * greedRadRatio, 1.0F, 0.0F, 0.0F);
                }

                if (this.defaultRotateAngleY != 0.0F)
                {
                    GL11.glRotatef(-this.defaultRotateAngleY * greedRadRatio, 0.0F, 1.0F, 0.0F);
                }

                if (this.childModels != null)
                {
                    for (Object childModel : this.childModels)
                    {
                        ((GGMModelRenderer) childModel).renderWithRotation(p_78791_1_);
                    }
                }

                GL11.glPopMatrix();
            }
        }
    }


    public void normalizeAngles()
    {
        this.rotateAngleX = this.defaultRotateAngleX;
        this.rotateAngleY = this.defaultRotateAngleY;
        this.rotateAngleZ = this.defaultRotateAngleZ;

        this.rotationPointX = this.defaultRotationPointX;
        this.rotationPointY = this.defaultRotationPointY;
        this.rotationPointZ = this.defaultRotationPointZ;

        if (this.childModels != null)
        {
            for (Object o : this.childModels)
            {
                ((GGMModelRenderer) o).normalizeAngles();
            }
        }
    }


    @Override
    public String toString()
    {
        return this.boxName + '\t' + this.defaultRotationPointX + '\t' + this.defaultRotationPointY + '\t' + this.defaultRotationPointZ + '\t' + this.defaultRotateAngleX + '\t' + this.defaultRotateAngleY + '\t' + this.defaultRotateAngleZ + '\t' + this.rotateAngleX + '\t' + this.rotateAngleY + '\t' + this.rotateAngleZ;
    }


    /*public void addQuad(int textureOffsetX, int textureOffsetY, int textureSizeX, int textureSizeY, float posX1, float posY1, float posZ1, float posX2, float posY2, float posZ2, float posX3, float posY3, float posZ3)
    {
        this.shapeList.add(new ModelQuad(this, textureOffsetX, textureOffsetY, textureSizeX, textureSizeY, posX1, posY1, posZ1, posX2, posY2, posZ2, posX3, posY3, posZ3));
    }



    @SideOnly(Side.CLIENT)
    @Override
    protected void compileDisplayList(float p_78788_1_)
    {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.displayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.instance;

        for (int i = 0; i < this.cubeList.size(); ++i)
        {
            ((ModelBox)this.cubeList.get(i)).render(tessellator, p_78788_1_);
        }

        for (int i = 0; i < this.shapeList.size(); ++i)
        {
            this.shapeList.get(i).render(tessellator, p_78788_1_);
        }

        GL11.glEndList();
        this.compiled = true;
    }*/
}
