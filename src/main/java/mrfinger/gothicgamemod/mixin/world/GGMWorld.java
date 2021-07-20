package mrfinger.gothicgamemod.mixin.world;

import mrfinger.gothicgamemod.entity.HabitatsCollection;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.*;

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
        File file = perWorldStorage.saveHandler.getMapFileFromName("gothic-habitats");
        String s = file != null ? (file + " " + file.exists()) : null;
        System.out.println("Debug in GGMWorld init " + " " + s);
        if (collection == null)
        {
            this.habitatsCollection = new HabitatsCollection(this);
            this.perWorldStorage.setData("gothic-habitats", this.habitatsCollection);
            System.out.println("AAA");
        }
        else
        {
            this.habitatsCollection = collection;
            this.habitatsCollection.setWorld(this);
            System.out.println("BBB");
        }
        System.out.println(perWorldStorage.saveHandler + " " + perWorldStorage.loadedDataMap.get("gothic-habitats") + " " + perWorldStorage.saveHandler.getMapFileFromName("gothic-habitats"));
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
