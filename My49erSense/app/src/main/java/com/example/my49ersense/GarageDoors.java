package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class GarageDoors extends AppCompatActivity {

    String car1Door = "close", car2Door = "close";
    Spinner car1Sp, car2Sp;

    MaterialButton btnUpdateDoors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_doors);

        car1Sp = findViewById(R.id.car1Spinner);
        car2Sp = findViewById(R.id.car2Spinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.GrDoors, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        car1Sp.setAdapter(adapter1);
        car1Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                car1Door = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.GrDoors, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        car2Sp.setAdapter(adapter2);
        car2Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                car2Door = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        getGrDoorsStatus();

        btnUpdateDoors = (MaterialButton) findViewById(R.id.btnUpdateGrDoors);
        btnUpdateDoors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "car1Door";
                        field[1] = "car2Door";

                        String[] data = new String[8];
                        data[0] = car1Door;
                        data[1] = car2Door;

                        PutData putData = new PutData(Globals.PHP_URL+"updateGrDoorStatus.php", "POST", field, data);

                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Garage Door Status Update Success")) {
                                    Toast.makeText(GarageDoors.this, result, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        // End write and read data with URL
                    }
                });
            }
        });
    }

    public void getGrDoorsStatus()
    {
        String[] field = new String[0];
        String[] data = new String[0];

        PutData putData = new PutData(Globals.PHP_URL+"getGrDoorStatus.php", "POST", field, data);

        if(putData.startPut()) {
            if(putData.onComplete()) {
                String result = putData.getResult();
                if(!result.equals("Error: Database connection")) {
                    Log.i("VIDEO_RECORD_TAG", "Retrieved data from mysql is: " + result);

                    try {
                        JSONObject jObject = new JSONObject(result);
                        String errorStatus = jObject.getString("error");

                        if(errorStatus == "false") {
                            JSONObject messageJson = jObject.getJSONObject("message");

                            if(messageJson.getString("car1Door").equals("Open")) {
                                car1Sp.setSelection(0);
                                car1Door = "Open";
                            }
                            else {
                                car1Sp.setSelection(1);
                                car1Door = "Close";
                            }
                            if(messageJson.getString("car2Door").equals("Open")) {
                                car2Sp.setSelection(0);
                                car2Door = "Open";
                            }
                            else {
                                car2Sp.setSelection(1);
                                car2Door = "Close";
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}