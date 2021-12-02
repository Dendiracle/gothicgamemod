package mrfinger.gothicgamemod.entity.capability.attribute;

import java.util.UUID;

public interface IGGMAttributeModifier {


    UUID getID();

    String getName();

    int getOperation();

    double getAmount();

    boolean isSaved();


    IGGMAttributeModifier setAmount(double amount);

    IGGMAttributeModifier setSaved(boolean isSaved);

}
