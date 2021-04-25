package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.fractions.Fraction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.Set;

public class EntityPack extends Entity {


    protected Set<IGGMEntityLivingBase> entitiesSet;

    protected Set<IGGMEntityLivingBase> enemiesSet;

    protected Fraction fraction;


    public EntityPack(World world) {
        super(world);

        this.noClip = true;
    }


    @Override
    protected void entityInit() {

    }


    @Override
    public void onEntityUpdate() {

        this.worldObj.theProfiler.startSection("entityBaseTick");

        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        int i;

        if (this.posY < -64.0D)
        {
            this.kill();
        }

        this.firstUpdate = false;
        this.worldObj.theProfiler.endSection();






    }


    protected void findEnemies() {

    }

    public void addEntityToPack(IGGMEntityLivingBase entity) {

        this.entitiesSet.add(entity);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }
}
