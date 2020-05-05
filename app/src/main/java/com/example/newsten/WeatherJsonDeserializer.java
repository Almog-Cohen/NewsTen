package com.example.newsten;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class WeatherJsonDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Weather> weathers = null;

        try{
            JsonObject jsonObject = json.getAsJsonObject();
            String city = jsonObject.get("city_name").getAsString();
            String countryCode = jsonObject.get("country_code").getAsString();
            JsonArray forecastsJsonArray = jsonObject.getAsJsonArray("data");
            weathers = new ArrayList<>(forecastsJsonArray.size());
            for (int i = 0; i < forecastsJsonArray.size(); i++) {
                JsonObject forecastObject = forecastsJsonArray.get(i).getAsJsonObject();
                JsonObject weatherObject = forecastObject.get("weather").getAsJsonObject();
                Weather dematerialized = context.deserialize(forecastObject, Weather.class);
                weathers.add(dematerialized);
                weathers.get(i).setForecastTitle(city);
                weathers.get(i).setCountryCode(countryCode);
                weathers.get(i).setForecastIcon(weatherObject.get("icon").getAsString());
                weathers.get(i).setWeatherDesc(weatherObject.get("description").getAsString());
            }
        }catch (JsonParseException jpe){

        }

        return weathers;
    }
}
