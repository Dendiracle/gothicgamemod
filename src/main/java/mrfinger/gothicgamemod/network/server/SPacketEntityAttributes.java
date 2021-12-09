package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.data.entity.EntityCapabilitiesData;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class SPacketEntityAttributes implements IMessage
{

    private int entityID;

    private Map<IGGMAttribute, IGGMAttributeInstance.IAttributeSnapshot> attributeSnapshotsList = new HashMap<>();


    public SPacketEntityAttributes()
    {
    }

    public SPacketEntityAttributes(int entityID, Collection<IGGMAttributeInstance> attributesCollection)
    {
        this.entityID = entityID;

        for (IGGMAttributeInstance attributeInstance : attributesCollection)
        {
            this.attributeSnapshotsList.put(((IGGMAttribute) attributeInstance.getAttribute()), attributeInstance.getShapshot());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        int size = buf.readInt();

        for (int i = 0; i < size; ++i)
        {
            IGGMAttribute attribute = EntityCapabilitiesData.getRegisteredGenericAttribute(new UUID(buf.readLong(), buf.readLong()));
            this.attributeSnapshotsList.put(attribute, attribute.getAttributeInstanceSnapshotFromBytes(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeInt(this.attributeSnapshotsList.size());

        for (Map.Entry<IGGMAttribute, IGGMAttributeInstance.IAttributeSnapshot> entry : this.attributeSnapshotsList.entrySet())
        {
            buf.writeLong(entry.getKey().getUUID().getMostSignificantBits());
            buf.writeLong(entry.getKey().getUUID().getLeastSignificantBits());
            entry.getKey().convertAttributeInstanceToBytes(entry.getValue(), buf);
        }
    }


    public static class Handler extends AbstractClientMessageHandler<SPacketEntityAttributes>
    {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketEntityAttributes message, MessageContext ctx)
        {

            Entity entity = player.worldObj.getEntityByID(message.entityID);

            if (entity instanceof EntityLivingBase)
            {
                for (Map.Entry<IGGMAttribute, IGGMAttributeInstance.IAttributeSnapshot> entry : message.attributeSnapshotsList.entrySet())
                {
                    IGGMAttributeInstance attributeInstance = (IGGMAttributeInstance) ((EntityLivingBase) entity).getAttributeMap().getAttributeInstanceByName(entry.getKey().getAttributeUnlocalizedName());

                    if (attributeInstance == null)
                    {
                        ((EntityLivingBase) entity).getAttributeMap().registerAttribute(entry.getKey());
                    }

                    entry.getValue().setAttribute(attributeInstance);
                }
            }

            return null;
        }
    }

}
