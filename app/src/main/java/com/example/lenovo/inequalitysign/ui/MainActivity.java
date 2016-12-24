package com.example.lenovo.inequalitysign.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lenovo.inequalitysign.R;

import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.entity.Dining;
import com.example.lenovo.inequalitysign.view.HomeFragment;
import com.example.lenovo.inequalitysign.view.MineFragment;
import com.example.lenovo.inequalitysign.view.SquareFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private HomeFragment hf;
    private MineFragment mf;
    private SquareFragment sf;
    private LinearLayout ll;
    private List<Dining> ls;


    private View.OnClickListener mListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            android.app.FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft =fm.beginTransaction();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (view.getId()){
                case R.id.btn_sy:
                  if(hf == null){
                      hf = new HomeFragment();
                  }
                    changeimg(1);
                    ft.replace(R.id.container,hf);
                    break;
                case R.id.btn_gc://广场
                    if(sf ==null){
                        sf = new SquareFragment();

                    }
                    changeimg(2);
                    ft.replace(R.id.container,sf);
                    break;
                case R.id.btn_fj:
                    Intent i =new Intent(MainActivity.this,NearbyActivity.class);
                    startActivity(i);
                    break;
                case R.id.btn_wd:
                    if(mf == null){
                        mf = new MineFragment();
                    }
                    changeimg(4);
                    ft.replace(R.id.container,mf);
                    break;
            }//switch
            ft.commit();
            ll.invalidate();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findView();
        setOnClick();
        switch(Utils.flag){//实现从Fragment跳转到Fragment
            case 1:
                changeimg(1);
                setDefaultPage();
                break;
            case 2:
                changeimg(2);
                setSquare();
                break;
            case 3:
//                changeimg(3);
//                setNear();
                break;
            case 4:
                changeimg(4);
                setMine();
                break;
        }

    }

    private void setMine() {
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (mf == null) {
            mf = new MineFragment();
        }

        ft.replace(R.id.container,mf);
        ft.commit();
        ll.invalidate();
    }

//    private void setNear() {
//        android.app.FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft =fm.beginTransaction();
//        if (nf == null) {
//            nf = new NearbyFragment();
//        }
//
//        ft.replace(R.id.container,nf);
//        ft.commit();
//        ll.invalidate();
//    }

    private void setSquare() {
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (sf == null) {
            sf = new SquareFragment();
        }

        ft.replace(R.id.container,sf);
        ft.commit();
        ll.invalidate();
    }

    private void setDefaultPage() {
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (hf == null) {
            hf = new HomeFragment();
        }

        ft.replace(R.id.container,hf);
        ft.commit();
        ll.invalidate();
    }

    private void setOnClick() {
        btn.setOnClickListener(mListener);
        btn1.setOnClickListener(mListener);
        btn2.setOnClickListener(mListener);
        btn3.setOnClickListener(mListener);

    }

    private void findView() {
        btn = (Button)findViewById(R.id.btn_sy);
        btn1 = (Button)findViewById(R.id.btn_gc);
        btn2 = (Button)findViewById(R.id.btn_fj);
        btn3 = (Button)findViewById(R.id.btn_wd);
        ll = (LinearLayout)findViewById(R.id.ll);
    }



    private void changeimg(int i){
        btn.setBackground(this.getResources().getDrawable(R.drawable.sy));
        btn1.setBackground(this.getResources().getDrawable(R.drawable.gc));
        btn2.setBackground(this.getResources().getDrawable(R.drawable.fj));
        btn3.setBackground(this.getResources().getDrawable(R.drawable.wd));
        switch (i){
            case 1:
                btn.setBackground(this.getResources().getDrawable(R.drawable.sy2));
                break;
            case 2:
                btn1.setBackground(this.getResources().getDrawable(R.drawable.gc2));
                break;
            case 3:
                btn2.setBackground(this.getResources().getDrawable(R.drawable.fj2));
                break;
            case 4:
                btn3.setBackground(this.getResources().getDrawable(R.drawable.wd2));
                break;
        }
    }
}
