package mrfinger.gothicgamemod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;

public class ModelQuad implements IModelShape
{
    /** The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube */
    private PositionTextureVertex[] vertexPositions;
    /** An array of 6 TexturedQuads, one for each face of a cube */
    private TexturedQuad quad;

    public String name;


    public ModelQuad(ModelRenderer modelRenderer, int textureOffsetX, int textureOffsetY, int textureSizeX, int textureSizeY, float posX1, float posY1, float posZ1, float posX2, float posY2, float posZ2, float posX3, float posY3, float posZ3)
    {
        this.vertexPositions = new PositionTextureVertex[4];
        this.vertexPositions[0] = new PositionTextureVertex(posX1, posY1, posZ1, 0.0F, 0.0F);
        this.vertexPositions[1] = new PositionTextureVertex(posX2, posY2, posZ2, 0.0F, 0.0F);
        this.vertexPositions[2] = new PositionTextureVertex(posX3, posY3, posZ3, 0.0F, 0.0F);
        this.vertexPositions[3] = new PositionTextureVertex(posX1 + posX3 - posX2, posY1 + posY3 - posY2, posZ1 + posZ3 - posZ2, 0.0F, 0.0F);

        this.quad = new TexturedQuad(new PositionTextureVertex[] {this.vertexPositions[0], this.vertexPositions[1], this.vertexPositions[2], this.vertexPositions[3]}, textureOffsetX, textureOffsetY, textureOffsetX + textureSizeX, textureOffsetY + textureSizeY, modelRenderer.textureWidth, modelRenderer.textureHeight);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void render(Tessellator p_78245_1_, float p_78245_2_)
    {
        this.quad.draw(p_78245_1_, p_78245_2_);
    }
}
