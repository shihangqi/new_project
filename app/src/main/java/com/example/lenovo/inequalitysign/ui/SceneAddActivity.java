package com.example.lenovo.inequalitysign.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.http.Httpss;

public class SceneAddActivity extends AppCompatActivity {

    private ImageView mIvPic;
    private Button mBtnSave;
    private ImageButton mIBtnBack;
    private EditText mEtContent;
    private View.OnClickListener mOClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.BtnDescriSave:
                    if (mEtContent.getText().toString().isEmpty()) {
                        Toast.makeText(SceneAddActivity.this, "请填写评论内容~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(SceneAddActivity.this, SceneActivity.class);
                        startActivity(intent);
                        finish();
                        SharedPreferences spf = getSharedPreferences("scene", MODE_APPEND);
                        String path = spf.getString("path", "");
                        Httpss httpss = new Httpss();
                        httpss.postScene(SceneAddActivity.this, path, mEtContent.getText().toString());

                    }
                    break;
                case R.id.IBtnDescriBack:
                    Intent intent = new Intent(SceneAddActivity.this, SceneActivity.class);
                    startActivity(intent);
                    finish();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_add);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findView();
        getPic();
        setListener();


    }

    private void setListener() {
        mBtnSave.setOnClickListener(mOClickListener);
        mIBtnBack.setOnClickListener(mOClickListener);
    }

    //获得实景图片
    private void getPic() {
        SharedPreferences spf = getSharedPreferences("scene", MODE_APPEND);
        String smallimg = spf.getString("bitmap", "");
        if (!smallimg.isEmpty()) {
            byte []b = Base64.decode(smallimg, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            mIvPic.setImageBitmap(bitmap);
        }
    }

    private void findView() {
        mIvPic = (ImageView) findViewById(R.id.IvDescriPic);
        mEtContent = (EditText) findViewById(R.id.EtDescriInform);
        mBtnSave = (Button) findViewById(R.id.BtnDescriSave);
        mIBtnBack = (ImageButton) findViewById(R.id.IBtnDescriBack);

    }
}
