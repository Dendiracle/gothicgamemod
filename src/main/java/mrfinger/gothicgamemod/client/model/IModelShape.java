package mrfinger.gothicgamemod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

public interface IModelShape
{

    @SideOnly(Side.CLIENT)
    void render(Tessellator p_78245_1_, float p_78245_2_);

}
