package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.debug.EntityDebug;
import mrfinger.gothicgamemod.entity.animals.EntityGothicAnimal;
import mrfinger.gothicgamemod.entity.animals.EntityGWolf;
import mrfinger.gothicgamemod.entity.animals.EntityGiantRat;
import mrfinger.gothicgamemod.entity.animals.EntityMeatBug;
import mrfinger.gothicgamemod.entity.animals.EntityNikita;
import mrfinger.gothicgamemod.entity.animals.EntityScavenger;
import mrfinger.gothicgamemod.entity.effects.EntityFireBolt;
import mrfinger.gothicgamemod.fractions.Fraction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

public class GEntities {	
	

	public static int EXPModifier = 10;
	
	public static final Fraction emptyFraction = new Fraction();
	
	public static final Fraction scavengers = new Fraction();
	
	
	
	
	
	
	
	public static void load(FMLInitializationEvent e) {
		
		loadHostileMobs();
		loadThrowingEntity();
		registerEntity(EntityDebug.class, "EntityDebug", 0x00FFFF, 0x00FFFF);

	}
	
	private static void loadFractions() {
		scavengers.fractionSet.add(EntityScavenger.class);
		scavengers.enemiesSet.add(EntityPlayer.class);
		scavengers.enemiesSet.add(EntityGWolf.class);
	}

	private static void loadHostileMobs() {
		
		registerAnimal(EntityScavenger.class, "Scavenger", 0x00FFFF, 0x00008B);
		registerAnimal(EntityGWolf.class, "G_Wolf", 0x00FFFF, 0x00008B);
		registerAnimal(EntityNikita.class, "Nikita", 0x00FFFF, 0x00008B);
		registerAnimal(EntityGiantRat.class, "GiantRat", 0x00FFFF, 0x00008B);
		registerEntity(EntityMeatBug.class, "MeatBug", 0x00FFFF, 0x00008B);
	}
	
	private static void loadThrowingEntity() {
		registerThrowableEntity(EntityFireBolt.class, "Fire_Arrow", 0x00FFFF, 0x00008B);	
	}
	
	public static void registerAnimal(Class<? extends EntityGothicAnimal> entityClass, String name, int primaryColor, int secondaryColor)
	{
	  int entityID = EntityRegistry.findGlobalUniqueEntityId();
	  long seed = name.hashCode();	  

	  EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
	  EntityRegistry.registerModEntity(entityClass, name, entityID, GothicMain.instance, 64, 1, true);
	  EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));	  
	}
	
	public static void registerEntity(Class<? extends Entity> entityClass, String name, int primaryColor, int secondaryColor)
	{
	  int entityID = EntityRegistry.findGlobalUniqueEntityId();
	  long seed = name.hashCode();	  

	  EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
	  EntityRegistry.registerModEntity(entityClass, name, entityID, GothicMain.instance, 64, 1, true);
	  EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));	  
	}
	
	public static void registerThrowableEntity(Class entityClass, String name, int primaryColor, int secondaryColor)
	{
	  int entityID = EntityRegistry.findGlobalUniqueEntityId();
	  long seed = name.hashCode();

	  EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
	  EntityRegistry.registerModEntity(entityClass, name, entityID, GothicMain.instance, 64, 1, true);	 
	}	
	
	
	

}
