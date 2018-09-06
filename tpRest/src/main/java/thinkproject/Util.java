/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinkproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 *
 * @author joost.meulenkamp
 */
public class Util {

    static JsonParser JSON = new JsonParser();
    static Gson GSON = new GsonBuilder().create();
    
    public static String getProperty(String json, String property) {
        JsonElement element = JSON.parse(json);
        if (element.isJsonObject()) {
            return element.getAsJsonObject().get(property).toString();
        } else {
            return null;
        }
    }
    
    public static String toJson(Object value){
        return GSON.toJson(value);
    }
}