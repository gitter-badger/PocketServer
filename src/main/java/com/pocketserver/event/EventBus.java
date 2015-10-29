package com.pocketserver.event;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {
	
    private final Map<Class<?>, Set<EventData>> eventListeners;

    public EventBus() {
        this.eventListeners = new ConcurrentHashMap<>();
    }

    public void registerListener(Object listener) {
    	if (listener == null)
    		return;
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Listener.class) || method.getReturnType() != void.class) {
                continue;
            }
            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1 || !Event.class.isAssignableFrom(parameters[0])) {
                continue;
            }
            Class<?> type = parameters[0];
            Set<EventData> dataList = eventListeners.containsKey(type) ? eventListeners.get(type) : new HashSet<EventData>();
            EventData data = new EventData(listener, method);
            dataList.add(data);
            eventListeners.put(type, dataList);
        }
    }

    public <T extends Event> T post(T event) {
    	if (event == null)
    		return null;
        for (Entry<Class<?>, Set<EventData>> entry : eventListeners.entrySet()) {
            if (entry.getKey() != event.getClass()) {
                continue;
            }
            for (EventData eventData : entry.getValue()) {
                eventData.invoke(event);
            }
        }
        return event;
    }

    private class EventData {
    	
        private final Object object;
        private final Method method;

        public EventData(Object object, Method method) {
            this.object = object;
            this.method = method;
        }
        
        public void invoke(Event event) {
        	if (method == null || method.getReturnType() != void.class || method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(method.getParameterTypes()[0]))
        		return;
        	if (!method.isAccessible())
        		method.setAccessible(true);
        	try {
        		method.invoke(object, event);
        	} catch (Exception ex) {}
        }
        
    }
    
}
