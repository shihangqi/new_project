package com.example.lenovo.inequalitysign.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.lenovo.inequalitysign.R;

public class CommentAddActivity extends AppCompatActivity {

    private ImageButton mIBtnBack;
    private Button mBtnSend;
    private EditText mEtComment;
    private CheckBox mCbNoname;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.IBtnBack:
                    finish();
                    break;
                case R.id.BtnSend:
                    finish();
                    break;
            }
        }
    };
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_add);
        findView();
        setListener();
        if (mCbNoname.isChecked()) {
            name = "匿名用户";
        }
    }

    private void setListener() {
        mBtnSend.setOnClickListener(mOnClickListener);
        mIBtnBack.setOnClickListener(mOnClickListener);
    }

    private void findView() {
        mIBtnBack = (ImageButton) findViewById(R.id.IBtnBack);
        mBtnSend = (Button) findViewById(R.id.BtnSend);
        mEtComment = (EditText) findViewById(R.id.EtComment);
        mCbNoname = (CheckBox) findViewById(R.id.CbNoname);
    }
}
