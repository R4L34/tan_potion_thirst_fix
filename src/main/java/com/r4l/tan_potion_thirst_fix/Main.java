package com.r4l.tan_potion_thirst_fix;

import com.r4l.tan_potion_thirst_fix.config.ConfigHandler;
import com.r4l.tan_potion_thirst_fix.network.NetworkHandler;
import com.r4l.tan_potion_thirst_fix.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCY, acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSION)
public class Main {

	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;

	
	//Event Handlers
	@EventHandler
	public static void preInit (FMLPreInitializationEvent event) {
		NetworkHandler.init();
		ConfigHandler.registerConfig(event);
	}
	
	@EventHandler
	public static void init (FMLInitializationEvent event) {
	}
	
	@EventHandler
	public static void postInit (FMLPostInitializationEvent event) {
		EventBusHandler.unregisterDrinkHandler();
		MinecraftForge.EVENT_BUS.register(new FixedDrinkHandler());
	}
}