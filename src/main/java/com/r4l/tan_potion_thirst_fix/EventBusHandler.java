package com.r4l.tan_potion_thirst_fix;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import toughasnails.handler.thirst.DrinkHandler;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public final class EventBusHandler {

    @SuppressWarnings("unchecked")
    private static ConcurrentHashMap<Object, ?> getListeners(EventBus bus) {
        try {
            Field f = EventBus.class.getDeclaredField("listeners");
            f.setAccessible(true);
            return (ConcurrentHashMap<Object, ?>) f.get(bus);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to access EventBus.listeners", e);
        }
    }

    public static int unregisterDrinkHandler() {
        int removed = 0;

        removed += unregister(MinecraftForge.EVENT_BUS);

        return removed;
    }

    private static int unregister(EventBus bus) {
        int removed = 0;
        ConcurrentHashMap<Object, ?> listeners = getListeners(bus);

        for (Object target : listeners.keySet()) {
            if (target instanceof DrinkHandler) {
                bus.unregister(target);
                removed++;
            }
        }
        return removed;
    }
}

