package mrfinger.gothicgamemod.client.model;

import net.minecraft.client.model.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelRenderer.class)
public interface IModelRenderer
{

    @Accessor("compiled")
    boolean getCompiled();

    @Accessor("displayList")
    int getDisplayList();


    @Invoker("compileDisplayList")
    void invokerCompileDisplayList(float p_78788_1_);

}
