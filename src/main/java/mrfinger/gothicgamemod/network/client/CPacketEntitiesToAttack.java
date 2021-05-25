package mrfinger.gothicgamemod.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import mrfinger.gothicgamemod.network.server.AbstractServerMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketEntitiesToAttack implements IMessage {


    private int[] idArray;


    public CPacketEntitiesToAttack() {}

    public CPacketEntitiesToAttack(Entity[] entityArray) {

        int size = entityArray.length;

        idArray = new int[size];

        for (int i = 0; i < size; ++i) {
            idArray[i] = entityArray[i].getEntityId();
        }
    }


    @Override
    public void fromBytes(ByteBuf buf) {

        int size = buf.readInt();

        idArray = new int[size];

        for (int i = 0; i < size; ++i) {
            idArray[i] = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(idArray.length);

        for (int i = 0; i < idArray.length; ++i) {
            buf.writeInt(idArray[i]);
        }
    }


    public static class Handler extends AbstractServerMessageHandler<CPacketEntitiesToAttack> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, CPacketEntitiesToAttack message, MessageContext ctx) {

            Entity[] entityArray = new Entity[message.idArray.length];


            for (int i = 0; i < message.idArray.length; ++i) {

                Entity entity = player.worldObj.getEntityByID(message.idArray[i]);

                entityArray[i] = entity;

            }

            ((IGGMEntityPlayerMP) player).setFightAnimationTargets((IGGMEntity[]) entityArray);

            return null;
        }
    }

}
