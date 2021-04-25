package mrfinger.gothicgamemod.init;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.entity.capability.attributes.GGMIncreasableAttributeInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class GGMConfig {
	

    public static File dir;
	
	
    
    public static class GGMEntities extends GGMConfigCommon {

        public static final String entityCategory = "entities";


    	public Set<String> entitiesSet;

    	public Map<ConfigCategory, ConfigCategory[]> categoryMap;
    	
    	Data d;

		protected GGMEntities(String fileName) {

			super(fileName);

			this.entitiesSet = EntityList.func_151515_b();
			categoryMap = new HashMap<>();

			for (String s : this.entitiesSet) {

                ConfigCategory[] cc = new ConfigCategory[GGMCapabilities.set.size()];

                int i = 0;

			    for (IAttribute a : GGMCapabilities.set) {
			        cc[i] = config.getCategory(entityCategory + config.CATEGORY_SPLITTER + s + config.CATEGORY_SPLITTER + ((RangedAttribute) a).getDescription());
			        cc[i].setComment(((RangedAttribute) a).getDescription() + ":\n");
			        ++i;
                }

                categoryMap.put(config.getCategory(s), cc);
            }

			d = new Data();
		}

		@Override
		protected void init() {
            category.setComment("This is a entities list with them charachteristics.\n"
                                + "Just change these lists and his parametrs if you want,\n"
                                + "but every entity must have a health, and a hostile\n"
                                + "mobs needs strenght and dexterity");

            save();
		}

		@Override
		protected void load() {

    	    for (Map.Entry<ConfigCategory, ConfigCategory[]> ce : this.categoryMap.entrySet()) {

                ConfigCategory c = ce.getKey();
    	        c.setComment(c.getName() + ":\n");

    	        for (ConfigCategory cc : ce.getValue()) {

    	            cc.setComment(cc.getName() + ":\n");



                }


            }
		}

		@Override
		protected void postLoadPre() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void postLoadPost() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void createTransferData() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void extractTransferData(byte[] transferData) {
			// TODO Auto-generated method stub
			
		}

        public class Data implements Serializable {

            public int lvl = 1;

            public boolean isHaveExp = false;

            public boolean[] haveStats = {
                    true, false, false, true, true, false
            };


            public GGMIncreasableAttributeInfo[] stats;
        }
    }
	
	public static abstract class GGMConfigCommon
    {

        protected byte[] transferData;

        protected Configuration config;
        protected ConfigCategory category;

        protected GGMConfigCommon(String fileName)
        {

            config = new Configuration(new File(dir, fileName.concat(".cfg")), GothicMain.VERSION, true);

            category = config.getCategory(fileName);

            init();
        }

        protected abstract void init();

        protected abstract void load();

        protected abstract void postLoadPre();

        protected abstract void postLoadPost();

        public void save()
        {
            if (config.hasChanged()) {
                config.save();
            }
        }

        public abstract void createTransferData();

        public abstract void extractTransferData(byte[] transferData);

        public byte[] getTransferData()
        {
            return transferData;
        }

        protected Property getPropertyStrings(String categoryName, String[] defValue, String comment, boolean needClear)
        {
            ConfigCategory cat = config.getCategory(categoryName);
            if (needClear) {
                cat.clear();
            }

            Property prop = config.get(cat.getQualifiedName(), "list", defValue);
            prop.comment = comment != null ? comment : "";
            return prop;
        }
    }
}
