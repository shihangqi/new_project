package com.example.lenovo.inequalitysign.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.CircleImageView;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.http.Httpss;
import com.example.lenovo.inequalitysign.ui.LoginActivity;
import com.example.lenovo.inequalitysign.ui.MainActivity;
import com.example.lenovo.inequalitysign.ui.MineOrderActivity;
import com.example.lenovo.inequalitysign.ui.MineProfileActivity;
import com.example.lenovo.inequalitysign.ui.SettingAboutActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {
    private String u = Utils.USER_URL+ "getmessage";
    private View view;
    private Button btn;
    private LinearLayout ll;
    private LinearLayout llL;
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.mypageB1:
                    //调到登陆界面 ,图片下面的字体改变成推出当前账号

                    Intent i = new Intent();
                    i.setClass(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    break;
                case R.id.mypageB2:
                    //如果 没登陆 调到登陆界面，登陆之后可以查看记录
                    if(Utils.id==""){
                        Intent intent = new Intent();
                        intent.setClass(getActivity().getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent();
                        intent.setClass(getActivity().getApplicationContext(), MineOrderActivity.class);
                        startActivity(intent);
                    }

                    break;
                case R.id.mypageB3:
                    //如果 没登陆 调到登陆界面，登陆之后可以设置个人资料
                    if(Utils.id==""){
                        Intent intent1 = new Intent();
                        intent1.setClass(getActivity().getApplicationContext(), LoginActivity.class);
                        startActivity(intent1);
                    }else{
                        Intent intent1 = new Intent();
                        intent1.setClass(getActivity().getApplicationContext(), MineProfileActivity.class);
                        startActivity(intent1);
                    }

                    break;
                case R.id.btn_exit:
                    Utils.id = "";
                    Utils.flag = 1;
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageB4:
                    Intent i1= new Intent(getActivity().getApplicationContext(), SettingAboutActivity.class);
                    startActivity(i1);
            }
        }
    };
    private TextView tv_name;
    private Button btn_exit;
    private String name;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_name.setText(name);
            ImageLoader.getInstance().displayImage(url,imageButton);
        }
    };
    private Handler handler1 = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btn.setVisibility(View.GONE);
            tv_name.setVisibility(View.VISIBLE);
            btn_exit.setVisibility(View.VISIBLE);
        }
    };
    private String url;
    private DisplayImageOptions options;
    private CircleImageView imageButton;
    private LinearLayout ll2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        setOnClick();
        init();
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
        if (Utils.id == "") {

        } else {
            Message msg =new Message();
            handler1.sendMessage(msg);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Httpss http = new Httpss();
                    NameValuePair pair = new BasicNameValuePair("id", Utils.id);
                    String s = http.setAndGet(u,pair);
                    try {
                        JSONObject object = new JSONObject(s);
                        name = object.getString("name");
                        url = object.getString("img");
                        Message msg =new Message();
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void setOnClick() {
        btn.setOnClickListener(mListener);
        ll.setOnClickListener(mListener);
        llL.setOnClickListener(mListener);
        btn_exit.setOnClickListener(mListener);
        ll2.setOnClickListener(mListener);
    }

    private void findView() {
        btn = (Button)view.findViewById(R.id.mypageB1);//登陆
        ll = (LinearLayout)view.findViewById(R.id.mypageB2);//排号记录
        llL = (LinearLayout)view.findViewById(R.id.mypageB3);//个人资料
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
        imageButton = (CircleImageView)view.findViewById(R.id.imageButton);
        ll2 = (LinearLayout)view.findViewById(R.id.mypageB4);
    }
}
