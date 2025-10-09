package com.example.easdktool.been;

import java.util.List;

public class TempWeather {
//    {"weatherUnit":0,"currentTemperature":20,"place":"Guanzhou","sDayArray":[{"eDayType":0,"eNightType":4,"eAir":0,"eRays":0,"eMoon":5,"minTemperature":10,"maxTemperature":26,"sunriseTimestamp":0,"sunsetTimestamp":0,"minWindPower":2,"maxWindPower":5,"cloudiness":0,"airGrade":0},{"eDayType":8,"eNightType":4,"eAir":2,"eRays":0,"eMoon":5,"minTemperature":15,"maxTemperature":10,"sunriseTimestamp":0,"sunsetTimestamp":0,"minWindPower":3,"maxWindPower":1,"cloudiness":0,"airGrade":0}]}
    public float currentTemperature = 0;
    public String place;
    public int weatherUnit = 0;
    public List<WeatherItem> sDayArray;
}
