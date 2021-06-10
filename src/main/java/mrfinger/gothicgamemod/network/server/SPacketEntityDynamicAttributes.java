package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SPacketEntityDynamicAttributes implements IMessage {


    private int id;

    private Map<String, Double> map;


    public SPacketEntityDynamicAttributes() {}

    public SPacketEntityDynamicAttributes(int entityID, Collection<IGGMDynamicAttributeInstance> dpiCollection) {

        this.id = entityID;
        this.map = new HashMap<>(dpiCollection.size(), 1.0F);

        for (IGGMDynamicAttributeInstance dpi : dpiCollection) {

            this.map.put(dpi.getAttribute().getAttributeUnlocalizedName(), dpi.getCurrentValue());
        }
    };

    @Override
    public void fromBytes(ByteBuf buf) {

        this.id = buf.readInt();

        int j = buf.readInt();

        this.map = new HashMap<>(j, 1.0F);

        for (int i = 0; i < j; ++i) {

            int jj = buf.readInt();

            char[] s = new char[jj];

            for (int ii = 0; ii < jj; ++ii) {

                s[ii] = buf.readChar();
            }

            this.map.put(String.valueOf(s), buf.readDouble());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.id);
        buf.writeInt(this.map.size());

        for (Map.Entry<String, Double> e : this.map.entrySet()) {

            char[] s = e.getKey().toCharArray();

            buf.writeInt(s.length);

            for (char c : s) {
                buf.writeChar(c);
            }

            buf.writeDouble(e.getValue());
        }
    }


    public static class Handler extends AbstractClientMessageHandler<SPacketEntityDynamicAttributes> {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketEntityDynamicAttributes message, MessageContext ctx) {

            Entity entity = player.worldObj.getEntityByID(message.id);

            if (entity instanceof EntityLivingBase)
            {

                for (Map.Entry<String, Double> e : message.map.entrySet())
                {
                    IGGMDynamicAttributeInstance dai = (IGGMDynamicAttributeInstance) ((EntityLivingBase) entity).getAttributeMap().getAttributeInstanceByName(e.getKey());
                    dai.setCurrentValue(e.getValue());
                }
            }

            return null;
        }
    }

}
