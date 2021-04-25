package mrfinger.gothicgamemod.item.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFireBolt extends ItemMagicCast {
	
	@Override
	public void castSpell(ItemStack item, World world, EntityPlayer player) {
		//IGGMEntityLivingBase.get(player).setCastDuration(new SpellFireBolt(world, player), item, 20, this.manaTakeValue());
	}
	
	@Override
	public String name() {
		return null;
	}
	
	public static class Scroll extends ItemFireBolt {
		
		private static final String name = "scroll_fire_bolt";
		
		public Scroll() {
			super();						
		}
		
		@Override
		public String name() {
			return name;
		}
		
		@Override
		public boolean expendable() {
			return true;
		}
	}	
	
	public static class Rune extends ItemFireBolt {
		
		private static final String name = "rune_fire_bolt";
		
		public Rune() {
			super();							
		}
		
		@Override
		public String name() {
			return name;
		}
		
		@Override
		protected boolean rune() {
			return true;
		}
		
	}

}
