package mrfinger.gothicgamemod.client.entity.animations.hits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketEntitiesToAttack;
import mrfinger.gothicgamemod.network.client.CPacketSetItemInUseInFightAnim;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.Collection;
import java.util.List;

@SideOnly(Side.CLIENT)
public class AnimationFightStanceClient extends GGMPlayerEquipmentAnimationFightStance
{

    public AnimationFightStanceClient(IGGMEntityPlayer entity)
    {
        super(entity);
    }


    @Override
    public boolean denyUsingItems()
    {
        PacketDispatcher.sendToServer(new CPacketSetItemInUseInFightAnim(true));
        return super.denyUsingItems();
    }

    @Override
    protected void updateAttack()
    {
        if (this.count == this.attackTick)
        {
            EntityLivingBase[] uArray;
            float[] angles;
            int size = 0;
            boolean flag = this.attackSeries % 2 == 0;
            ++this.attackSeries;
            EntityLivingBase[] hArray;

            {
                float sl = (float) this.entity.getPosY() - this.entity.getEyeHeight() - 0.1F;
                float rad = 4.0F;
                float s = 0.05F;
                float pi = (float) Math.PI;
                float semipi = pi * 0.5F;


                float rotY = this.entity.getRotationYaw() * 0.017453292F;
                if (rotY > pi) {
                    float dpi = pi * 2.0F;
                    while (rotY >= pi) rotY -= dpi;
                } else if (rotY <= -pi) {
                    float dpi = pi * 2.0F;
                    while (rotY <= -pi) rotY += dpi;
                }
                float rotP = this.entity.getRotationPitch() * 0.017453292F;
                if (rotP >= semipi - 0.1F) rotP = semipi - 0.1F;
                else if (rotP <= -semipi + 0.1F) rotP = -semipi + 0.1F;

                float sinY = MathHelper.sin(rotY);
                float cosY = MathHelper.cos(rotY);
                float sinP = MathHelper.sin(rotP);
                float cosP = MathHelper.cos(rotP);


                float dx = -s * sinY * sinP;
                float dy = s * cosP;
                float dz = s * cosY * sinP;

                float maxY = sl + dy;
                float minY = sl - dy;
                {
                    float f = rad * sinP;
                    if (sinP < 0.0F) maxY -= f;
                    else minY -= f;
                }

                float minX = (float) this.entity.getPosX();
                float maxX = minX;
                if (dx < 0) {
                    minX += dx;
                    maxX -= dx;
                } else {
                    minX -= dx;
                    maxX += dx;
                }
                {
                    float f = rad * cosY;
                    if (f < 0) f *= -1.0F;
                    minX -= f;
                    maxX += f;

                    float f1 = (rad - f) * cosP;
                    if (sinY > 0.0F) minX -= f1;
                    else maxX += f1;
                }

                float minZ = (float) this.entity.getPosZ();
                float maxZ = minZ;
                if (dz < 0) {
                    minZ += dz;
                    maxZ -= dz;
                } else {
                    minZ -= dz;
                    maxZ += dz;
                }
                {
                    float f = rad * sinY;
                    if (f < 0) f *= -1.0F;
                    minZ -= f;
                    maxZ += f;
                    float f1 = (rad - f) * cosP;
                    if (cosY < 0.0F) minZ -= f1;
                    else maxZ += f1;
                }

                {

                    List<EntityLivingBase> eArray = this.entity.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
                    int e = eArray.size();
                    uArray = new EntityLivingBase[e];
                    angles = new float[e];

                    boolean isRiding = this.entity.isRiding();

                    for (EntityLivingBase entity : eArray) {

                        if (entity == this.entity || (this.entity.getRidingEntity() == entity)) continue;

                        float mal = flag ? -9999.0F : 9999.0F;

                        boolean fits = false;

                        {
                            float xz[] = new float[8];

                            {
                                float d;
                                d = (float) this.entity.getPosX() + dx;
                                xz[0] = (float) entity.boundingBox.minX - d;
                                xz[1] = (float) entity.boundingBox.maxX - d;
                                d = (float) this.entity.getPosZ() + dz;
                                xz[2] = (float) entity.boundingBox.minZ - d;
                                xz[3] = (float) entity.boundingBox.maxZ - d;
                                d = (float) this.entity.getPosX() - dx;
                                xz[4] = (float) entity.boundingBox.minX - d;
                                xz[5] = (float) entity.boundingBox.maxX - d;
                                d = (float) this.entity.getPosZ() - dz;
                                xz[6] = (float) entity.boundingBox.minZ - d;
                                xz[7] = (float) entity.boundingBox.maxZ - d;
                            }

                            for (int i = 0; i < 2; ++i) {

                                for (int j = 2; j < 4; ++j) {

                                    float yt, yb, d, d1, al, al1, a, a1;
                                    al = rotY;
                                    al1 = rotY;

                                    d = MathHelper.sqrt_float(xz[i] * xz[i] + xz[j] * xz[j]);
                                    if (xz[i] <= 0.0F) al += (float) Math.asin(xz[j] / d);
                                    else {
                                        if (xz[j] >= 0.0F) al += semipi + (float) Math.asin(xz[i] / d);
                                        else al -= semipi + (float) Math.asin(xz[i] / d);
                                    }

                                    if (flag) {
                                        if (al > mal) mal = al;
                                    } else if (al < mal) mal = al;

                                    if (!fits) {

                                        i += 4;
                                        j += 4;
                                        d1 = MathHelper.sqrt_float(xz[i] * xz[i] + xz[j] * xz[j]);
                                        if (xz[i] <= 0.0F) al1 += (float) Math.asin(xz[j] / d);
                                        else {
                                            if (xz[j] >= 0.0F) al1 += semipi + (float) Math.asin(xz[i] / d);
                                            else al1 -= semipi + (float) Math.asin(xz[i] / d);
                                        }
                                        i -= 4;
                                        j -= 4;

                                        a = d * MathHelper.sin(al);
                                        yt = (a * -sinP) / cosP;
                                        a1 = d1 * MathHelper.sin(al1);
                                        yb = (a1 * -sinP) / cosP;

                                        if (sl + dy + yt >= entity.boundingBox.minY && sl - dy + yb <= entity.boundingBox.maxY
                                                && (a >= 0.0F || a1 >= 0.0F)
                                                && (MathHelper.sqrt_float(d * d + yt * yt) <= rad || MathHelper.sqrt_float(d1 * d1 + yb * yb) <= rad)) {
                                            fits = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (!fits) {

                            boolean x = maxX <= entity.boundingBox.maxX && minX >= entity.boundingBox.minX;
                            boolean y = maxY <= entity.boundingBox.maxY && minY >= entity.boundingBox.minY;
                            boolean z = maxZ <= entity.boundingBox.maxZ && minZ >= entity.boundingBox.minZ;

                            if ((x && y) || (x && z) || (y && z)) fits = true;
                        }


                        if (fits) {
                            uArray[size] = entity;
                            angles[size] = mal;
                            ++size;
                        }
                    }
                }

                hArray = new EntityLivingBase[size];

                for (int i = 0; i < size; ++i) {
                    if (uArray[i] != null) {

                        float minan;
                        int index = 0;

                        if (flag) {

                            minan = -9999998.0F;

                            for (int ii = 0; ii < size; ++ii) {

                                if (minan < angles[ii]) {
                                    index = ii;
                                    minan = angles[ii];
                                }
                            }
                            angles[index] = -9999999.0F;
                            hArray[i] = uArray[index];
                        } else {

                            minan = 9999998.0F;

                            for (int ii = 0; ii < size; ++ii) {

                                if (minan > angles[ii]) {
                                    index = ii;
                                    minan = angles[ii];
                                }
                            }
                            angles[index] = 9999999.0F;
                            hArray[i] = uArray[index];
                        }
                    }
                }
            }

            PacketDispatcher.sendToServer(new CPacketEntitiesToAttack(hArray));
        }
    }


    @Override
    public IGGMEntity[] getTargets()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTargets(IGGMEntity[] entities)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTargets(Collection<IGGMEntity> entities)
    {
        throw new UnsupportedOperationException();
    }

}