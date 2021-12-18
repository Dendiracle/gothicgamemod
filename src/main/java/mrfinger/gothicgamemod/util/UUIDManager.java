package mrfinger.gothicgamemod.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDManager
{

    private static final List<UUID> uuidList = new ArrayList();


    public static UUID getUUID(int counter)
    {
        if (counter < 0) throw new IllegalArgumentException("Counter cannot be less than 0");

        UUID id = uuidList.get(counter);

        if (id == null)
        {
            int beforeCounter = counter;

            for (; beforeCounter > 0 && uuidList.get(beforeCounter) == null; --beforeCounter) {}

            for (; beforeCounter < counter; ++beforeCounter)
            {
                byte bytes[] = ByteBuffer.allocate(8).putLong(beforeCounter).array();
                uuidList.add(UUID.nameUUIDFromBytes(bytes));
                System.out.println("Debug in UUIDManager " + counter + " " + bytes[0] + " " + bytes[1]);
            }

            id = uuidList.get(counter);
        }

        return id;
    }


}
