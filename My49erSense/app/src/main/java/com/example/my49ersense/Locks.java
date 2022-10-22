package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class Locks extends AppCompatActivity {

    String frontDoor = "unlock", backDoor = "unlock", garageDoor = "unlock";
    Spinner frontDoorSp, backDoorSp, garageDoorSp;

    MaterialButton btnLocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locks);

        frontDoorSp = findViewById(R.id.fdSpinner);
        backDoorSp = findViewById(R.id.bdSpinner);
        garageDoorSp = findViewById(R.id.gdSpinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Locks, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frontDoorSp.setAdapter(adapter1);
        frontDoorSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                frontDoor = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Locks, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backDoorSp.setAdapter(adapter2);
        backDoorSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                backDoor = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Locks, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        garageDoorSp.setAdapter(adapter2);
        garageDoorSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                garageDoor = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        getLocksStatus();

        btnLocks = (MaterialButton) findViewById(R.id.btnUpdateLocks);
        btnLocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[3];
                        field[0] = "frontDoor";
                        field[1] = "backDoor";
                        field[2] = "garageDoor";

                        String[] data = new String[3];
                        data[0] = frontDoor;
                        data[1] = backDoor;
                        data[2] = garageDoor;

                        PutData putData = new PutData("http://192.168.50.59/LoginRegister/updateLocksStatus.php", "POST", field, data);

                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Locks Status Update Success")) {
                                    Toast.makeText(Locks.this, result, Toast.LENGTH_SHORT).show();
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

    public void getLocksStatus()
    {
        String[] field = new String[0];
        String[] data = new String[0];

        PutData putData = new PutData("http://192.168.50.59/LoginRegister/getLocksStatus.php", "POST", field, data);

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

                            if(messageJson.getString("frontDoor").equals("Lock")) {
                                frontDoorSp.setSelection(0);
                                frontDoor = "Lock";
                            }
                            else {
                                frontDoorSp.setSelection(1);
                                frontDoor = "Unlock";
                            }
                            if(messageJson.getString("backDoor").equals("Lock")) {
                                backDoorSp.setSelection(0);
                                backDoor = "Lock";
                            }
                            else {
                                backDoorSp.setSelection(1);
                                backDoor = "Unlock";
                            }
                            if(messageJson.getString("garageDoor").equals("Lock")) {
                                garageDoorSp.setSelection(0);
                                garageDoor = "Lock";
                            }
                            else {
                                garageDoorSp.setSelection(1);
                                garageDoor = "Unlock";
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