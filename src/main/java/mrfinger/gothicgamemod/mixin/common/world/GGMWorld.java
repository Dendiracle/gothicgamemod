package mrfinger.gothicgamemod.mixin.common.world;

import mrfinger.gothicgamemod.entity.HabitatsCollection;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public abstract class GGMWorld implements IGGMWorld
{

    @Shadow public boolean isRemote;

    @Shadow @Final public MapStorage perWorldStorage;
    protected HabitatsCollection habitatsCollection;


    @Redirect(method = "<init>(Lnet/minecraft/world/storage/ISaveHandler;Ljava/lang/String;Lnet/minecraft/world/WorldSettings;Lnet/minecraft/world/WorldProvider;Lnet/minecraft/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/storage/MapStorage;loadData(Ljava/lang/Class;Ljava/lang/String;)Lnet/minecraft/world/WorldSavedData;", ordinal = 0))
    private WorldSavedData onInit(MapStorage mapStorage, Class clas, String string)
    {
        HabitatsCollection collection = (HabitatsCollection) this.perWorldStorage.loadData(HabitatsCollection.class, "gothic-habitats");

        if (collection == null)
        {
            this.habitatsCollection = new HabitatsCollection(this);
            this.perWorldStorage.setData("gothic-habitats", this.habitatsCollection);
        }
        else
        {
            this.habitatsCollection = collection;
            this.habitatsCollection.setWorld(this);
        }

        return mapStorage.loadData(clas, string);
    }


    @Override
    public boolean isRemote()
    {
        return this.isRemote;
    }


    @Override
    public HabitatsCollection getHabitatsCollection()
    {
        return this.habitatsCollection;
    }

}
