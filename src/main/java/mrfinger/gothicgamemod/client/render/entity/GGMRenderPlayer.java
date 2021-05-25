package mrfinger.gothicgamemod.client.render.entity;

import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.client.model.ModelPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GGMRenderPlayer extends RenderPlayer
{
    private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");

    public GGMRenderPlayer()
    {
        this.mainModel = new ModelPlayer();
        this.modelBipedMain = (ModelBiped) this.mainModel;
        this.modelArmorChestplate = new ModelPlayer();
        this.modelArmor = new ModelPlayer();
    }


    @Override
    public void doRender(AbstractClientPlayer playerg, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float f1)
    {
        IGGMAbstractClientPlayer player = (IGGMAbstractClientPlayer) playerg;

        if (player.inFightStance())
        {
            ItemStack itemstack = player.getSecHeldItem();

            if (itemstack != null)
            {
                this.modelArmorChestplate.heldItemLeft = this.modelArmor.heldItemLeft = this.modelBipedMain.heldItemLeft = 1;
            }
        }

        ((ModelPlayer) this.mainModel).tickDur = f1;

        //this.modelArmorChestplate.setLivingAnimations(playerg,playerg.limbSwing - playerg.limbSwingAmount * (1.0F - f1), playerg.prevLimbSwingAmount + (playerg.limbSwingAmount - playerg.prevLimbSwingAmount) * f1, f1);
        super.doRender(playerg, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, f1);

        this.modelArmorChestplate.heldItemLeft = this.modelArmor.heldItemLeft = this.modelBipedMain.heldItemLeft = 0;
    }


    @Override
    protected void renderEquippedItems(AbstractClientPlayer playerg, float p_77029_2_)
    {
        super.renderEquippedItems(playerg, p_77029_2_);

        IGGMAbstractClientPlayer player = (IGGMAbstractClientPlayer) playerg;

        if (player.inFightStance())
        {
            ItemStack itemstack1 = player.getSecHeldItem();

            if (itemstack1 != null)
            {
                GL11.glPushMatrix();
                this.modelBipedMain.bipedLeftArm.postRender(0.0625F);
                GL11.glTranslatef(0.0625F, 0.4375F, 0.0625F);

                EnumAction enumaction = null;

                if (player.isUsingLH())
                {
                    enumaction = itemstack1.getItemUseAction();
                }

                net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack1, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
                float f2;

                if (is3D || itemstack1.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack1.getItem()).getRenderType()))
                {
                    f2 = 0.5F;
                    GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                    f2 *= 0.75F;
                    GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(-f2, -f2, f2);
                }
                else if (itemstack1.getItem() == Items.bow)
                {
                    f2 = 0.625F;
                    GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                    GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f2, -f2, f2);
                    GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                }
                else if (itemstack1.getItem().isFull3D())
                {
                    f2 = 0.625F;

                    if (itemstack1.getItem().shouldRotateAroundWhenRendering())
                    {
                        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                    }

                    if (playerg.getItemInUseCount() > 0 && enumaction == EnumAction.block)
                    {
                        GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                        GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                        GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                    GL11.glScalef(f2, -f2, f2);
                    GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                }
                else
                {
                    f2 = 0.375F;
                    GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                    GL11.glScalef(f2, f2, f2);
                    GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
                }

                float f3;
                int k;
                float f12;

                if (itemstack1.getItem().requiresMultipleRenderPasses())
                {
                    for (k = 0; k < itemstack1.getItem().getRenderPasses(itemstack1.getItemDamage()); ++k)
                    {
                        int i = itemstack1.getItem().getColorFromItemStack(itemstack1, k);
                        f12 = (float)(i >> 16 & 255) / 255.0F;
                        f3 = (float)(i >> 8 & 255) / 255.0F;
                        float f4 = (float)(i & 255) / 255.0F;
                        GL11.glColor4f(f12, f3, f4, 1.0F);
                        this.renderManager.itemRenderer.renderItem(playerg, itemstack1, k);
                    }
                }
                else
                {
                    k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                    float f11 = (float)(k >> 16 & 255) / 255.0F;
                    f12 = (float)(k >> 8 & 255) / 255.0F;
                    f3 = (float)(k & 255) / 255.0F;
                    GL11.glColor4f(f11, f12, f3, 1.0F);
                    this.renderManager.itemRenderer.renderItem(playerg, itemstack1, 0);
                }

                GL11.glPopMatrix();
            }
        }
    }


    @Override
    protected float renderSwingProgress(EntityLivingBase entity, float p_77040_2_)
    {
        IGGMAbstractClientPlayer player = (IGGMAbstractClientPlayer) entity;

        if (player.getGGMEquipment().isUsingItem())
        {
            if (player.isUsingLH())
            {
                this.modelArmorChestplate.heldItemLeft = this.modelArmor.heldItemLeft = this.modelBipedMain.heldItemLeft = 3;

                if (player.getGGMEquipment().getSecHeldItem().getItemUseAction() == EnumAction.bow)
                {
                    this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
                }
            }
            else
            {
                this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;

                if (player.getGGMEquipment().getHeldItem().getItemUseAction() == EnumAction.bow)
                {
                    this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
                }
            }
        }
        return super.renderSwingProgress(entity, p_77040_2_);
    }
}
