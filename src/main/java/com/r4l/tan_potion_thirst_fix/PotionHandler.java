package com.r4l.tan_potion_thirst_fix;

import java.util.Arrays;

import com.r4l.tan_potion_thirst_fix.config.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import toughasnails.api.TANPotions;
import toughasnails.api.config.GameplayOption;
import toughasnails.api.config.SyncedConfig;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class PotionHandler {
	
	public static String getPotionId(ItemStack stack) {
	    if (stack.isEmpty() || stack.getItem() != Items.POTIONITEM) 
	    	return null;
	    PotionType type = PotionUtils.getPotionFromItem(stack);
	    String id = type.getRegistryName().toString(); // minecraft:water for example
	    
	    return id;
	}
	
	// Do the thing with potions
	public static void applyPotionRules(EntityPlayer player, ItemStack stack) {
		
		String[] potion_ids = ConfigHandler.configData.potion_ids;
		
		  if (!stack.getItem().equals(Items.POTIONITEM))
			  return;
		
		ThirstHandler thirstHandler = (ThirstHandler)ThirstHelper.getThirstData(player);
		thirstHandler.addStats(ConfigHandler.configData.thirst_value, ConfigHandler.configData.hydration_value);
		
		boolean giveEffect = false;
		
		if (!ConfigHandler.configData.make_whitelist_blacklist) {
			giveEffect = Arrays.asList(potion_ids).contains(getPotionId(stack));
		} else {
			giveEffect = !Arrays.asList(potion_ids).contains(getPotionId(stack));
		}
		
		
		//Give thirst Effect if it's on the list and the Config file enabled it
		if(giveEffect && SyncedConfig.getBooleanValue(GameplayOption.ENABLE_THIRST)) {
			player.addPotionEffect(new PotionEffect(TANPotions.thirst, ConfigHandler.configData.duration));
		}
		
	}
	
	
}
