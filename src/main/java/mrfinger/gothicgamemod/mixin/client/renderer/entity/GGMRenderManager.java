package mrfinger.gothicgamemod.mixin.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.model.ModelPlayer;
import mrfinger.gothicgamemod.client.render.entity.GGMRenderPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderManager.class)
@SideOnly(Side.CLIENT)
public class GGMRenderManager
{

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/client/renderer/entity/RenderPlayer"))
    private RenderPlayer changeModel()
    {
        return new GGMRenderPlayer();
    }

}
