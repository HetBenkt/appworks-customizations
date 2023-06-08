package nl.bos;

import java.util.HashMap;
import java.util.Map;

public class ControllersRegistry {
    private static final Map<String, Object> registeredControllers = new HashMap<>();

    private ControllersRegistry() {
    }

    public static Object get(String controllerName) {
        return registeredControllers.get(controllerName);
    }

    public static void put(String controllerName, Object controller) {
        registeredControllers.put(controllerName, controller);
    }
}
