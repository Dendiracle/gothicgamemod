package mrfinger.gothicgamemod.mixin.client.entity;

import mrfinger.gothicgamemod.entity.capability.attribute.IGGMAttributeModifier;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class GGMEntityPlayerSP extends GGMAbstractClientPlayer
{

    IGGMAttributeModifier bonusModifer;


    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Minecraft minecraft, World world, Session session, int i, CallbackInfo ci)
    {
        this.bonusModifer = (IGGMAttributeModifier) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(GGMCapabilities.dexterity.getUUID());
    }

    @Redirect(method = "getFOVMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerCapabilities;getWalkSpeed()F", ordinal = 0))
    private float fixGettingFovMiltiplier(PlayerCapabilities capabilities)
    {
        return capabilities.getWalkSpeed() + (float) this.bonusModifer.getAmount();
    }


}



