package com.r4l.tan_potion_thirst_fix.config;

import java.io.File;
import java.util.Arrays;

import com.r4l.tan_potion_thirst_fix.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static ConfigData configData = new ConfigData();
	
	public static void init(File file) {
		config = new Configuration(file);
		config.load();
		
		String category = "TAN_Potion_Thirst_Configuration";
		config.addCustomCategoryComment(category, "What the name says");
		
		// Force output order in the file
	    config.setCategoryPropertyOrder(category, Arrays.asList(
	        "potion_ids",
	        "make_whitelist_blacklist",
	        "thirst_value",
	        "hydration_value",
	        "duration"
	    ));
		
	    configData.potion_ids = config.getStringList("potion_ids", category, DefaultValues.potion_ids, "Which potions will give the thirst effect after drinking it");
	    configData.make_whitelist_blacklist = config.getBoolean("make_whitelist_blacklist", category, DefaultValues.make_whitelist_blacklist, "Makes so all of the potions will give the thirst effect unless they're in the list");
		
	    configData.thirst_value = config.getInt("thirst_value", category, DefaultValues.thirst_value, 0, Integer.MAX_VALUE, "A value of thirst you gain after drinking a Potion");
	    configData.hydration_value = config.getFloat("hydration_value", category, DefaultValues.hydration_value, 0.0f, Float.MAX_VALUE, "A value of hydration you gain after drinking a Potion");
	    configData.duration = config.getInt("duration", category, DefaultValues.duration, 1, Integer.MAX_VALUE, "A duration of the Thirst effect");
		
		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event) {
		File cfg_dir = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		cfg_dir.mkdirs();
		init(new File(cfg_dir.getPath(), Reference.MODID + ".cfg"));
	}

}
