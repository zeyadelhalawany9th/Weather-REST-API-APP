package com.example.weather_rest_api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button getCityID, getWeatherByCityIDBtn, getWeatherByCityNameBtn;

    EditText dataInput;

    ListView weatherReportsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getCityID = findViewById(R.id.getCityIDBtn);
        getWeatherByCityIDBtn = findViewById(R.id.getWeatherByCityIDBtn);
        getWeatherByCityNameBtn = findViewById(R.id.getWeatherByCityNameBtn);

        dataInput = findViewById(R.id.dataInput);

        weatherReportsList = findViewById(R.id.weatherReportsList);




        getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://www.metaweather.com/api/location/search/?query=" + dataInput.getText().toString();

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject cityInfo;
                        String cityID = "";

                        try
                        {
                            cityInfo = response.getJSONObject(0);
                            cityID = cityInfo.getString("woeid");

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this, cityID, Toast.LENGTH_SHORT).show();
                        

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                    }
                });

                // Add a request to the RequestQueue.
                RequestQueueSingleton.getInstance(MainActivity.this).addToRequestQueue(jsonArrayRequest);

            }
        });




        getWeatherByCityIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        getWeatherByCityNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}