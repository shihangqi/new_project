package com.example.lenovo.inequalitysign.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.OrderAdapter;
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
    private ImageButton btn_back;
    private ListView lv;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OrderAdapter adapter = new OrderAdapter(MineOrderActivity.this,ls);
            Log.e("ls.toString",ls.toString());
            lv.setAdapter(adapter);
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

        if(shop_id == "null"||shop_id==null){
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
    }

    private void findView() {
        btn_back = (ImageButton)findViewById(R.id.order_back);
        lv = (ListView)findViewById(R.id.lv);
    }
}
