package com.r4l.tan_potion_thirst_fix.config;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class ConfigData {
	
	public String[] potion_ids;
	
	public boolean make_whitelist_blacklist;
	
	public int thirst_value;
	
	public float hydration_value;
	
	public int duration;
	
	public ConfigData() {}
	
	public ConfigData(ConfigData other) {
		potion_ids = other.potion_ids.clone();
		make_whitelist_blacklist = other.make_whitelist_blacklist;
		thirst_value = other.thirst_value;
		hydration_value = other.hydration_value;
		duration = other.duration;
	}
	
	public static ConfigData readBuf(ByteBuf buf) {
		ConfigData data = new ConfigData();
		int count = buf.readInt();
		data.potion_ids = new String[count];
		for (int i = 0; i < count; i++) {
			data.potion_ids[i] = ByteBufUtils.readUTF8String(buf);
		}
		data.make_whitelist_blacklist = buf.readBoolean();
		data.thirst_value = buf.readInt();
		data.hydration_value = buf.readFloat();
		data.duration = buf.readInt();
		
		return data;
	}
	
	public void writeBuf(ByteBuf buf) {
		buf.writeInt(potion_ids.length);
		for (String entry : potion_ids) {
			ByteBufUtils.writeUTF8String(buf, entry);
		}
		buf.writeBoolean(make_whitelist_blacklist);
		buf.writeInt(thirst_value);
		buf.writeFloat(hydration_value);
		buf.writeInt(duration);
	}
}
