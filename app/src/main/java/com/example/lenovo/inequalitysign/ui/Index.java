package com.example.lenovo.inequalitysign.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.inequalitysign.R;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        new Handler().postDelayed(r,1000);

    }
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Index.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    };
}
