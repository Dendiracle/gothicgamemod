package mrfinger.gothicgamemod.block;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.tileentity.TileEntityAnimalEgg;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAnimalEggs extends Block implements ITileEntityProvider
{

    public BlockAnimalEggs(Material material, String name, float hardness, float resistance, float width, float height)
    {
        super(material);

        float f0 = 0.5F - width * 0.5F;
        this.setBlockName(name);
        this.setBlockTextureName(GothicMain.MODID + ":" + name);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setBlockBounds(f0, 0F, f0, f0 += width, height, f0);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public BlockAnimalEggs(String name, float width, float height)
    {
        this(Material.rock, name, 1F, 1F, width, height);
    }


    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack p_149689_6_)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, p_149689_6_);

        if (entity instanceof IEntityGothicAnimal)
        {
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityAnimalEgg)
            {
                ((IEntityGothicAnimal) entity).setEggParametrs((TileEntityAnimalEgg) tileEntity);
            }
            else
            {
                if (tileEntity != null)
                {
                    world.removeTileEntity(x, y, z);
                }

                tileEntity = this.createNewTileEntity(world, 0);
                world.addTileEntity(tileEntity);
                ((IEntityGothicAnimal) entity).setEggParametrs((TileEntityAnimalEgg) tileEntity);
            }
        }
    }


    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityAnimalEgg();
    }

}
