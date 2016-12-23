package com.example.lenovo.inequalitysign.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;

public class SettingAboutActivity extends AppCompatActivity {

    private ImageButton iv_btn;
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.IBtnAboutBack:
                    Utils.flag =4;
                    Intent i = new Intent(SettingAboutActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);
        findView();
        setOnlick();
    }

    private void setOnlick() {
        iv_btn.setOnClickListener(mListener);
    }

    private void findView() {
        iv_btn = (ImageButton)findViewById(R.id.IBtnAboutBack);
    }
}
