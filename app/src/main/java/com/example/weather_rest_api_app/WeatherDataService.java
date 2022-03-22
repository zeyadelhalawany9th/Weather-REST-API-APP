package com.example.weather_rest_api_app;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherDataService {

    Context context;
    String cityID;

    public WeatherDataService(Context context)
    {
        this.context = context;
    }


    public interface VolleyResponseListener
    {
        void onError(String message);

        void onResponse(String cityID);

    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener)
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

                volleyResponseListener.onResponse(cityID);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(context, "Error Occurred!", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Error Occurred!");


            }
        });

        // Add a request to the RequestQueue.
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


//    public List<WeatherReport> getWeatherReportByCityID(String cityID)
//    {
//
//    }
//
//
//    public List<WeatherReport> getWeatherReportByCityName(String cityName)
//    {
//
//    }




}
