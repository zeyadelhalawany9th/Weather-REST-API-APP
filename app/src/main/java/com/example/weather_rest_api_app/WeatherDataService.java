package com.example.weather_rest_api_app;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    Context context;
    String cityID;

    public WeatherDataService(Context context)
    {
        this.context = context;
    }


    public interface cityIDResponseListener
    {
        void onError(String message);

        void onResponse(String cityID);

    }

    public void getCityID(String cityName, cityIDResponseListener cityIDResponseListener)
    {
        String url = "https://www.metaweather.com/api/location/search/?query=" + cityName;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject cityInfo;
                cityID = "";

                try
                {
                    cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                //Toast.makeText(context, cityID, Toast.LENGTH_SHORT).show();

                cityIDResponseListener.onResponse(cityID);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(context, "Error Occurred!", Toast.LENGTH_SHORT).show();
                cityIDResponseListener.onError("Error Occurred!");


            }
        });

        // Add a request to the RequestQueue.
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


    public interface cityWeatherByIDResponseListener
    {
        void onError(String message);

        void onResponse(WeatherReport weatherReport);

    }


    public void getWeatherReportByCityID(String cityID, cityWeatherByIDResponseListener cityWeatherByIDResponseListener)
    {
        List<WeatherReport> weatherReports = new ArrayList<>();

        String url = "https://www.metaweather.com/api/location/" + cityID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {

                // Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                try
                {
                    JSONArray consolidatedWeatherList = response.getJSONArray("consolidated_weather");

                    WeatherReport dayOne = new WeatherReport();

                    JSONObject dayOneProps = consolidatedWeatherList.getJSONObject(0);

                    dayOne.setId(dayOneProps.getInt("id"));
                    dayOne.setWeather_state_name(dayOneProps.getString("weather_state_name"));
                    dayOne.setWeather_state_abbr(dayOneProps.getString("weather_state_abbr"));
                    dayOne.setWind_direction_compass(dayOneProps.getString("wind_direction_compass"));
                    dayOne.setCreated(dayOneProps.getString("created"));
                    dayOne.setApplicable_date(dayOneProps.getString("applicable_date"));
                    dayOne.setMin_temp(dayOneProps.getLong("min_temp"));
                    dayOne.setMax_temp(dayOneProps.getLong("max_temp"));
                    dayOne.setThe_temp(dayOneProps.getLong("the_temp"));
                    dayOne.setWind_speed(dayOneProps.getLong("wind_speed"));
                    dayOne.setWind_direction(dayOneProps.getLong("wind_direction"));
                    dayOne.setAir_pressure(dayOneProps.getInt("air_pressure"));
                    dayOne.setHumidity(dayOneProps.getInt("humidity"));
                    dayOne.setVisibility(dayOneProps.getLong("visibility"));
                    dayOne.setPredictability(dayOneProps.getInt("predictability"));

                    cityWeatherByIDResponseListener.onResponse(dayOne);



                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                cityWeatherByIDResponseListener.onError("Error Occurred!");


            }
        });

        // Add a request to the RequestQueue.
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


//
//
//    public List<WeatherReport> getWeatherReportByCityName(String cityName)
//    {
//
//    }




}
