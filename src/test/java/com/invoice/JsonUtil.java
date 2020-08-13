package com.invoice;

import com.google.gson.Gson;

public class JsonUtil {
    /**
     * This function converts the given object into JSON.
     *
     * @param source Object needed to be converted to json.
     * @return
     */
    public static String convertToJson(Object source) {

        Gson gson = new Gson();
        return gson.toJson(source);
    }

    /**
     * This function converts the json back to java object based on the given class.
     *
     * @param json  Input json.
     * @param clazz Class type of the target object.
     * @param <T>   Data type of the target object.
     * @return
     */
    public static <T> T convertFromJson(String json, Class<T> clazz) {

        Gson gson = new Gson();
        return gson.fromJson(json, clazz);

    }
}

