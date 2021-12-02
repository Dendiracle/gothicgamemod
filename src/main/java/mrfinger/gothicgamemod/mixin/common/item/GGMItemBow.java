package mrfinger.gothicgamemod.mixin.common.item;

import mrfinger.gothicgamemod.init.GGMCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemBow.class)
public abstract class GGMItemBow {


   /* @Redirect(method = "onPlayerStoppedUsing", at = @At(value = "NEW", target = "net/minecraft/entity/projectile/EntityArrow"))
    private EntityArrow redirectArrow(EntityArrow arrow) {
        arrow.setDamage(((EntityPlayer) arrow.shootingEntity).getEntityAttribute(EntityAttributes.dexterity).getAttributeValue());
        return arrow;
    }*/

//, World world, EntityLivingBase entity, float power entity.getEntityAttribute(EntityAttributes.dexterity).getAttributeValue()


    @Redirect(method = "onPlayerStoppedUsing", at = @At(value = "NEW", target = "net/minecraft/entity/projectile/EntityArrow"))
    private EntityArrow redirectArrow(World world, EntityLivingBase entity, float power) {

        EntityArrow arrow = new EntityArrow(world, entity, power);
        arrow.setDamage(((EntityPlayer) arrow.shootingEntity).getEntityAttribute(GGMCapabilities.dexterity).getAttributeValue());
        return arrow;
    }
}
