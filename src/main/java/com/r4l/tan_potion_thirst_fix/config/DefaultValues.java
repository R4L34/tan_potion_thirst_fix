package com.r4l.tan_potion_thirst_fix.config;

import toughasnails.api.thirst.WaterType;

public class DefaultValues {
	
	public static String[] potion_ids = {
			"minecraft:water"
	};
	
	public static boolean make_whitelist_blacklist = false;
	
	public static int thirst_value = WaterType.NORMAL.getThirst();
	
	public static float hydration_value = WaterType.NORMAL.getHydration();
	
	public static int duration = 600;

}
