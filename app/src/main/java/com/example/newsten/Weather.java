package com.example.newsten;


import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {

    private String forecastCity;

    private String weatherDesc;
    private String forecastIcon;

    @SerializedName(value = "valid_date")
    private String date;

    @SerializedName(value = "max_temp")
    private String forecastMaxDegrees;

    @SerializedName(value = "min_temp")
    private String forecastMinDegrees;

    @SerializedName(value = "country_code")
    private String countryCode;

    @SerializedName(value = "temp")
    private String avgTemp;

    @SerializedName(value = "wind_cdir")
    private String windDirection;

    @SerializedName(value = "wind_spd")
    private String windSpeed;

    @SerializedName(value = "pop")
    private String propOfPrecip;

    @SerializedName(value = "precip")
    private String precip;


    public Weather(String forecastTitle, String forecastDegrees) {
        this.forecastCity = forecastTitle;
        this.avgTemp = forecastDegrees;
    }

    public Weather(String forecastIcon, String forecastTitle, String forecastDegrees) {
        this.forecastIcon = forecastIcon;
        this.forecastCity = forecastTitle;
        this.avgTemp = forecastDegrees;
    }

    public String getDayOfTheWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String day = null;
        try {
            Date currentDate = sdf.parse(date);
            DateFormat dayFormat = new SimpleDateFormat("EEEE");
            day = dayFormat.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;

    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getForecastIcon() {
        return forecastIcon;
    }

    public String getForecastTitle() {
        return forecastCity;
    }

    public String getForecastMaxDegrees() {
        return forecastMaxDegrees;
    }

    public String getForecastMinDegrees() {
        return forecastMinDegrees;
    }

    public String getForecastCity() {
        return forecastCity;
    }

    public String getDate() {
        return date;
    }

    public String getAvgTemp() {
        return avgTemp;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
    public void setForecastIcon(String forecastIcon) {
        this.forecastIcon = forecastIcon;
    }
    public void setForecastTitle(String forecastTitle) {
        this.forecastCity = forecastTitle;
    }
    public void setForecastMaxDegrees(String forecastMaxDegrees) {
        this.forecastMaxDegrees = forecastMaxDegrees;
    }
    public void setForecastMinDegrees(String forecastMinDegrees) {
        this.forecastMinDegrees = forecastMinDegrees;
    }

    private String hebrewDay(String dayOfTheWeek) {
        switch (dayOfTheWeek) {
            case "Sunday":
                return "ראשון";
            case "Monday":
                return "שני";
            case "Tuesday":
                return "שלישי";
            case "Wednesday":
                return "רביעי";
            case "Thursday":
                return "חמישי";
            case "Friday":
                return "שישי";
            case "Saturday":
                return "שבת";
        }
        return "";
    }
}
