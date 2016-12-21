package com.example.lenovo.inequalitysign.view;


import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.entity.Rank;
import com.example.lenovo.inequalitysign.http.Httpss;
import com.example.lenovo.inequalitysign.ui.CommentConent;
import com.example.lenovo.inequalitysign.ui.DiningInformationActivity;
import com.example.lenovo.inequalitysign.ui.RankActivity;
import com.example.lenovo.inequalitysign.ui.SceneActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SquareFragment extends Fragment {
    private String u = Utils.SHOP_URL+"list";
    private View view;
    private Button rank;
    private Button comment;
    private Button scene;
    private List<Rank> ls = new ArrayList<>();
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sq_rank:
                    Intent i = new Intent();
                    i.setClass(getActivity().getApplicationContext(),RankActivity.class);
                    startActivity(i);
                    break;
                case R.id.sq_comment:
                    Intent ii = new Intent();
                    ii.setClass(getActivity().getApplicationContext(), CommentConent.class);
                    startActivity(ii);
                    break;
                case R.id.sq_scene:
                    Intent intent = new Intent();
                    intent.setClass(getActivity().getApplicationContext(), SceneActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll1:
                    Intent i1 = new Intent();
                    i1.putExtra("Name",name1);
                    i1.putExtra("Id",id1);
                    i1.putExtra("Context","SquareFragment");
                    i1.setClass(getActivity().getApplicationContext(), DiningInformationActivity.class);
            }
        }
    };
    private String name1;
    private String id1;
    private String name2;
    private String id2;
    private String name3;
    private String id3;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            name1 = ls.get(0).getShop_name();
//            id1 = ls.get(0).getShop_id();
//            name2 = ls.get(1).getShop_name();
//            id2 = ls.get(1).getShop_id();
//            name3 = ls.get(2).getShop_name();
//            id3 = ls.get(2).getShop_id();
//            ImageLoader.getInstance().displayImage(ls.get(0).getUrl(),iv1);
//            tv1.setText(ls.get(0).getShop_name());
//            ImageLoader.getInstance().displayImage(ls.get(1).getUrl(),iv1);
//            tv2.setText(ls.get(1).getShop_name());
//            ImageLoader.getInstance().displayImage(ls.get(2).getUrl(),iv1);
//            tv2.setText(ls.get(2).getShop_name());
//            ll1.setOnClickListener(mListener);
//
//        }
//    };
    private ImageView iv1;
    private TextView tv1;
    private ImageView iv2;
    private TextView tv2;
    private ImageView iv3;
    private TextView tv3;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private DisplayImageOptions options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_square, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        findView();
        setOnClick();
//        setContent();
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

//    private void setContent() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Httpss http = new Httpss();
//                NameValuePair pair = new BasicNameValuePair("city", Utils.city);
//                String s  = http.setAndGet(u,pair);
//                ls = http.parserRank(s);
//                Message msg = new Message();
//                handler.sendMessage(msg);
//                Log.e("size",ls.size()+"");
//
//            }
//        }).start();
//    }

    private void setOnClick() {
        rank.setOnClickListener(mListener);
        comment.setOnClickListener(mListener);
        scene.setOnClickListener(mListener);
    }

    private void findView() {
        rank = (Button)view.findViewById(R.id.sq_rank);//排行
        comment = (Button)view.findViewById(R.id.sq_comment);
        scene = (Button)view.findViewById(R.id.sq_scene);
        iv1 = (ImageView)view.findViewById(R.id.iv1);
        tv1 = (TextView)view.findViewById(R.id.tv1);
        iv2 = (ImageView)view.findViewById(R.id.iv2);
        tv2 = (TextView)view.findViewById(R.id.tv2);
        iv3 = (ImageView)view.findViewById(R.id.iv3);
        tv3 = (TextView)view.findViewById(R.id.tv3);
        ll1 = (LinearLayout)view.findViewById(R.id.LlaySquareNo1);
        ll2 = (LinearLayout)view.findViewById(R.id.LlaySquareNo2);
        ll3 = (LinearLayout)view.findViewById(R.id.LlaySquareNo3);


    }
}
