package com.example.weather_rest_api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.List;

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

        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);



        getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                weatherDataService.getCityID(dataInput.getText().toString(), new WeatherDataService.cityIDResponseListener() {
                    @Override
                    public void onError(String message)
                    {

                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String cityID)
                    {

                        Toast.makeText(MainActivity.this, "The ID Returned is: "+cityID, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });




        getWeatherByCityIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                weatherDataService.getWeatherReportByCityID(dataInput.getText().toString(), new WeatherDataService.cityWeatherByIDResponseListener() {
                    @Override
                    public void onError(String message)
                    {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReport> weatherReports)
                    {
                        // put the list in a list view
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReports);

                        weatherReportsList.setAdapter(arrayAdapter);
                    }
                });


            }
        });




        getWeatherByCityNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}