package com.example.lenovo.inequalitysign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.MyApplication;
import com.example.lenovo.inequalitysign.entity.Dining;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderInformationActivity extends AppCompatActivity {
    private String u = Utils.SHOP_URL+"join";
    private ImageButton btn1;
    private Button btn;
    private String all;
    private String now;
    private String add;
    private String name;
    private TextView tv1;
    private TextView tv;
    private TextView tv2;
    private TextView tv3;
    private TextView tv_address;
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            try {
//                JSONObject object = new JSONObject(s);
//                    all = object.getString("all");
//                    now = object.getString("now");
//                    add = object.getString("shop_address");
//                    name = object.getString("shop_name");
//                    tv.setText(name);
//                    tv1.setText(all);
//                    tv2.setText(now);
//                    int i1 = Integer.parseInt(all)-Integer.parseInt(now);
//                    tv3.setText(i1+"");
//                    tv_address.setText(add);
//                    SharedPreferences spf =getSharedPreferences("Count", Context.MODE_APPEND);
//                    SharedPreferences.Editor editor = spf.edit();
//                    editor.putString("mine",all);
//                    editor.commit();
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    };


    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back4:
                    Intent ii = getIntent();
                    String name1 = ii.getStringExtra("Name");
                    String id = ii.getStringExtra("Id");
                    String start1 = ii.getStringExtra("Context");
                    if(start1.equals("MineOrderActivity")){
                        Intent intent=new Intent(OrderInformationActivity.this,MineOrderActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent=new Intent(OrderInformationActivity.this,DiningInformationActivity.class);
                        intent.putExtra("Name",name1);
                        intent.putExtra("Context",start1);
                        intent.putExtra("Id",id);
                        startActivity(intent);
                    }

                    break;
                case R.id.btn_qxph:
                    Intent i3 = getIntent();
                    String context = i3.getStringExtra("Context");
                    if(context.equals("HomeFragment")){
                        Intent i4 = new Intent();
                        Utils.flag =1;
                        i4.setClass(OrderInformationActivity.this,MainActivity.class);
                        startActivity(i4);
                    }else{
                        Intent i5 = new Intent();
                        i5.setClass(OrderInformationActivity.this, DiningActivity.class);
                        startActivity(i5);
                    }
                    break;
                case R.id.clock:    //设置响铃
                    AlertDialog.Builder adb = new AlertDialog.Builder(OrderInformationActivity.this);
                    adb.setTitle("请设置闹钟,");
                    final EditText edittext = new EditText(OrderInformationActivity.this);
                    adb.setView(edittext);
                    adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(edittext.getText().length()==0){
                                Toast.makeText(OrderInformationActivity.this,"请输入值",Toast.LENGTH_LONG).show();
                            }else{
                               Utils.num = edittext.getText().toString();
                            }
                        }
                    });
                    adb.setNegativeButton("取消",null);
                    adb.create();
                    adb.show();
                    break;
                case R.id.stop: //停止响铃
                    if (MyApplication.mp != null) {
                        MyApplication.mp.stop();
                    }
                    break;
            }
        }
    };
    private Button clock;
    private String shop_id;
    private String type;
    private String s;
    private String con;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
        finView();
        setOnClick();

//        name = i.getStringExtra("NAME");
//        add = i.getStringExtra("ADDRESS");
//        all = i.getStringExtra("ALL");
//        now = i.getStringExtra("NOW");
        setContent();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MyApplication.mp != null) {
            MyApplication.mp.release();
        }
    }

    private void setContent() {
        SharedPreferences spf =getSharedPreferences("Count",Context.MODE_APPEND);
//        intent.putExtra("Context",content);
//        intent.putExtra("Id",id);
//        intent.putExtra("Address",add11);
//        intent.putExtra("Name",name1);
//        intent.putExtra("Mine",all1);
//        intent.putExtra("Now",Utils.now);
        Intent i = getIntent();
        con = i.getStringExtra("Context");
//        shop_id = i.getStringExtra("Id");
        Utils.now = spf.getString("Now","");
        all = i.getStringExtra("Mine");
        add = i.getStringExtra("Address");
        name = i.getStringExtra("Name");
        type = i.getStringExtra("type");
        tv.setText(name);//商家名字
        tv1.setText(all);//我的拍好
        tv2.setText(Utils.now);//当前排号
        int i1 = Integer.parseInt(all)-Integer.parseInt(Utils.now);
        tv3.setText(i1+"");//还需等待
        tv_address.setText(add);//地址
        if (con.equals("222")) {
            clock.setVisibility(View.GONE);
            stop.setVisibility(View.VISIBLE);
        }

//        Log.e("********",shop_id+type);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Httpss http = new Httpss();
//                NameValuePair pair = new BasicNameValuePair("user_id", Utils.id);
//                NameValuePair pair1 = new BasicNameValuePair("shop_id",shop_id);
//                NameValuePair pair2 = new BasicNameValuePair("type",type);
//                s = http.setAndGet(u,pair,pair1,pair2);
//                Log.e("**************",s);
//                Message msg = new Message();
//                mHandler.sendMessage(msg);
//            }
//        }).start();
    }


    private void setOnClick() {
        btn.setOnClickListener(mListener);
        btn1.setOnClickListener(mListener);
        clock.setOnClickListener(mListener);
        stop.setOnClickListener(mListener);
    }

    private void finView() {
        btn1=(ImageButton)findViewById(R.id.back4);//回退
        btn=(Button)findViewById(R.id.btn_qxph);//取消排号
        tv = (TextView)findViewById(R.id.tv_name);//商家名称
        tv1 = (TextView)findViewById(R.id.tv_num);//取到的号
        tv2 = (TextView)findViewById(R.id.tv_num1);//当前已到
        tv3 = (TextView)findViewById(R.id.tv_num2);//还需等待
        tv_address = (TextView)findViewById(R.id.tv_address);
        clock = (Button)findViewById(R.id.clock);
        stop = (Button) findViewById(R.id.stop);
    }
}
