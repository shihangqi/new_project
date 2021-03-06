package com.example.lenovo.inequalitysign.ui;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.DiningAdapter;
import com.example.lenovo.inequalitysign.entity.Dining;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class DiningActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    private  String u = Utils.SHOP_URL+"line_dining";
    private String u1 = Utils.SHOP_URL+"line_hall";

    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ImageButton btn;
    private Button btn1;
    private Button btn2;
    private DiningAdapter adapter;
    private ListView lv;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter = new DiningAdapter(DiningActivity.this,ls);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //设置点击事件
                    Intent intent = new Intent();
                    intent.setClass(DiningActivity.this, DiningInformationActivity.class);
                    intent.putExtra("Context","DiningActivity");
                    intent.putExtra("Name",ls.get(i).getName());
                    intent.putExtra("Id",ls.get(i).getShop_id());
                    startActivityForResult(intent,i);
                }
            });
        }
    };
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {//营业厅列表的点击事件
            super.handleMessage(msg);

            adapter = new DiningAdapter(DiningActivity.this,ls);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //设置点击事件
                    Intent intent = new Intent();
                    intent.setClass(DiningActivity.this, DiningInformationActivity.class);
                    intent.putExtra("Context","YytActivity");
                    intent.putExtra("Name",ls.get(i).getName());
                    intent.putExtra("Id",ls.get(i).getShop_id());
                    startActivityForResult(intent,i);
                }
            });
        }
    };
    public List<Dining> ls= new ArrayList<Dining>();//存放餐厅列表数据
    private String s = "";
    private List<String> list = new ArrayList<>();//存放营业厅列表数据
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back1:
                    Intent i = new Intent();
                    Utils.flag = 1;
                    i.setClass(DiningActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case R.id.ct:
                    //展示餐厅列表数据
                    displayDining();
                    lv.setAdapter(adapter);
                    break;
                case R.id.yyt:
                    //展示营业厅列表数据

                    displayYyt();
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findView();
        setOnClick();
        setDefault();

    }

    private void setDefault() {
        displayDining();
    }

    /**
     * 展示营业厅数据
     */
    private void displayYyt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ls = new ArrayList<Dining>();
                Httpss http = new Httpss();
                NameValuePair pair = new BasicNameValuePair("city", Utils.city);
                s = http.setAndGet(u1,pair);
                ls = http.parser(s);
                Message msg = new Message();
                mHandler1.sendMessage(msg);

            }
        }).start();

    }


    /**
     * 展示餐厅列表
     */
    private void displayDining() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ls = new ArrayList<Dining>();
                Httpss http = new Httpss();
                NameValuePair pair = new BasicNameValuePair("city", Utils.city);
                s =http.setAndGet(u,pair);
                Log.e("ct",s);
                ls = http.parser(s);
                Log.e("Dining",ls.toString());
                Message msg = new Message();
                mHandler.sendMessage(msg);

            }
        }).start();



    }


    private void setOnClick() {
        btn.setOnClickListener(mListener);
        btn1.setOnClickListener(mListener);
        btn2.setOnClickListener(mListener);
        mSwipeLayout.setOnRefreshListener(this);
    }

    private void findView() {
        btn2=(Button)findViewById(R.id.yyt);
        btn=(ImageButton)findViewById(R.id.back1);
        btn1 = (Button)findViewById(R.id.ct);
        lv = (ListView)findViewById(R.id.Lv);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
    }
    public void onRefresh() {
        displayDining();
        Handler1.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1000);
    }
    private Handler Handler1 = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
      Toast.makeText(DiningActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };
}
