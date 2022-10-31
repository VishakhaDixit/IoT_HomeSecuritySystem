package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class Thermostat extends AppCompatActivity {

    String mfMode = "Off", mfFan = "Off", mfCurTemp = "0", mfConTemp = "0",
            upMode = "Off", upFan = "Off", upCurTemp = "0", upConTemp = "0";
    Spinner mfModeSp, mfFanSp, mfConTempSp, upModeSp, upFanSp, upConTempSp;

    MaterialButton btnUpdThermostat;
    EditText curTempValMf;
    EditText curTempValUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);

        mfModeSp = findViewById(R.id.mfSpinnerMode);
        mfFanSp = findViewById(R.id.mfSpinnerFan);
        curTempValMf = findViewById(R.id.mfCurTempVal);
        mfConTempSp = findViewById(R.id.mfSpinnerTemp);
        upModeSp = findViewById(R.id.upSpinnerMode);
        upFanSp = findViewById(R.id.upSpinnerFan);
        curTempValUp = findViewById(R.id.upCurTempVal);
        upConTempSp = findViewById(R.id.upSpinnerTemp);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Mode, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mfModeSp.setAdapter(adapter1);
        mfModeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                mfMode = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Fan, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mfFanSp.setAdapter(adapter2);
        mfFanSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                mfFan = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Temperature, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mfConTempSp.setAdapter(adapter3);
        mfConTempSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                mfConTemp = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Mode, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upModeSp.setAdapter(adapter4);
        upModeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                upMode = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.Fan, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upFanSp.setAdapter(adapter5);
        upFanSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                upFan = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.Temperature, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upConTempSp.setAdapter(adapter6);
        upConTempSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                upConTemp = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        getThermostatStatus();

        btnUpdThermostat = (MaterialButton) findViewById(R.id.btnUpdateTherm);
        btnUpdThermostat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(!curTempValMf.getText().toString().equals(""))
                            mfCurTemp = curTempValMf.getText().toString();
                        if(!curTempValUp.getText().toString().equals(""))
                            upCurTemp = curTempValUp.getText().toString();

                        String[] field = new String[8];
                        field[0] = "mfMode";
                        field[1] = "mfFan";
                        field[2] = "mfCurTemp";
                        field[3] = "mfConTemp";
                        field[4] = "upMode";
                        field[5] = "upFan";
                        field[6] = "upCurTemp";
                        field[7] = "upConTemp";

                        String[] data = new String[8];
                        data[0] = mfMode;
                        data[1] = mfFan;
                        data[2] = mfCurTemp;
                        data[3] = mfConTemp;
                        data[4] = upMode;
                        data[5] = upFan;
                        data[6] = upCurTemp;
                        data[7] = upConTemp;

                        PutData putData = new PutData(Globals.PHP_URL+"updateThermostatStatus.php", "POST", field, data);

                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Thermostat Status Update Success")) {
                                    Toast.makeText(Thermostat.this, result, Toast.LENGTH_SHORT).show();
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

    public void getThermostatStatus()
    {
        String[] field = new String[0];
        String[] data = new String[0];

        PutData putData = new PutData(Globals.PHP_URL+"getThermostatStatus.php", "POST", field, data);

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
                            Log.i("THERMO", "Message: " + messageJson);

                            if(messageJson.getString("mfMode").equals("Heat")) {
                                mfModeSp.setSelection(0);
                                mfMode = "Heat";
                            }
                            else if(messageJson.getString("mfMode").equals("Cool")) {
                                mfModeSp.setSelection(1);
                                mfMode = "Cool";
                            }
                            else {
                                mfModeSp.setSelection(2);
                                mfMode = "Off";
                            }

                            if(messageJson.getString("mfFan").equals("Auto")) {
                                mfFanSp.setSelection(0);
                                mfFan = "Auto";
                            }
                            else if(messageJson.getString("mfFan").equals("On")) {
                                mfFanSp.setSelection(1);
                                mfFan = "On";
                            }
                            else {
                                mfFanSp.setSelection(2);
                                mfFan = "Off";
                            }

                            curTempValMf.setText(messageJson.getString("mfCurTemp"));

                            if(messageJson.getString("mfConTemp").equals("60")) {
                                mfConTempSp.setSelection(0);
                                mfConTemp = "60";
                            }
                            else if(messageJson.getString("mfConTemp").equals("65")) {
                                mfConTempSp.setSelection(1);
                                mfConTemp = "65";
                            }
                            else if(messageJson.getString("mfConTemp").equals("70")) {
                                mfConTempSp.setSelection(2);
                                mfConTemp = "70";
                            }
                            else if(messageJson.getString("mfConTemp").equals("75")) {
                                mfConTempSp.setSelection(3);
                                mfConTemp = "75";
                            }
                            else if(messageJson.getString("mfConTemp").equals("80")) {
                                mfConTempSp.setSelection(4);
                                mfConTemp = "80";
                            }
                            else {
                                mfConTempSp.setSelection(5);
                                mfConTemp = "85";
                            }

                            if(messageJson.getString("upMode").equals("Heat")) {
                                upModeSp.setSelection(0);
                                upMode = "Heat";
                            }
                            else if(messageJson.getString("upMode").equals("Cool")) {
                                upModeSp.setSelection(1);
                                upMode = "Cool";
                            }
                            else {
                                upModeSp.setSelection(2);
                                upMode = "Off";
                            }
                            if(messageJson.getString("upFan").equals("Auto")) {
                                upFanSp.setSelection(0);
                                upFan = "Auto";
                            }
                            else if(messageJson.getString("mfFan").equals("On")) {
                                upFanSp.setSelection(1);
                                upFan = "On";
                            }
                            else {
                                upFanSp.setSelection(2);
                                upFan = "Off";
                            }

                            curTempValUp.setText(messageJson.getString("upCurTemp"));

                            if(messageJson.getString("upConTemp").equals("60")) {
                                upConTempSp.setSelection(0);
                                upConTemp = "60";
                            }
                            else if(messageJson.getString("upConTemp").equals("65")) {
                                upConTempSp.setSelection(1);
                                upConTemp = "65";
                            }
                            else if(messageJson.getString("upConTemp").equals("70")) {
                                upConTempSp.setSelection(2);
                                upConTemp = "70";
                            }
                            else if(messageJson.getString("upConTemp").equals("75")) {
                                upConTempSp.setSelection(3);
                                upConTemp = "75";
                            }
                            else if(messageJson.getString("upConTemp").equals("80")) {
                                upConTempSp.setSelection(4);
                                upConTemp = "80";
                            }
                            else {
                                upConTempSp.setSelection(5);
                                upConTemp = "85";
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