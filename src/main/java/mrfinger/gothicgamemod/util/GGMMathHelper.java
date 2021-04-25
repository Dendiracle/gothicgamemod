package mrfinger.gothicgamemod.util;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GGMMathHelper extends MathHelper {


    /*public static Entity mathEntityToAttack(float x, float y, float z, float rotPitch, float rotYaw, float radius, float scatter, Entity[] entityArray) {

        Entity closest;

        for (int i = 0; i < entityArray.length; ++i) {




        }

        return closest;
    }

    public static Entity[] mathEntitiesToAttack(float px, float py, float pz, float rotPitch, float rotYaw, float radius, float scatter, Entity[] entityArray) {

        Entity closestToAttack;

        float minX;
        float maxX;
        float minY;
        float maxY;
        float minZ;
        float maxZ;

        //float closestX;
        //float closestY;
        //float closestZ;





        for (int i = 0; i < entityArray.length; ++i) {

            minX = (float) entityArray[i].boundingBox.minX - px;
            maxX = (float) entityArray[i].boundingBox.maxX - px;
            minY = (float) entityArray[i].boundingBox.maxY - py + scatter;
            maxY = (float) entityArray[i].boundingBox.maxY - py - scatter;
            minZ = (float) entityArray[i].boundingBox.maxZ - pz;
            maxZ = (float) entityArray[i].boundingBox.maxZ - pz;

            //closestX = minX < maxX ? minX : maxX;
            //closestY = minY < maxY ? minY : maxY;
            //closestZ = minZ < maxZ ? minZ : maxZ;

            float f = MathHelper.sqrt_float(minX * minX + minZ * minZ);
           // float f1 =

           // if (!())


        }

        return entityArray;
    }*/

}
