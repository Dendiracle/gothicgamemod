package mrfinger.gothicgamemod.tileentity;

import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class TileEntityAnimalEgg extends TileEntity
{

    protected NBTTagCompound babyNBT;
    protected int actionTimer;
    protected int timerToDestroyDuration;


    public TileEntityAnimalEgg()
    {
        this.actionTimer = Integer.MAX_VALUE;
    }


    public void setParametrs(IEntityGothicAnimal baby, int actionTimer, int timerToDestroyDuration)
    {
        if (baby != null)
        {
            NBTTagCompound babyNBT = new NBTTagCompound();
            babyNBT.setString("id", EntityList.getEntityString((Entity) baby));
            baby.writeToNBTGGM(babyNBT);
            this.babyNBT = babyNBT;
        }
        else
        {
            this.babyNBT = null;
        }
        this.actionTimer = actionTimer;
        this.timerToDestroyDuration = timerToDestroyDuration;
    }


    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (this.babyNBT != null)
        {
            nbt.setTag("Baby", this.babyNBT);
        }

        nbt.setInteger("Timer", this.actionTimer);
        nbt.setInteger("Dest", this.timerToDestroyDuration);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.hasKey("Baby"))
        {
            this.babyNBT = nbt.getCompoundTag("Baby");
        }
        else
        {
            this.babyNBT = null;
        }
        this.actionTimer = nbt.getInteger("Timer");
        this.timerToDestroyDuration = nbt.getInteger("Dest");
    }


    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.actionTimer <= 0 && !this.worldObj.isRemote)
        {
            if (this.babyNBT != null)
            {
                this.birthChild();
                this.babyNBT = null;
                this.actionTimer = this.timerToDestroyDuration;
            }
            else
            {
                this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
            }
        }

        --this.actionTimer;
    }

    protected void birthChild()
    {
        EntityLivingBase baby = (EntityLivingBase) EntityList.createEntityFromNBT(this.babyNBT, this.worldObj);
        baby.setLocationAndAngles(this.xCoord, this.yCoord, this.zCoord, MathHelper.wrapAngleTo180_float(this.worldObj.rand.nextFloat() * 360.0F), 0.0F);
        baby.rotationYawHead = baby.rotationYaw;
        baby.renderYawOffset = baby.rotationYaw;
        this.worldObj.spawnEntityInWorld(baby);
    }
}
