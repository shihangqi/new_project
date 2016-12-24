package com.example.lenovo.inequalitysign.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class CommentAddActivity extends AppCompatActivity {

    private ImageButton mIBtnBack;
    private Button mBtnSend;
    private EditText mEtComment;
    private CheckBox mCbNoname;
    private String starnum;
    private String result = "";
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (result.equals("1")) {
                Toast.makeText(CommentAddActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CommentAddActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.IBtnBack:
                    finish();
                    break;
                case R.id.BtnSend:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = getIntent();
                            Httpss httpss = new Httpss();
                            NameValuePair pairId = new BasicNameValuePair("user_id", Utils.id);
                            NameValuePair pairShopid = new BasicNameValuePair("shop_id", i.getStringExtra("shopid"));
                            NameValuePair pairStar = new BasicNameValuePair("star", starnum);
                            NameValuePair pairContent = new BasicNameValuePair("content", mEtComment.getText().toString());
                            result = httpss.setAndGet(Utils.urlComment, pairId, pairShopid, pairStar, pairContent);
                            Message msg = new Message();
                            h.sendMessage(msg);
                        }
                    }).start();
                    finish();
                    break;
            }
        }
    };
    private String name;
    private EditText mEtStarNum;
    private RatingBar ratingBar;

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
        mEtStarNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    starnum = mEtStarNum.getText().toString();
                    if (!starnum.isEmpty()) {
                        ratingBar.setRating(Float.parseFloat(starnum));
                    }
                }
            }
        });
    }

    private void findView() {
        mIBtnBack = (ImageButton) findViewById(R.id.IBtnBack);
        mBtnSend = (Button) findViewById(R.id.BtnSend);
        mEtComment = (EditText) findViewById(R.id.EtComment);
        mCbNoname = (CheckBox) findViewById(R.id.CbNoname);
        mEtStarNum = (EditText) findViewById(R.id.EtStarNum);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
    }
}
