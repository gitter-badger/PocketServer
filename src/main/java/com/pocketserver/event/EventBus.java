package com.pocketserver.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {
    private final Map<Class<?>, List<EventData>> eventListeners;

    public EventBus() {
        this.eventListeners = new ConcurrentHashMap<>();
    }

    public void registerListener(Object listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Listener.class)) {
                continue;
            }
            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length == 0) {
                continue;
            }

            Class<?> type = parameters[0];
            List<EventData> dataList = eventListeners.containsKey(type) ? new ArrayList<EventData>() : eventListeners.get(type);
            EventData data = new EventData(listener, method);
            dataList.add(data);
            eventListeners.put(type, dataList);
        }
    }

    public void post(Event event) {
        for (Class<?> aClass : eventListeners.keySet()) {
            if (!aClass.isInstance(event)) {
                continue;
            }
            List<EventData> dataList = eventListeners.get(aClass);
            for (EventData eventData : dataList) {
                Method method = eventData.getMethod();
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                try {
                    method.invoke(eventData.getObject(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class EventData {
        private final Object object;
        private final Method method;

        public EventData(Object object, Method method) {
            this.object = object;
            this.method = method;
        }

        public Object getObject() {
            return object;
        }

        public Method getMethod() {
            return method;
        }
    }
}
