package com.example.my49ersense;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    ImageButton btn;
    TextView weather;

    MaterialButton ctrlLights;
    MaterialButton ctrlVideos;
    MaterialButton btnWeatherSts;

    private final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String appId = "6469afdcd8abb7d10a31774fbd8936a4";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
        getWeatherDetails();        // Display weather details on create.

        weather = (TextView) findViewById(R.id.weatherStatus);
        btnWeatherSts = (MaterialButton) findViewById(R.id.btnWeatherStatus);
        btnWeatherSts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherDetails();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        txt = (TextView) findViewById(R.id.homeStatus);

        btn = (ImageButton) findViewById(R.id.btnDisarmed);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Disarmed!");
            }
        });

        btn = (ImageButton) findViewById(R.id.btnHomeArm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Home & Armed!");
            }
        });

        btn = (ImageButton) findViewById(R.id.btnAwayArm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Away & Armed!");
            }
        });

        ctrlLights = (MaterialButton) findViewById(R.id.controlLights);
        ctrlLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openControlLightsActivity();
            }
        });

        ctrlVideos = (MaterialButton) findViewById(R.id.videos);
        ctrlVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideosActivity();
            }
        });
    }

    public void getWeatherDetails() {
        String city = "Charlotte".trim();
        String tempUrl = URL + "?q=" + city + "&appId=" + appId;
        Log.i("VIDEO_RECORD_TAG", "Weather: Hello");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    Log.i("VIDEO_RECORD_TAG", "Weather: " + jsonArray);
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");

                    output += city + "(" + countryName + ")" + "\nTemp: " + df.format(temp) + "°C" +
                            "/ Feels Like: " + df.format(feelsLike) + "°C" +
                            "\n Description: " + description;

                    weather.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void openControlLightsActivity() {
        Intent intent = new Intent(this, ControlLights.class);
        startActivity(intent);
    }

    public void openVideosActivity() {
        Intent intent = new Intent(this, VediosActivity.class);
        startActivity(intent);
    }
}