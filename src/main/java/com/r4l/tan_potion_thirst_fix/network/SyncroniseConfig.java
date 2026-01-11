package com.r4l.tan_potion_thirst_fix.network;


import com.r4l.tan_potion_thirst_fix.config.ConfigData;
import com.r4l.tan_potion_thirst_fix.config.ConfigHandler;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncroniseConfig {
	
	public static class Message implements IMessage{
		
		private ConfigData data;
		
		public Message () {}
		
		public Message (ConfigData data) {
			this.data = data;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			data = ConfigData.readBuf(buf);
		}

		@Override
		public void toBytes(ByteBuf buf) {
			data.writeBuf(buf);
		}

		public ConfigData getData() {
			return data;
		}
		
	}
	
	
	public static class Handler implements IMessageHandler<Message, IMessage>{

		@Override
		public IMessage onMessage(Message message, MessageContext ctx) {
			NetworkHandler.getThreadListener(ctx).addScheduledTask(() -> {
				
				ConfigHandler.configData = new ConfigData(message.getData());
				
			});
			return null;
		}
	}

}
