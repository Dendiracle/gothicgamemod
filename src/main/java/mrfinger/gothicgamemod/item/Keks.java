package mrfinger.gothicgamemod.item;

import cpw.mods.fml.common.Mod.EventHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.UUID;

public class Keks extends Item{
	
	
	@EventHandler
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier(UUID.randomUUID(), "bonusFomStat", 100F, 1));
		
		return item;		
	}
}
