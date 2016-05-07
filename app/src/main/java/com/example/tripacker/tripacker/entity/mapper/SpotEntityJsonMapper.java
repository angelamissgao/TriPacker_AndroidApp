package com.example.tripacker.tripacker.entity.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Class used to transform from Strings representing json to valid objects.
 */

public class SpotEntityJsonMapper {

    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
             JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable)object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }
}
