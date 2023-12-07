package uk.ac.rgu.cm2115;

import java.util.HashMap;
import java.util.Map;

public class AppState {
    private static AppState instance = null;
    private Map<String, Object> contextData;

    private AppState() {
        contextData = new HashMap<>();
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public Object get(String key) {
        return contextData.get(key);
    }

    public void put(String key, Object value) {
        contextData.put(key, value);
    }
}