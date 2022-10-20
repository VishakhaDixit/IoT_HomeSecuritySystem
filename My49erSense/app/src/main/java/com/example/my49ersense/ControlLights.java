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

public class ControlLights extends AppCompatActivity {

    private MaterialButton btnUpdate;
    String lrL1 = "off", lrL2 = "off", kitL1 = "off", kitL2 = "off",
            mb1L1 = "off", mb1L2 = "off", mb2L1 = "off", mb2L2 = "off";
    Switch lrS1, lrS2, kitS1, kitS2, mb1S1, mb1S2, mb2S1, mb2S2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_lights);

        lrS1 = (Switch) findViewById(R.id.lrSwitch1);
        lrS2 = (Switch) findViewById(R.id.lrSwitch2);
        kitS1 = (Switch) findViewById(R.id.kitSwitch1);
        kitS2 = (Switch) findViewById(R.id.kitSwitch2);
        mb1S1 = (Switch) findViewById(R.id.mb1Switch1);
        mb1S2 = (Switch) findViewById(R.id.mb1Switch2);
        mb2S1 = (Switch) findViewById(R.id.mb2Switch1);
        mb2S2 = (Switch) findViewById(R.id.mb2Switch2);

        getStatus();

        lrS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lrS1.isChecked())
                    lrL1 = "on";
                else
                    lrL1 = "off";
            }
        });

        lrS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lrS2.isChecked())
                    lrL2 = "on";
                else
                    lrL2 = "off";
            }
        });

        kitS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kitS1.isChecked())
                    kitL1 = "on";
                else
                    kitL1 = "off";
            }
        });

        kitS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kitS2.isChecked())
                    kitL2 = "on";
                else
                    kitL2 = "off";
            }
        });

        mb1S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb1S1.isChecked())
                    mb1L1 = "on";
                else
                    mb1L1 = "off";
            }
        });

        mb1S2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb1S2.isChecked())
                    mb1L2 = "on";
                else
                    mb1L2 = "off";
            }
        });

        mb2S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb2S1.isChecked())
                    mb2L1 = "on";
                else
                    mb2L1 = "off";
            }
        });

        mb2S2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mb2S2.isChecked())
                    mb2L2 = "on";
                else
                    mb2L2 = "off";
            }
        });

        btnUpdate = (MaterialButton) findViewById(R.id.btnUpdateLights);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[8];
                        field[0] = "lrBulb1";
                        field[1] = "lrBulb2";
                        field[2] = "kitBulb1";
                        field[3] = "kitBulb2";
                        field[4] = "mb1Bulb1";
                        field[5] = "mb1Bulb2";
                        field[6] = "mb2Bulb1";
                        field[7] = "mb2Bulb2";

                        String[] data = new String[8];
                        data[0] = lrL1;
                        data[1] = lrL2;
                        data[2] = kitL1;
                        data[3] = kitL2;
                        data[4] = mb1L1;
                        data[5] = mb1L2;
                        data[6] = mb2L1;
                        data[7] = mb2L2;

                        PutData putData = new PutData("http://192.168.50.59/LoginRegister/updateLightsStatus.php", "POST", field, data);

                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Lights Status Update Success")) {
                                    Toast.makeText(ControlLights.this, result, Toast.LENGTH_SHORT).show();
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

    public void getStatus()
    {
        String[] field = new String[0];
        String[] data = new String[0];

        PutData putData = new PutData("http://192.168.50.59/LoginRegister/getLightsStatus.php", "POST", field, data);

        if(putData.startPut()) {
            if(putData.onComplete()) {
                String result = putData.getResult();
                if(!result.equals("Error: Database connection")) {
                    // Retrieved data from mysql is in the format: {"error":false,"message":{"id":"6","lrBulb1":"off","lrBulb2":"on","kitBulb1":"on","kitBulb2":"off","mb1Bulb1":"off","mb1Bulb2":"on","mb2Bulb1":"off","mb2Bulb2":"off"}}
                    Log.i("VIDEO_RECORD_TAG", "Retrieved data from mysql is: " + result);

                    try {
                        JSONObject jObject = new JSONObject(result);
                        String errorStatus = jObject.getString("error");

                        if(errorStatus == "false") {
                            JSONObject messageJson = jObject.getJSONObject("message");

                            if(messageJson.getString("lrBulb1").equals("on")) {
                                lrS1.setChecked(true);
                                lrL1 = "on";
                            }
                            else {
                                lrS1.setChecked(false);
                                lrL1 = "off";
                            }
                            if(messageJson.getString("lrBulb2").equals("on")) {
                                lrS2.setChecked(true);
                                lrL2 = "on";
                            }
                            else {
                                lrS2.setChecked(false);
                                lrL2 = "off";
                            }
                            if(messageJson.getString("kitBulb1").equals("on")) {
                                kitS1.setChecked(true);
                                kitL1 = "on";
                            }
                            else {
                                kitS1.setChecked(false);
                                kitL1 = "off";
                            }
                            if(messageJson.getString("kitBulb2").equals("on")) {
                                kitS2.setChecked(true);
                                kitL2 = "on";
                            }
                            else {
                                kitS2.setChecked(false);
                                kitL2 = "off";
                            }
                            if(messageJson.getString("mb1Bulb1").equals("on")) {
                                mb1S1.setChecked(true);
                                mb1L1 = "on";
                            }
                            else {
                                mb1S1.setChecked(false);
                                mb1L1 = "off";
                            }
                            if(messageJson.getString("mb1Bulb2").equals("on")) {
                                mb1S2.setChecked(true);
                                mb1L2 = "on";
                            }
                            else {
                                mb1S2.setChecked(false);
                                mb1L2 = "off";
                            }
                            if(messageJson.getString("mb2Bulb1").equals("on")) {
                                mb2S1.setChecked(true);
                                mb2L1 = "on";
                            }
                            else {
                                mb2S1.setChecked(false);
                                mb2L1 = "off";
                            }
                            if(messageJson.getString("mb2Bulb2").equals("on")) {
                                mb2S2.setChecked(true);
                                mb2L2 = "on";
                            }
                            else {
                                mb2S2.setChecked(false);
                                mb2L2 = "off";
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