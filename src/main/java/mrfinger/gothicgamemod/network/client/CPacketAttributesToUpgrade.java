package mrfinger.gothicgamemod.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.client.entity.capability.GGMIncreasableAttributeHelper;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityExperienceable;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.network.server.AbstractServerMessageHandler;
import mrfinger.gothicgamemod.network.server.SPacketAfterAttributesUpgraded;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class CPacketAttributesToUpgrade implements IMessage
{


    int id;

    Map<String, Integer> amounts;


    public CPacketAttributesToUpgrade()
    {

    }

    public CPacketAttributesToUpgrade(int id, GGMIncreasableAttributeHelper... attributeHelpers) {

        this.id = id;
        this.amounts = new HashMap<>(attributeHelpers.length, 1.0F);

        for (GGMIncreasableAttributeHelper ah : attributeHelpers) {

            IGGMAttributeInstance ai = ah.attributeInstance;
            this.amounts.put(ai.getAttribute().getAttributeUnlocalizedName(), ah.upgradeAmounts);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.id = buf.readInt();
        int j = buf.readInt();
        this.amounts = new HashMap<>(j, 1.0F);

        for (int i = 0; i < j; ++i) {

            int jj = buf.readInt();

            char[] s = new char[jj];

            for (int ii = 0; ii < jj; ++ii) {

                s[ii] = buf.readChar();
            }

            this.amounts.put(String.valueOf(s), buf.readInt());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.id);
        buf.writeInt(this.amounts.size());

        for (Map.Entry<String, Integer> e : this.amounts.entrySet()) {

            char[] s = e.getKey().toCharArray();

            buf.writeInt(s.length);

            for (char c : s)
            {
                buf.writeChar(c);
            }

            buf.writeInt(e.getValue());
        }
    }


    public static class Handler extends AbstractServerMessageHandler<CPacketAttributesToUpgrade> {


        @Override
        public IMessage handleServerMessage(EntityPlayer player, CPacketAttributesToUpgrade message, MessageContext ctx)
        {
            Entity entity = player.worldObj.getEntityByID(message.id);

            if (entity instanceof IGGMEntityExperienceable)
            {
                ((IGGMEntityExperienceable) entity).increaseAttributesByName(message.amounts);
            }
            return new SPacketAfterAttributesUpgraded();
        }
    }
}
