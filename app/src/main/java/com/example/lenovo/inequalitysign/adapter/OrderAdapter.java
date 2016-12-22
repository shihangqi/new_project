package com.example.lenovo.inequalitysign.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.entity.Order;
import com.example.lenovo.inequalitysign.http.Httpss;
import com.example.lenovo.inequalitysign.ui.DiningInformationActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ff on 2016/12/3.
 */
public class OrderAdapter extends BaseAdapter {

    private String u =Utils.USER_URL+"deleteorder";
    private DisplayImageOptions options;
    private List<Order> ls = new ArrayList<>();
    private Context context;
    private String shop_id="";
    private String type="";
    private String num="";
    private String message;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(message.equals("ok")){
                Toast.makeText(context,"删除订单成功",Toast.LENGTH_SHORT).show();
                Log.e("index",ls.size()+"");
                ls.remove(i1);
                Log.e("index",i1+"");
                notifyDataSetChanged();
            }else{
                Toast.makeText(context,"删除订单失败",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private  int i1;
    private int i2;

    public OrderAdapter(Context context,List<Order> ls) {
        this.ls = ls;
        this.context = context;
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

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int i) {
        return ls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.order_item,null);
        }
        Order order = ls.get(i);
        TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
        ImageView tv_url = (ImageView)view.findViewById(R.id.tv_url);
        TextView tv_num = (TextView)view.findViewById(R.id.tv_num);
        ImageButton  btn_delete = (ImageButton)view.findViewById(R.id.mine_orderB2);
        Button again = (Button)view.findViewById(R.id.mine_orderB3);
        TextView status = (TextView)view.findViewById(R.id.tv_type);
        tv_name.setText(order.getTitle());
        tv_num.setText(order.getNum());
        ImageLoader.getInstance().displayImage(order.getUrl(),tv_url);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i1 = i;
                shop_id = ls.get(i).getShop_id();
                type = ls.get(i).getType();
                num = ls.get(i).getNum();
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("温馨提示");
                adb.setMessage("您确定要删除订单么");
                adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Httpss http = new Httpss();
                                NameValuePair pair = new BasicNameValuePair("shop_id",shop_id);
                                NameValuePair pair1 =  new BasicNameValuePair("type",type);
                                NameValuePair pair2 = new BasicNameValuePair("num",num);
                                NameValuePair pair3 = new BasicNameValuePair("user_id",Utils.id);
                                message = http.setAndGet(u,pair,pair1,pair2,pair3);
                                Message msg = new Message();
                                mHandler.sendMessage(msg);
                            }
                        }).start();

                    }
                });
                adb.setNegativeButton("取消",null);
                adb.create();
                adb.show();


            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i2 = i;
                Intent i = new Intent();
                i.setClass(context,DiningInformationActivity.class);
                i.putExtra("Id",ls.get(i2).getShop_id());
                i.putExtra("Context","MineOrderActivity");
                i.putExtra("Name",ls.get(i2).getTitle());
                context.startActivity(i);
            }
        });
        Log.e("Status",order.getStatus());
        if("1".equals(order.getStatus())){
            status.setText("未消费");
        }else{
            status.setText("已消费");
        }


        return view;
    }
}
