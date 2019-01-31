package com.assignmenthappyemi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Temperature {

    @SerializedName("location")
    private TemperatureLocation location;
    @SerializedName("current")
    private Current current;
    @SerializedName("forecast")
    private Forecast forecast;

    public TemperatureLocation getLocation() {
        return location;
    }

    public void setLocation(TemperatureLocation location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }


    public static class TemperatureLocation {

        @SerializedName("name")
        private String name;
        @SerializedName("region")
        private String region;
        @SerializedName("country")
        private String country;
        @SerializedName("lat")
        private Double lat;
        @SerializedName("lon")
        private Double lon;
        @SerializedName("tz_id")
        private String tzId;
        @SerializedName("localtime_epoch")
        private Integer localtimeEpoch;
        @SerializedName("localtime")
        private String localtime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public String getTzId() {
            return tzId;
        }

        public void setTzId(String tzId) {
            this.tzId = tzId;
        }

        public Integer getLocaltimeEpoch() {
            return localtimeEpoch;
        }

        public void setLocaltimeEpoch(Integer localtimeEpoch) {
            this.localtimeEpoch = localtimeEpoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }

    }

    public static class Current {

        @SerializedName("last_updated_epoch")
        private Double lastUpdatedEpoch;
        @SerializedName("last_updated")
        private String lastUpdated;
        @SerializedName("temp_c")
        private Double tempC;
        @SerializedName("temp_f")
        private Double tempF;
        @SerializedName("is_day")
        private Integer isDay;
        @SerializedName("wind_mph")
        private Double windMph;
        @SerializedName("wind_kph")
        private Double windKph;
        @SerializedName("wind_degree")
        private Double windDegree;
        @SerializedName("wind_dir")
        private String windDir;
        @SerializedName("pressure_mb")
        private Integer pressureMb;
        @SerializedName("pressure_in")
        private Double pressureIn;
        @SerializedName("precip_mm")
        private Double precipMm;
        @SerializedName("precip_in")
        private Double precipIn;
        @SerializedName("humidity")
        private Double humidity;
        @SerializedName("cloud")
        private Double cloud;
        @SerializedName("feelslike_c")
        private Double feelslikeC;
        @SerializedName("feelslike_f")
        private Double feelslikeF;
        @SerializedName("vis_km")
        private Double visKm;
        @SerializedName("vis_miles")
        private Double visMiles;
        @SerializedName("uv")
        private Double uv;

        public Double getLastUpdatedEpoch() {
            return lastUpdatedEpoch;
        }

        public void setLastUpdatedEpoch(Double lastUpdatedEpoch) {
            this.lastUpdatedEpoch = lastUpdatedEpoch;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public Double getTempC() {
            return tempC;
        }

        public void setTempC(Double tempC) {
            this.tempC = tempC;
        }

        public Double getTempF() {
            return tempF;
        }

        public void setTempF(Double tempF) {
            this.tempF = tempF;
        }

        public Integer getIsDay() {
            return isDay;
        }

        public void setIsDay(Integer isDay) {
            this.isDay = isDay;
        }

        public Double getWindMph() {
            return windMph;
        }

        public void setWindMph(Double windMph) {
            this.windMph = windMph;
        }

        public Double getWindKph() {
            return windKph;
        }

        public void setWindKph(Double windKph) {
            this.windKph = windKph;
        }

        public Double getWindDegree() {
            return windDegree;
        }

        public void setWindDegree(Double windDegree) {
            this.windDegree = windDegree;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public Integer getPressureMb() {
            return pressureMb;
        }

        public void setPressureMb(Integer pressureMb) {
            this.pressureMb = pressureMb;
        }

        public Double getPressureIn() {
            return pressureIn;
        }

        public void setPressureIn(Double pressureIn) {
            this.pressureIn = pressureIn;
        }

        public Double getPrecipMm() {
            return precipMm;
        }

        public void setPrecipMm(Double precipMm) {
            this.precipMm = precipMm;
        }

        public Double getPrecipIn() {
            return precipIn;
        }

        public void setPrecipIn(Double precipIn) {
            this.precipIn = precipIn;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        public Double getCloud() {
            return cloud;
        }

        public void setCloud(Double cloud) {
            this.cloud = cloud;
        }

        public Double getFeelslikeC() {
            return feelslikeC;
        }

        public void setFeelslikeC(Double feelslikeC) {
            this.feelslikeC = feelslikeC;
        }

        public Double getFeelslikeF() {
            return feelslikeF;
        }

        public void setFeelslikeF(Double feelslikeF) {
            this.feelslikeF = feelslikeF;
        }

        public Double getVisKm() {
            return visKm;
        }

        public void setVisKm(Double visKm) {
            this.visKm = visKm;
        }

        public Double getVisMiles() {
            return visMiles;
        }

        public void setVisMiles(Double visMiles) {
            this.visMiles = visMiles;
        }

        public Double getUv() {
            return uv;
        }

        public void setUv(Double uv) {
            this.uv = uv;


        }
    }


    public static class Forecast {

        @SerializedName("forecastday")
        private List<ForecastDay> listOfDays = null;

        public List<ForecastDay> getListOfDays() {
            return listOfDays;
        }

        public void setListOfDays(List<ForecastDay> listOfDays) {
            this.listOfDays = listOfDays;
        }
    }

    public static class ForecastDay {

        @SerializedName("date")
        private String date;
        @SerializedName("date_epoch")
        private Integer dateEpoch;
        @SerializedName("day")
        private Day day;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getDateEpoch() {
            return dateEpoch;
        }

        public void setDateEpoch(Integer dateEpoch) {
            this.dateEpoch = dateEpoch;
        }

        public Day getDay() {
            return day;
        }

        public void setDay(Day day) {
            this.day = day;
        }

    }

    public static class Day {

        @SerializedName("maxtemp_c")
        private Double maxtempC;
        @SerializedName("maxtemp_f")
        private Double maxtempF;
        @SerializedName("mintemp_c")
        private Double mintempC;
        @SerializedName("mintemp_f")
        private Double mintempF;
        @SerializedName("avgtemp_c")
        private Double avgtempC;
        @SerializedName("avgtemp_f")
        private Double avgtempF;
        @SerializedName("maxwind_mph")
        private Double maxwindMph;
        @SerializedName("maxwind_kph")


        public Double getMaxtempC() {
            return maxtempC;
        }

        public void setMaxtempC(Double maxtempC) {
            this.maxtempC = maxtempC;
        }

        public Double getMaxtempF() {
            return maxtempF;
        }

        public void setMaxtempF(Double maxtempF) {
            this.maxtempF = maxtempF;
        }

        public Double getMintempC() {
            return mintempC;
        }

        public void setMintempC(Double mintempC) {
            this.mintempC = mintempC;
        }

        public Double getMintempF() {
            return mintempF;
        }

        public void setMintempF(Double mintempF) {
            this.mintempF = mintempF;
        }

        public Double getAvgtempC() {
            return avgtempC;
        }

        public void setAvgtempC(Double avgtempC) {
            this.avgtempC = avgtempC;
        }

        public Double getAvgtempF() {
            return avgtempF;
        }

        public void setAvgtempF(Double avgtempF) {
            this.avgtempF = avgtempF;
        }

        public Double getMaxwindMph() {
            return maxwindMph;
        }

        public void setMaxwindMph(Double maxwindMph) {
            this.maxwindMph = maxwindMph;
        }
    }
}
