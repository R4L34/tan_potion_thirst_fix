package com.r4l.tan_potion_thirst_fix.network;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkHandler {
	
	public static SimpleNetworkWrapper channel = new SimpleNetworkWrapper("tan_potion_thirst");
	
	
	public static void init() {
		
		channel.registerMessage(SyncroniseConfig.Handler.class, SyncroniseConfig.Message.class, 0, Side.CLIENT);
		
	}
	
	
	public static IThreadListener getThreadListener(MessageContext ctx) {
		return ctx.side == Side.SERVER ? (WorldServer) ctx.getServerHandler().player.world : getClientThreadListener();
	}
	
	@SideOnly(Side.CLIENT)
	public static IThreadListener getClientThreadListener() {
		return Minecraft.getMinecraft();
	}

}
