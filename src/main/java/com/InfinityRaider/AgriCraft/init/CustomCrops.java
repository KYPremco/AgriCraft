package com.InfinityRaider.AgriCraft.init;

import com.InfinityRaider.AgriCraft.blocks.BlockModPlant;
import com.InfinityRaider.AgriCraft.handler.ConfigurationHandler;
import com.InfinityRaider.AgriCraft.items.ItemModSeed;
import com.InfinityRaider.AgriCraft.reference.Names;
import com.InfinityRaider.AgriCraft.utility.IOHelper;
import com.InfinityRaider.AgriCraft.utility.LogHelper;
import com.InfinityRaider.AgriCraft.utility.RegisterHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class CustomCrops {
    public static BlockModPlant[] customCrops;
    public static ItemModSeed[] customSeeds;

    public static void init() {
        if(ConfigurationHandler.customCrops) {
           String[] cropsRawData = IOHelper.getLinesArrayFromData(ConfigurationHandler.readCustomCrops());
            customCrops = new BlockModPlant[cropsRawData.length];
            customSeeds = new ItemModSeed[cropsRawData.length];
            for(int i=0;i< cropsRawData.length;i++) {
                String[] cropData = IOHelper.getCropData(cropsRawData[i]);
                //cropData[0]: name
                //cropData[1]: fruit name
                //cropData[2]: fruit meta
                //cropData[3]: base block name
                //cropData[4]: base block meta
                //cropData[5]: tier
                //cropData[6]: render type
                //cropData[7]: information
                customCrops[i] = new BlockModPlant(Block.getBlockFromName(cropData[3]), Integer.parseInt(cropData[4]), (Item) Item.itemRegistry.getObject(cropData[1]), Integer.parseInt(cropData[2]), Integer.parseInt(cropData[5]), Integer.parseInt(cropData[6]));
                RegisterHelper.registerBlock(customCrops[i], Names.crop+ Character.toUpperCase(cropData[0].charAt(0))+cropData[0].substring(1));

                customSeeds[i] = new ItemModSeed(customCrops[i], Character.toUpperCase(cropData[0].charAt(0))+cropData[0].substring(1) + " Seeds", cropData[7]);
                RegisterHelper.registerItem(customSeeds[i], Names.seed+ Character.toUpperCase(cropData[0].charAt(0))+cropData[0].substring(1));
                OreDictionary.registerOre(Names.listAllseed, CustomCrops.customSeeds[i]);
            }
            LogHelper.info("Custom crops registered");
        }
    }
}