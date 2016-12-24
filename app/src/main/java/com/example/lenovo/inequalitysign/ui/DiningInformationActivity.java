package com.example.lenovo.inequalitysign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;

import com.example.lenovo.inequalitysign.http.Httpss;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DiningInformationActivity extends AppCompatActivity {

    private ImageButton btn1;
    private DisplayImageOptions options;
    private Button btn;
    private String all1="";
    private String now="";
    private String add11="";
    private String name1="";
    private String name;
    private String id="";
    private TextView tv_name;
    private ImageView img;
    private String s="";
    private String content="";
    private String result="";
    private TextView tv1;
    private TextView tv11;
    private TextView tv2;
    private TextView tv21;
    private TextView tv3;
    private TextView tv31;
    private int state = 0;//代表类型选择状态  如果选择了  则=1 否则 =0
    private String nowtype1;//表示现在是多少号
    private String alltype1;//表示总共有多少号
    private String nowtype2;
    private String alltype2;
    private String nowtype3;
    private String alltype3;
    private String type_name1;//业务类型
    private String type_name2;
    private String type_name3;
    private int type;//存储业务类型
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private ImageButton ib1;
    private ImageButton ib2;
    private ImageButton ib3;
    private static int what;

    private TextView add;
    private String add1;
    private String img1;
    private String u = Utils.SHOP_URL+"store";
    private View.OnClickListener Listener3 =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ib1:
                    ib1.setImageResource(R.drawable.xuanzhong);
                    ib2.setImageResource(R.drawable.daixuan);
                    ib3.setImageResource(R.drawable.daixuan);
                    type =  1;
                    state =1;
                    break;
                case R.id.ib2:
                    ib2.setImageResource(R.drawable.xuanzhong);
                    ib1.setImageResource(R.drawable.daixuan);
                    ib3.setImageResource(R.drawable.daixuan);
                    state=1;
                    type =  2;
                    break;
                case R.id.ib3:
                    ib1.setImageResource(R.drawable.daixuan);
                    ib2.setImageResource(R.drawable.daixuan);
                    ib3.setImageResource(R.drawable.xuanzhong);
                    state =1;
                    type =  3;
                    break;
            }
        }
    };
    private View.OnClickListener Listener2=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ib1:
                    ib1.setImageResource(R.drawable.xuanzhong);
                    ib2.setImageResource(R.drawable.daixuan);
                    type =  1;
                    state=1;
                    break;
                case R.id.ib2:
                    ib2.setImageResource(R.drawable.xuanzhong);
                    ib1.setImageResource(R.drawable.daixuan);
                    type =  2;
                    state=1;
                    break;

            }
        }
    };
    /**
     * 设置服务种类
     */
    private Handler mHandler = new Handler(){//处理页面数据
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                Log.e("content",content);
               if(content.equals("YytActivity")){
                   type1.setText("服务类型");
                   type2.setText("等待人数");
               }else{
                   type1.setText("餐桌类型");
                   type2.setText("等待桌数");
               }
                switch (what){
                    case 1:
                        ib1 = (ImageButton)findViewById(R.id.ib1);
                        ib1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ib1.setImageResource(R.drawable.xuanzhong);
                                type = 1;
                                state =1;
                            }
                        });
                        int i1 = Integer.parseInt(alltype1);
                        int k1 = Integer.parseInt(nowtype1);
                        tv1.setText(type_name1);

                        tv11.setText(i1-k1+"");
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        break;
                    case 2:
                        ib1 = (ImageButton)findViewById(R.id.ib1);
                        ib2 = (ImageButton)findViewById(R.id.ib2);
                        ib1.setOnClickListener(Listener2);
                        ib2.setOnClickListener(Listener2);
                        int i11 = Integer.parseInt(alltype1);
                        int k11 = Integer.parseInt(nowtype1);
                        int i2 = Integer.parseInt(alltype2);
                        int k2 = Integer.parseInt(nowtype2);
                        Log.e("++++",alltype2);
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.GONE);
                        tv1.setText(type_name1);
                        tv11.setText(i11-k11+"");
                        tv2.setText(type_name2);
                        tv21.setText(i2-k2+"");
                        break;
                    case 3:
                        ib1 = (ImageButton)findViewById(R.id.ib1);
                        ib2 = (ImageButton)findViewById(R.id.ib2);
                        ib3 = (ImageButton)findViewById(R.id.ib3);
                        ib1.setOnClickListener(Listener3);
                        ib2.setOnClickListener(Listener3);
                        ib3.setOnClickListener(Listener3);
                        int ii1 = Integer.parseInt(alltype1);
                        int kk1 = Integer.parseInt(nowtype1);
                        int ii2 = Integer.parseInt(alltype2);
                        int kk2 = Integer.parseInt(nowtype2);
                        int i3 = Integer.parseInt(alltype3);
                        int k3 = Integer.parseInt(nowtype3);

                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        tv1.setText(type_name1);
                        tv11.setText(ii1-kk1+"");
                        tv2.setText(type_name2);
                        tv21.setText(ii2-kk2+"");
                        tv3.setText(type_name3);
                        tv31.setText(i3-k3+"");
                        break;
                }
                add.setText(add1);
                tv_name.setText(name);
                ImageLoader.getInstance().displayImage(img1,img);



        }
    };
    /**
     * 处理取号事件
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //如果不能取号  提示
            try {
                JSONObject object = new JSONObject(s);
                result  = object.getString("result");
                Log.e("RESULT",result);
                if(result.equals("fail")){
                    AlertDialog.Builder adb = new AlertDialog.Builder(DiningInformationActivity.this);
                    adb.setTitle("温馨提示");
                    adb.setMessage("您已经在这家店取号");
                    adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(content.equals("HomeFragment")){
                                Utils.flag =1;
                                Intent i2 = new Intent();
                                i2.setClass(DiningInformationActivity.this,MainActivity.class);
                                startActivity(i2);
                            }else if(content.equals("MineOrderActivity")){
                                Intent i4 = new Intent();
                                i4.setClass(DiningInformationActivity.this,MineOrderActivity.class);
                                startActivity(i4);
                            } else{
                                Intent i3 = new Intent();
                                i3.setClass(DiningInformationActivity.this,DiningActivity.class);
                                startActivity(i3);
                            }
                        }
                    });//adb
                    adb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(content.equals("HomeFragment")){
                                Utils.flag =1;
                                Intent i2 = new Intent();
                                i2.setClass(DiningInformationActivity.this,MainActivity.class);
                                startActivity(i2);
                            }else if(content.equals("MineOrderActivity")){
                                Intent i4 = new Intent();
                                i4.setClass(DiningInformationActivity.this,MineOrderActivity.class);
                                startActivity(i4);
                            } else{
                                Intent i3 = new Intent();
                                i3.setClass(DiningInformationActivity.this,DiningActivity.class);
                                startActivity(i3);
                            }
                        }
                    });//adb
                    adb.create();
                    adb.show();
                }else{
                    //得到数据
                    all1 = object.getString("all");
                    now = object.getString("now");
                    Utils.now = now;
                    SharedPreferences spf1 =getSharedPreferences("Count",Context.MODE_APPEND);
                    SharedPreferences.Editor editor1 = spf1.edit();
                    editor1.putString("Now",Utils.now);
                    editor1.commit();
                    add11 = object.getString("shop_address");
                    name1 = object.getString("shop_name");
                    //将我的排号和商家地址 保存到本地；
                    SharedPreferences spf =getSharedPreferences("Count", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("mine",all1);
                    editor.putString("address",add11);
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(DiningInformationActivity.this,OrderInformationActivity.class);
                    String id = getIntent().getStringExtra("Id");
                    intent.putExtra("Context",content);
                    intent.putExtra("Id",id);
                    intent.putExtra("Address",add11);
                    intent.putExtra("Name",name1);
                    intent.putExtra("Mine",all1);
                    intent.putExtra("Now",Utils.now);

                    intent.putExtra("type",type+"");
                    Log.e("Utils.now",now);
                    Log.e("Utils.now",Utils.now);
                    startActivity(intent);
                    finish();


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    /**
     * 本页面按钮的点击事件
     *
     */
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back3:
                    String start = getIntent().getStringExtra("Context");
                    if(start.equals("HomeFragment")){
                        Utils.flag =1;
                        Intent intent1 = new Intent(DiningInformationActivity.this,MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }else if(start.equals("DiningActivity")){
                        Intent i=new Intent(DiningInformationActivity.this,DiningActivity.class);
                        startActivity(i);
                        finish();
                    }else if(start.equals("RankActivity")){
                        Intent i1=new Intent(DiningInformationActivity.this,RankActivity.class);
                        startActivity(i1);
                        finish();
                    }else if(start.equals("YytActivity")){
                        Intent i=new Intent(DiningInformationActivity.this,DiningActivity.class);
                        startActivity(i);
                        finish();
                    }else if(start.equals("SquareFragment")){
                        Utils.flag =2;
                        Intent i=new Intent(DiningInformationActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }else if(start.equals("MineOrderActivity")) {
                        Intent i4 = new Intent();
                        i4.setClass(DiningInformationActivity.this,MineOrderActivity.class);
                        startActivity(i4);
                        finish();
                    }
                    else{
                        Toast.makeText(DiningInformationActivity.this,content,Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_qh:
                    if(Utils.id==""){
                        Intent i = new Intent();
                        i.setClass(DiningInformationActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        if(state == 0){
                            AlertDialog.Builder adb = new AlertDialog.Builder(DiningInformationActivity.this);
                            adb.setTitle("温馨提示");
                            adb.setTitle("请选择业务类型");
                            adb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            adb.setPositiveButton("确定",null);
                            adb.create();
                            adb.show();
                        }else{

                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    Httpss http = new Httpss();
                                    NameValuePair pair = new BasicNameValuePair("user_id", Utils.id);
                                    NameValuePair pair1 = new BasicNameValuePair("shop_id",getIntent().getStringExtra("Id"));
                                    NameValuePair pair2 = new BasicNameValuePair("type",type+"");
                                    s = http.setAndGet(Utils.SHOP_URL+"join",pair1,pair2,pair);
                                    Message msg = new Message();
                                    handler.sendMessage(msg);
                                }
                            }).start();//new Thread


                        }//else

                    }

                    break;
            }
        }
    };
    private TextView type1;
    private TextView type2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_information);
        finView();
        setOnClick();
        init();

        Intent i = getIntent();
        Log.e("content","1");
        id = i.getStringExtra("Id");
        name = i.getStringExtra("Name");
        content  = i.getStringExtra("Context");
        Log.e("++++++",id);
        Log.e("content",content);

        setContent();




    }




    private void init() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)  // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)  // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))// 设置成圆角图片
                .build();// 创建配置过得DisplayImageOption对象
    }



    private void setContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Httpss http = new Httpss();
                NameValuePair pair = new BasicNameValuePair("shop_id",id);
                s = http.setAndGet(u,pair);

                try {
                        JSONObject object = new JSONObject(s);
                        nowtype1 = object.getString("now_type1");
                        nowtype2 = object.getString("now_type2");
                        nowtype3 = object.getString("now_type3");
                        type_name1 = object.getString("name_type1");
                        type_name2 = object.getString("name_type2");
                        type_name3 = object.getString("name_type3");
                        alltype1 = object.getString("all_type1");
                        alltype2 = object.getString("all_type2");
                        alltype3 = object.getString("all_type3");
                        add1 = object.getString("shop_address");
                        img1 = object.getString("shop_img_big");
                        name = object.getString("shop_name");
                        if(nowtype2.equals("null")){
                            what =1;
                        }else{
                            if(nowtype3.equals("null")){
                                what =2;
                            }
                            else{
                                what =3;
                            }
                        }
                    Log.e("+++++++++++++++++",what+"");

//



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        }).start();

    }

    private void setOnClick() {
        btn.setOnClickListener(mListener);
        btn1.setOnClickListener(mListener);

    }

    private void finView() {
        btn1=(ImageButton)findViewById(R.id.back3);//退出按钮
        btn=(Button)findViewById(R.id.btn_qh);//取号按钮
        tv_name = (TextView)findViewById(R.id.name);
        add = (TextView)findViewById(R.id.address);
        img = (ImageView)findViewById(R.id.iv_logo);
        tv1 = (TextView)findViewById(R.id.Dtv1);
        tv11 = (TextView)findViewById(R.id.Dtv11);
        tv2 = (TextView)findViewById(R.id.Dtv2);
        tv21 = (TextView)findViewById(R.id.Dtv21);
        tv3 = (TextView)findViewById(R.id.Dtv3);
        tv31 = (TextView)findViewById(R.id.Dtv31);
        ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll2 = (LinearLayout)findViewById(R.id.ll2);
        ll3 = (LinearLayout)findViewById(R.id.ll3);
        type1 = (TextView)findViewById(R.id.type_name1);
        type2 = (TextView)findViewById(R.id.type_name2);
    }
}
