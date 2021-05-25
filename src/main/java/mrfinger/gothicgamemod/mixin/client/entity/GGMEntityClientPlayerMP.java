package mrfinger.gothicgamemod.mixin.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.entity.IGGMEntityClientPlayerMP;
import net.minecraft.client.entity.EntityClientPlayerMP;
import org.spongepowered.asm.mixin.Mixin;

@SideOnly(Side.CLIENT)
@Mixin(EntityClientPlayerMP.class)
public abstract class GGMEntityClientPlayerMP extends GGMEntityPlayerSP implements IGGMEntityClientPlayerMP {


    public EntityClientPlayerMP thisEntity() {
        return (EntityClientPlayerMP) (Object) this;
    }
}
