package mrfinger.gothicgamemod.data.entity;

import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationManager;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationManager;
import mrfinger.gothicgamemod.util.UUIDManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimationsData
{

    private static int idCounter = 0;

    private static final Map<UUID, IAnimationManager> AnimationManagerMapByUUID = new HashMap<>();
    private static final Map<UUID, IAnimationManager> UnmodifiableAnimationManagerMapByMapByUUID = Collections.unmodifiableMap(AnimationManagerMapByUUID);


    public static void registerAnimationManager(IAnimationManager manager)
    {
        manager.setID(UUIDManager.getUUID(idCounter++));
        AnimationManagerMapByUUID.put(manager.getID(), manager);
    }

    public static IAnimationManager getManager(UUID id)
    {
        return AnimationManagerMapByUUID.get(id);
    }

    public static Map<UUID, IAnimationManager> getAnimationManagerMap()
    {
        return UnmodifiableAnimationManagerMapByMapByUUID;
    }
}
