package com.r4l.tan_potion_thirst_fix;

import com.r4l.tan_potion_thirst_fix.config.ConfigHandler;
import com.r4l.tan_potion_thirst_fix.network.NetworkHandler;
import com.r4l.tan_potion_thirst_fix.network.SyncroniseConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import toughasnails.api.TANPotions;
import toughasnails.api.config.GameplayOption;
import toughasnails.api.config.SyncedConfig;
import toughasnails.api.item.ItemDrink;
import toughasnails.api.stat.capability.IThirst;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.config.json.DrinkData;
import toughasnails.handler.thirst.DrinkHandler;
import toughasnails.init.ModConfig;
import toughasnails.thirst.ThirstHandler;

public class FixedDrinkHandler extends DrinkHandler{
	
	//Overriding one method
	@Override
	@SubscribeEvent
    public void onItemUseFinish(LivingEntityUseItemEvent.Finish event){
        if (event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            ItemStack stack = event.getItem();
            ThirstHandler thirstHandler = (ThirstHandler)ThirstHelper.getThirstData(player);

            if (thirstHandler.isThirsty()){
                boolean zeroStack = false;

                if (stack.getCount() <= 0){
                    stack.setCount(1);
                    zeroStack = true;
                }
                
                //A replacement function that takes the mod's settings into account
                PotionHandler.applyPotionRules(player, stack);

                
                if (!(stack.getItem() instanceof ItemDrink)){
                    String registryName = stack.getItem().getRegistryName().toString();

                    if (ModConfig.drinkData.containsKey(registryName)){
                        for (DrinkData drinkData : ModConfig.drinkData.get(registryName)){
                            if (drinkData.getPredicate().apply(stack)){
                                applyDrinkFromData(player, drinkData);
                                break;
                            }
                        }
                    }
                }

                if (zeroStack) stack.setCount(0);
            }
        }
    }
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		NetworkHandler.channel.sendTo(new SyncroniseConfig.Message(ConfigHandler.configData), (EntityPlayerMP) event.player);
	}
	
	
	private static void applyDrinkFromData(final EntityPlayer player, final DrinkData data){
	        applyDrink(player, data.getThirstRestored(), data.getHydrationRestored(), data.getPoisonChance());
	}

	 
	private static void applyDrink(final EntityPlayer player, final int thirstRestored, final float hydrationRestored, final float poisonChance){
	    IThirst thirstStats = ThirstHelper.getThirstData(player);
	    thirstStats.addStats(thirstRestored, hydrationRestored);

	    if (!player.world.isRemote && (player.world.rand.nextFloat() < poisonChance) && SyncedConfig.getBooleanValue(GameplayOption.ENABLE_THIRST)){
	        player.addPotionEffect(new PotionEffect(TANPotions.thirst, 600));
	    }
	}
}
