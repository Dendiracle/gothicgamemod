package mrfinger.gothicgamemod.mixin.common.world.chunk;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chunk.class)
public class GGMChunk
{

    private static int k = 0;

    @Shadow public World worldObj;

    @Inject(method = "onChunkLoad", at = @At("HEAD"))
    private void a(CallbackInfo ci)
    {
        /*++k;
        System.out.println("DABABA " + k);
        if (this.worldObj.getTotalWorldTime() > 600) throw new IllegalArgumentException("AAAAAAAAAAAAAAAAAAA");*/
    }

}
