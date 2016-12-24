package com.example.lenovo.inequalitysign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.OrderAdapter;
import com.example.lenovo.inequalitysign.entity.Order;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MineOrderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private String u = Utils.USER_URL+"order";
    private String u1 =Utils.USER_URL+ "deleteorder";
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private List<Order> ls = new ArrayList<>();
    private ImageButton btn_back;
    private ListView lv;
    private String add;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OrderAdapter adapter = new OrderAdapter(MineOrderActivity.this,ls);

            SharedPreferences spf = getSharedPreferences("Count", Context.MODE_APPEND);
            add = spf.getString("address","");
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
            }
        });
        mSwipeLayout.setOnRefreshListener(this);
    }

    private void findView() {
        btn_back = (ImageButton)findViewById(R.id.order_back);
        lv = (ListView)findViewById(R.id.lv);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

    }

    public void onRefresh() {
        setContent();
        Handler1.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1000);
    }
    private Handler Handler1 = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    Toast.makeText(MineOrderActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };
}
