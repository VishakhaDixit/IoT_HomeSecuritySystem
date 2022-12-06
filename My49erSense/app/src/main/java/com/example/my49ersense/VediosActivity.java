package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.google.android.material.button.MaterialButton;

public class VediosActivity extends AppCompatActivity {

    MaterialButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedios);

        btn = (MaterialButton) findViewById(R.id.btnStartRec);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebView myWebView = (WebView) findViewById(R.id.activity_camera);
                myWebView.loadUrl("http://" + "192.168.10.101" + ":8000/");
            }
        });
    }
}