package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;

public class ModelPolygon implements IModelShape
{
    /** The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube */
    private PositionTextureVertex[] vertexPositions;
    /** An array of 6 TexturedQuads, one for each face of a cube */
    private TexturedQuad quad;
    /** X vertex coordinate of lower box corner */
    public final float posX1;
    /** Y vertex coordinate of lower box corner */
    public final float posY1;
    /** Z vertex coordinate of lower box corner */
    public final float posZ1;
    /** X vertex coordinate of upper box corner */
    public final float posX2;
    /** Y vertex coordinate of upper box corner */
    public final float posY2;
    /** Z vertex coordinate of upper box corner */
    public final float posZ2;
    public String name;


    public ModelPolygon(ModelRenderer modelRenderer, float posX1, float posY1, float posZ1, float posX2, float posY2, float posZ2, float f)
    {
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posZ1 = posZ1;

        float f1 = posX1 + posX2;
        float f2 = posY1 + posY2;
        float f3 = posZ1 + posZ2;

        this.posX2 = f1;
        this.posY2 = f2;
        this.posZ2 = f3;

        posX1 -= f;
        posY1 -= f;
        posZ1 -= f;
        f1 += f;
        f2 += f;
        f3 += f;

        if (modelRenderer.mirror)
        {
            float f4 = f1;
            f1 = posX1;
            posX1 = f4;
        }

        this.vertexPositions[0] = new PositionTextureVertex(posX1, posY1, posZ1, 0.0F, 0.0F);


        this.vertexPositions = new PositionTextureVertex[4];
    }
}
