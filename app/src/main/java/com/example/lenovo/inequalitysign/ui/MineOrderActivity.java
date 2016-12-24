package com.example.lenovo.inequalitysign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.OrderAdapter;
import com.example.lenovo.inequalitysign.adapter.OrderedAdapter;
import com.example.lenovo.inequalitysign.entity.Order;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MineOrderActivity extends AppCompatActivity {
    private String u = Utils.USER_URL+"order";
    private String u1 =Utils.USER_URL+ "deleteorder";
    private List<Order> ls = new ArrayList<>();
    private List<Order> luse = new ArrayList<>();
    private List<Order> lused = new ArrayList<>();
    private ImageButton btn_back;
    private ListView lv;
    private String add;
    private OrderAdapter adapter;
    private OrderedAdapter adapter2;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new OrderAdapter(MineOrderActivity.this,luse);

            SharedPreferences spf = getSharedPreferences("Count", Context.MODE_APPEND);
            add = spf.getString("address","");
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("+++++1",ls.get(i).getStatus());
                    if("1".equals(ls.get(i).getStatus())){
                        Intent i1 = new Intent(MineOrderActivity.this,DiningInformationActivity.class);
                        i1.putExtra("Name",ls.get(i).getTitle());
                        i1.putExtra("Address",add);
                        i1.putExtra("Type",ls.get(i).getType());
                        i1.putExtra("Mine",ls.get(i).getNum());

                    }
                }
            });
        }
    };
    private String shop_id;
    private String type;
    private String num;
    private TextView mTvUse;
    private TextView mTvUsed;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.TvUse:
                    mVcolor1.setVisibility(View.VISIBLE);
                    mVcolor2.setVisibility(View.GONE);
                    mTvUse.setTextColor(ResourcesCompat.getColor(getResources(), R.color.all, null));
                    mTvUsed.setTextColor(ResourcesCompat.getColor(getResources(), R.color.grey, null));
                    adapter = new OrderAdapter(MineOrderActivity.this,luse);
                    lv.setAdapter(adapter);
                    break;
                case R.id.TvUsed:
                    mVcolor2.setVisibility(View.VISIBLE);
                    mVcolor1.setVisibility(View.GONE);
                    mTvUsed.setTextColor(ResourcesCompat.getColor(getResources(), R.color.all, null));
                    mTvUse.setTextColor(ResourcesCompat.getColor(getResources(), R.color.grey, null));
                    adapter2 = new OrderedAdapter(MineOrderActivity.this,lused);
                    lv.setAdapter(adapter2);
                    break;
            }
        }
    };
    private View mVcolor1;
    private View mVcolor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order);
        findView();
        setOnClick();
        setContent();
    }

    private void setContent() {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    ls.clear();
                    Httpss http = new Httpss();
                    NameValuePair pair = new BasicNameValuePair("id",Utils.id);
                    String s = http.setAndGet(u,pair);
                    ls = http.parserOrder(s);
                    Log.e("ls", s);
                    for (int i = 0; i < ls.size(); i ++) {
                        String type = ls.get(i).getStatus();
                        if (type.equals("1")) {
                            luse.add(ls.get(i));
                        } else if (type.equals("0")) {
                            lused.add(ls.get(i));
                        }
                    }
                    Message msg = new Message();
                    mHandler.sendMessage(msg);
                }
            }).start();
}



    private void setOnClick() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Utils.flag = 4;
                intent.setClass(MineOrderActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mTvUse.setOnClickListener(mOnClickListener);
        mTvUsed.setOnClickListener(mOnClickListener);
    }

    private void findView() {
        btn_back = (ImageButton)findViewById(R.id.order_back);
        lv = (ListView)findViewById(R.id.lv);
        mTvUse = (TextView) findViewById(R.id.TvUse);
        mTvUsed = (TextView) findViewById(R.id.TvUsed);
        mVcolor1 = findViewById(R.id.Vcolor1);
        mVcolor2 = findViewById(R.id.Vcolor2);
    }
}
