package mrfinger.gothicgamemod.mixin.client.entity;

import codechicken.lib.packet.PacketCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.client.gui.GGMGuiCharachterMenu;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.client.entity.IGGMEntityClientPlayerMP;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(EntityClientPlayerMP.class)
public abstract class GGMEntityClientPlayerMP extends GGMAbstractClientPlayer implements IGGMEntityClientPlayerMP {


    public EntityClientPlayerMP thisEntity() {
        return (EntityClientPlayerMP) (Object) this;
    }
}
