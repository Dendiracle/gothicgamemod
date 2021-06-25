package mrfinger.gothicgamemod.tileentity;

import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityAnimalEgg extends TileEntity
{

    protected IEntityGothicAnimal baby;
    protected int actionTimer;
    protected int timerToDestroyDuration;


    public TileEntityAnimalEgg()
    {
        this.actionTimer = Integer.MAX_VALUE;
        System.out.println("Debug in TileEntityAnimalEgg init ");
    }


    public TileEntityAnimalEgg(IEntityGothicAnimal baby, int timer, int timerToDestroyDuration)
    {
        this.baby = baby;
        this.actionTimer = timer;
        this.timerToDestroyDuration = timerToDestroyDuration;
    }


    public void setParametrs(IEntityGothicAnimal baby, int actionTimer, int timerToDestroyDuration)
    {
        this.baby = baby;
        this.actionTimer = actionTimer;
        this.timerToDestroyDuration = timerToDestroyDuration;
    }


    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (this.baby != null)
        {
            NBTTagCompound babyNBT = new NBTTagCompound();
            babyNBT.setString("id", EntityList.getEntityString((Entity) this.baby));
            this.baby.writeToNBT(nbt);
            nbt.setTag("Baby", babyNBT);
        }

        nbt.setInteger("Timer", this.actionTimer);
        nbt.setInteger("Dest", this.timerToDestroyDuration);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.hasKey("Baby")) this.baby = (IEntityGothicAnimal) EntityList.createEntityFromNBT(nbt.getCompoundTag("Baby"), this.worldObj);
        this.actionTimer = nbt.getInteger("Timer");
        this.timerToDestroyDuration = nbt.getInteger("Dest");
    }


    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.actionTimer <= 0 && !this.worldObj.isRemote)
        {
            if (this.baby != null)
            {
                this.birthChild();
                this.actionTimer = this.timerToDestroyDuration;
            }
            else
            {
                this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
            }
        }

        --this.actionTimer;
        if (!this.worldObj.isRemote && this.actionTimer % 200 == 0)
        {
            System.out.println("Debug in TileEntityAnimalEgg isTileEntityAlive " + xCoord + " " + yCoord + " " + zCoord + " " + actionTimer);
        }
    }

    protected void birthChild()
    {
        EntityLivingBase baby = (EntityLivingBase) this.baby;
        baby.setLocationAndAngles(this.xCoord, this.yCoord, this.zCoord, MathHelper.wrapAngleTo180_float(this.worldObj.rand.nextFloat() * 360.0F), 0.0F);
        baby.rotationYawHead = baby.rotationYaw;
        baby.renderYawOffset = baby.rotationYaw;
        this.worldObj.spawnEntityInWorld(baby);
    }
}
