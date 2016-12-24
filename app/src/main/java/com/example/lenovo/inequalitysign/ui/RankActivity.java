package com.example.lenovo.inequalitysign.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.RankAdapter;
import com.example.lenovo.inequalitysign.entity.Rank;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private String u = Utils.SHOP_URL+"list";
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private List<Rank> ls = new ArrayList<>();
    private ImageButton btn;
    private ListView lv;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RankAdapter adapter = new RankAdapter(RankActivity.this,ls);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //设置点击事件
                    Intent intent = new Intent();
                    intent.setClass(RankActivity.this, DiningInformationActivity.class);
                    intent.putExtra("Context","RankActivity");
                    intent.putExtra("Id",ls.get(i).getShop_id());
                    startActivityForResult(intent,i);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findView();
        setOnClick();
        getContent();
    }

    private void getContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Httpss http = new Httpss();
                NameValuePair pair = new BasicNameValuePair("city",Utils.city);
                String s  = http.setAndGet(u,pair);
                Log.e("S",s);
                ls = http.parserRank(s);
                Message msg = new Message();
                handler.sendMessage(msg);

            }
        }).start();
    }

    private void setOnClick() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.flag = 2;
                Intent intent = new Intent();
                intent.setClass(RankActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mSwipeLayout.setOnRefreshListener(this);
    }

    private void findView() {
        btn = (ImageButton)findViewById(R.id.rank_back);
        lv = (ListView)findViewById(R.id.lv);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

    }
    public void onRefresh() {
        getContent();
        Handler1.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1000);
    }
    private Handler Handler1 = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    Toast.makeText(RankActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };
}
