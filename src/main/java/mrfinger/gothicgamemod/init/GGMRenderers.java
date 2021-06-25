package mrfinger.gothicgamemod.init;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import mrfinger.gothicgamemod.client.model.GiantRatModel;
import mrfinger.gothicgamemod.client.model.MeatbugModel;
import mrfinger.gothicgamemod.client.model.NikitaModel;
import mrfinger.gothicgamemod.client.model.ModelScavenger;
import mrfinger.gothicgamemod.client.render.entity.RenderGGMAnimal;
import mrfinger.gothicgamemod.client.render.entity.RenderEntity;
import mrfinger.gothicgamemod.client.render.item.magic.RuneItemRenderer;
import mrfinger.gothicgamemod.entity.animals.EntityGiantRat;
import mrfinger.gothicgamemod.entity.animals.EntityMeatBug;
import mrfinger.gothicgamemod.entity.animals.EntityNikita;
import mrfinger.gothicgamemod.entity.animals.EntityScavenger;
import mrfinger.gothicgamemod.entity.effects.EntityFireBolt;
import mrfinger.gothicgamemod.item.magic.ItemMagicCast;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraftforge.client.MinecraftForgeClient;

public class GGMRenderers {
	
	public static void load(FMLInitializationEvent e)
	{
		registerEntityAnimalRenderer();

		RenderingRegistry.registerEntityRenderingHandler(EntityMeatBug.class, new RenderEntity(new MeatbugModel(), "meatbug.png", 0.3F));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBolt.class, new RenderSnowball(Items.fire_charge));
		
		registerRuneRenderer();
	}

	private static void registerEntityAnimalRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityScavenger.class, new RenderGGMAnimal(new ModelScavenger(), "scavenger.png", 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNikita.class, new RenderGGMAnimal(new NikitaModel(), "nikita.png", 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGiantRat.class, new RenderGGMAnimal(new GiantRatModel(), "giantrat.png", 0.5F));
	}
	
	private static void registerItemRenderer() {
		
	}
	
	private static void registerRuneRenderer() {
		for (int i = 0; i < GGMItems.runesList.size(); i++) {
			ItemMagicCast rune = GGMItems.runesList.get(i);
			MinecraftForgeClient.registerItemRenderer(rune, new RuneItemRenderer(rune.name()));
		}
	}
	
}
