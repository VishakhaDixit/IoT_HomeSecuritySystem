package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}