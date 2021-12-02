package mrfinger.gothicgamemod.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.network.client.AbstractClientMessageHandler;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;
public class SPacketPackControl implements IMessage
{

    protected UUID id;
    protected String fraction;

    protected double posX;
    protected double posY;
    protected double posZ;


    public SPacketPackControl() {}

    public SPacketPackControl(UUID id, PackFraction fraction, double posX, double posY, double posZ)
    {
        this.id = id;
        this.fraction = fraction.getUnlocalizedName();
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.id = new UUID(buf.readLong(), buf.readLong());

        char[] fraction = new char[buf.readInt()];

        if (fraction.length > 0)
        {
            for (int i = 0; i < fraction.length; ++i) {
                fraction[i] = buf.readChar();
            }

            this.fraction = String.copyValueOf(fraction);
        }

        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.id.getMostSignificantBits());
        buf.writeLong(this.id.getLeastSignificantBits());

        char[] fraction = this.fraction.toCharArray();
        buf.writeInt(fraction.length);

        for (char c : fraction)
        {
            buf.writeChar(c);
        }

        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
    }


    public static class Handler extends AbstractClientMessageHandler<SPacketPackControl>
    {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, SPacketPackControl message, MessageContext ctx)
        {
            //IPackEntity pack = ((IGGMWorld) player.worldObj).createNewPack((PackFraction) GGMFractions.fractionsMap.get(message.fraction), message.id);
            //pack.setPos(message.posX, message.posY, message.posZ);

            return null;
        }

    }
}
