package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class Sensors extends AppCompatActivity {

    private MaterialButton btnUpdate;
    String lrL1 = "off", lrL2 = "off", lrMd = "off", kitL1 = "off", kitL2 = "off", kitMd = "off",
            mb1L1 = "off", mb1L2 = "off", mb1Md = "off", mb2L1 = "off", mb2L2 = "off", mb2Md = "off";
    Switch lrSw1, lrSw2, lrSw3, kitSw1, kitSw2, kitSw3, mb1Sw1, mb1Sw2, mb1Sw3, mb2Sw1, mb2Sw2, mb2Sw3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        lrSw1 = (Switch) findViewById(R.id.lrSwitch1);
        lrSw2 = (Switch) findViewById(R.id.lrSwitch2);
        lrSw3 = (Switch) findViewById(R.id.lrMDSwitch);
        kitSw1 = (Switch) findViewById(R.id.kitSwitch1);
        kitSw2 = (Switch) findViewById(R.id.kitSwitch2);
        kitSw3 = (Switch) findViewById(R.id.kitMDSwitch);
        mb1Sw1 = (Switch) findViewById(R.id.mb1Switch1);
        mb1Sw2 = (Switch) findViewById(R.id.mb1Switch2);
        mb1Sw3 = (Switch) findViewById(R.id.mb1MDSwitch);
        mb2Sw1 = (Switch) findViewById(R.id.mb2Switch1);
        mb2Sw2 = (Switch) findViewById(R.id.mb2Switch2);
        mb2Sw3 = (Switch) findViewById(R.id.mb2MDSwitch);

        getStatusSensors();

        lrSw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lrSw1.isChecked())
                    lrL1 = "on";
                else
                    lrL1 = "off";
            }
        });

        lrSw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lrSw2.isChecked())
                    lrL2 = "on";
                else
                    lrL2 = "off";
            }
        });

        lrSw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lrSw3.isChecked())
                    lrMd = "on";
                else
                    lrMd = "off";
            }
        });

        kitSw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kitSw1.isChecked())
                    kitL1 = "on";
                else
                    kitL1 = "off";
            }
        });

        kitSw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kitSw2.isChecked())
                    kitL2 = "on";
                else
                    kitL2 = "off";
            }
        });

        kitSw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kitSw2.isChecked())
                    kitMd = "on";
                else
                    kitMd = "off";
            }
        });

        mb1Sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb1Sw1.isChecked())
                    mb1L1 = "on";
                else
                    mb1L1 = "off";
            }
        });

        mb1Sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb1Sw2.isChecked())
                    mb1L2 = "on";
                else
                    mb1L2 = "off";
            }
        });

        mb1Sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb1Sw3.isChecked())
                    mb1Md = "on";
                else
                    mb1Md = "off";
            }
        });

        mb2Sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb2Sw1.isChecked())
                    mb2L1 = "on";
                else
                    mb2L1 = "off";
            }
        });

        mb2Sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb2Sw2.isChecked())
                    mb2L2 = "on";
                else
                    mb2L2 = "off";
            }
        });

        mb2Sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb2Sw3.isChecked())
                    mb2Md = "on";
                else
                    mb2Md = "off";
            }
        });

        btnUpdate = (MaterialButton) findViewById(R.id.btnUpdateSensors);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[12];
                        field[0] = "lrSensor1";
                        field[1] = "lrSensor2";
                        field[2] = "lrMd";
                        field[3] = "kitSensor1";
                        field[4] = "kitSensor2";
                        field[5] = "kitMd";
                        field[6] = "mb1Sensor1";
                        field[7] = "mb1Sensor2";
                        field[8] = "mb1Md";
                        field[9] = "mb2Sensor1";
                        field[10] = "mb2Sensor2";
                        field[11] = "mb2Md";

                        String[] data = new String[12];
                        data[0] = lrL1;
                        data[1] = lrL2;
                        data[2] = lrMd;
                        data[3] = kitL1;
                        data[4] = kitL2;
                        data[5] = kitMd;
                        data[6] = mb1L1;
                        data[7] = mb1L2;
                        data[8] = mb1Md;
                        data[9] = mb2L1;
                        data[10] = mb2L2;
                        data[11] = mb2Md;

                        PutData putData = new PutData("http://192.168.50.59/LoginRegister/updateSensorsStatus.php", "POST", field, data);

                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Sensors Status Update Success")) {
                                    Toast.makeText(Sensors.this, result, Toast.LENGTH_SHORT).show();
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

    public void getStatusSensors()
    {
        String[] field = new String[0];
        String[] data = new String[0];

        PutData putData = new PutData("http://192.168.50.59/LoginRegister/getSensorsStatus.php", "POST", field, data);

        if(putData.startPut()) {
            if(putData.onComplete()) {
                String result = putData.getResult();
                if(!result.equals("Error: Database connection")) {
                    Log.i("SENSORS_RECORD_TAG", "Retrieved data from mysql is: " + result);

                    try {
                        JSONObject jObject = new JSONObject(result);
                        String errorStatus = jObject.getString("error");

                        if(errorStatus == "false") {
                            JSONObject messageJson = jObject.getJSONObject("message");

                            if(messageJson.getString("lrSensor1").equals("on")) {
                                lrSw1.setChecked(true);
                                lrL1 = "on";
                            }
                            else {
                                lrSw1.setChecked(false);
                                lrL1 = "off";
                            }
                            if(messageJson.getString("lrSensor2").equals("on")) {
                                lrSw2.setChecked(true);
                                lrL2 = "on";
                            }
                            else {
                                lrSw2.setChecked(false);
                                lrL2 = "off";
                            }
                            if(messageJson.getString("lrMd").equals("on")) {
                                lrSw3.setChecked(true);
                                lrMd = "on";
                            }
                            else {
                                lrSw3.setChecked(false);
                                lrMd = "off";
                            }
                            if(messageJson.getString("kitSensor1").equals("on")) {
                                kitSw1.setChecked(true);
                                kitL1 = "on";
                            }
                            else {
                                kitSw1.setChecked(false);
                                kitL1 = "off";
                            }
                            if(messageJson.getString("kitSensor2").equals("on")) {
                                kitSw2.setChecked(true);
                                kitL2 = "on";
                            }
                            else {
                                kitSw2.setChecked(false);
                                kitL2 = "off";
                            }
                            if(messageJson.getString("kitMd").equals("on")) {
                                kitSw3.setChecked(true);
                                kitMd = "on";
                            }
                            else {
                                kitSw3.setChecked(false);
                                kitMd = "off";
                            }
                            if(messageJson.getString("mb1Sensor1").equals("on")) {
                                mb1Sw1.setChecked(true);
                                mb1L1 = "on";
                            }
                            else {
                                mb1Sw1.setChecked(false);
                                mb1L1 = "off";
                            }
                            if(messageJson.getString("mb1Sensor2").equals("on")) {
                                mb1Sw2.setChecked(true);
                                mb1L2 = "on";
                            }
                            else {
                                mb1Sw2.setChecked(false);
                                mb1L2 = "off";
                            }
                            if(messageJson.getString("mb1Md").equals("on")) {
                                mb1Sw3.setChecked(true);
                                mb1Md = "on";
                            }
                            else {
                                mb1Sw3.setChecked(false);
                                mb1Md = "off";
                            }
                            if(messageJson.getString("mb2Sensor1").equals("on")) {
                                mb2Sw1.setChecked(true);
                                mb2L1 = "on";
                            }
                            else {
                                mb2Sw1.setChecked(false);
                                mb2L1 = "off";
                            }
                            if(messageJson.getString("mb2Sensor2").equals("on")) {
                                mb2Sw2.setChecked(true);
                                mb2L2 = "on";
                            }
                            else {
                                mb2Sw2.setChecked(false);
                                mb2L2 = "off";
                            }
                            if(messageJson.getString("mb2Md").equals("on")) {
                                mb2Sw3.setChecked(true);
                                mb2Md = "on";
                            }
                            else {
                                mb2Sw3.setChecked(false);
                                mb2Md = "off";
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