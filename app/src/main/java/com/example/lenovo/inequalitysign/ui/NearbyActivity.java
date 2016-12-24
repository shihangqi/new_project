package com.example.lenovo.inequalitysign.ui;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.MarkerInfoUtil;
import com.example.lenovo.inequalitysign.entity.Dining;
import com.example.lenovo.inequalitysign.entity.Rank;
import com.example.lenovo.inequalitysign.http.Httpss;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import java.util.ArrayList;
import java.util.List;

public class NearbyActivity extends AppCompatActivity {
    //地图试图
    private TextureMapView mMapView = null;
    //地图控制器
    private BaiduMap mBaiduMap = null;
    //UI控制器
    private UiSettings mUiSettings = null;
    ///定位的客户端
     private LocationClient mLocationClient;
    /* 定位的监听器 */
    public MyLocationListener mMyLocationListener;
    /* 当前定位的模式 */
    private MyLocationConfiguration.LocationMode mCurrentMode= MyLocationConfiguration.LocationMode.NORMAL;
    /* 是否是第一次定位 */
    private volatile boolean isFristLocation = true;
    /* 最新一次的经纬度 */
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    private LatLng latLng ;
    private String s="天安门广场";
    private ImageButton rank_back;
    private Button rank_menu;
    private List<MarkerInfoUtil> list = new ArrayList<>();

    private List<Rank> ls = new ArrayList<>();
    private  String u1 = Utils.SHOP_URL+"list";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mBaiduMap.clear();
            for(int i = 0; i< ls.size();i++){
                Rank d = ls.get(i);
                Log.e("=====","1");
                setAdd(d.getAddress());
                Log.e("======",latLng+"");
//                Log.e("=====",latLng+"");
//                list.add(new MarkerInfoUtil(d.getShop_id(),d.getShop_name(),d.getWait_num(),d.getAddress(),latLng));
            }



        }
    };
    private View.OnClickListener mListener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
               switch (view.getId()) {

                   case R.id.rank_back:
                       Utils.flag =1;

                       Intent i = new Intent(NearbyActivity.this,MainActivity.class);
                       startActivity(i);
                       break;
                   case R.id.rank_menu:
                       new Thread(new Runnable() {
                           @Override
                           public void run() {
                               ls.clear();
                               Message msg = new Message();
                               Httpss http = new Httpss();
                               NameValuePair pair1 = new BasicNameValuePair("city",Utils.city);
                               String s = http.setAndGet(u1,pair1);
                               ls = http.parserRank(s);

                               mHandler.sendMessage(msg);

                           }
                       }).start();

//
//                       PopupWindow popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                       popupWindow.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), android.R.color.transparent, null));
//                       popupWindow.setOutsideTouchable(false);
//                       popupWindow.setFocusable(true);
//                       popupWindow.showAsDropDown(rank_menu);
//                       break;

               }
       }
   };
    private View view1;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_nearby);
        findView();
        setOnClick();
        initBaiduMap();
        //初始化百度定位客户端
        initMyLocation();
//        setMarkerOnClick();

    }

//    private void setMarkerOnClick() {
//        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Bundle bundle = marker.getExtraInfo();
//                MarkerInfoUtil info = (MarkerInfoUtil)bundle.getSerializable("info");
//                Log.e("+++++",info.getShop_address());
//                Log.e("+++++",info.getShop_id());
//                Log.e("+++++",info.getShop_name());
//                Log.e("+++++",info.getShop_num());
//                return true;
//            }
//        });
//    }

    /**
     * 设置点击事件
     */
    private void setOnClick() {
        rank_back.setOnClickListener(mListener);
        rank_menu.setOnClickListener(mListener);
//        clear.setOnClickListener(mListener);
//        mine.setOnClickListener(mListener);
//        text.setOnClickListener(mListener);
//        mark.setOnClickListener(mListener);
    }

    /**
     * 找到试图
     */
    private void findView() {
        rank_back = (ImageButton)findViewById(R.id.rank_back);
        rank_menu = (Button)findViewById(R.id.rank_menu);
//        view1 = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.near_pop,null);
//        clear = (TextView) view1.findViewById(R.id.id_map_clear);
//        mark = (TextView) view1.findViewById(R.id.id_map_mark);
//        mine = (TextView) view1.findViewById(R.id.id_map_mine);
//        text = (TextView) view1.findViewById(R.id.id_map_text);
        et = (EditText)findViewById(R.id.et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ls.clear();
                        String s1 = editable.toString();
                        Log.e("S1",s1);
                        Message msg = new Message();
                        if(s1.length()!= 0 ){

                            Httpss http = new Httpss();
                            NameValuePair pair = new BasicNameValuePair("content",s1);
                            NameValuePair pair1 = new BasicNameValuePair("city",Utils.city);
                            String s = http.setAndGet(u1,pair,pair1);
                            ls = http.parserRank(s);
                            Log.e("String",s+ls.size());
                            msg.what = 1;

                        }else{
                            msg.what = 0;
                        }

                        mHandler.sendMessage(msg);

                    }
                }).start();
            }
        });


    }

    /**
     * 根据地址获得经纬度
     */
    private void setAdd(String s) {


        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(NearbyActivity.this, "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(NearbyActivity.this,
                        "位置：" + result.getAddress(), Toast.LENGTH_LONG)
                        .show();
            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Log.e("+++++ADD","失败");
                }
                Log.e("+++++++++++",result.getLocation()+"");
                latLng = result.getLocation();
                Log.e("latLng",latLng+"");
                addMarkerOverlay(latLng);


            }
        };
        geoCoder.setOnGetGeoCodeResultListener(listener);
        GeoCodeOption GeoOption = new GeoCodeOption();
        GeoOption.city(Utils.city);
        GeoOption.address(s);
        geoCoder.geocode(GeoOption);
//        geoCoder.destroy();




    }

//    /**
//     * 设置监听
//     */
//    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
//        // 反地理编码查询结果回调函数
//
//        @Override
//        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                // 没有检测到结果
//                Toast.makeText(NearbyActivity.this, "抱歉，未能找到结果",
//                        Toast.LENGTH_LONG).show();
//            }
//            Toast.makeText(NearbyActivity.this,
//                    "位置：" + result.getAddress(), Toast.LENGTH_LONG)
//                    .show();
//        }
//
//        // 地理编码查询结果回调函数
//        @Override
//        public void onGetGeoCodeResult(GeoCodeResult result) {
//            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                // 没有检测到结果
//                Log.e("+++++ADD","失败");
//            }
//            Log.e("+++++++++++",result.getLocation()+"");
//            latLng = result.getLocation();
//            Log.e("latLng",latLng+"");
////            addMarkerOverlay(latLng);
//
//
//        }
//    };


    /**
     * 初始化百度定位
     */
    private void initMyLocation() {
        mLocationClient = new LocationClient(NearbyActivity.this);
        //定位SDK初始化
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //是否打开gps
        option.setCoorType("bd09ll");//坐标模式
        option.setIsNeedAddress(true);//设置需要地址信息
        option.setScanSpan(1000);//设置时间间隔
        option.setIsNeedLocationPoiList(true);//设置需要返回
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式

        //对客户端进行设置
        mLocationClient.setLocOption(option);
        //注册定位监听
        mMyLocationListener= new MyLocationListener();
        mLocationClient.registerLocationListener( mMyLocationListener);
        //设置定位图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.marker);
        MyLocationConfiguration config = new MyLocationConfiguration( mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
    }

    /**
     * 初始化百度地图相关设置
     */
    private void initBaiduMap() {
        mMapView = (TextureMapView)findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);//设置比例尺
        mUiSettings = mBaiduMap.getUiSettings();//获取地图UI控制器
        mBaiduMap.setMapStatus(msu);
        //隐藏指南针
        mUiSettings.setCompassEnabled(false);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            // mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            //构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //记录经纬度
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            // 第一次定位时，将地图位置移动到当前位置        
            if (isFristLocation) {            
                isFristLocation = false;            
                center2myLoc();       
            }


        }
    }

    /**
     * 将地图位置移动到当前位置
     */
    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);

        // 设置当前定位位置为 BaiduMap 的中心点，并移动到定位位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }



//    /**
//     * menu中具体操作
//     */
//    private void addTextOverlay(LatLng latLng,String name) {
//
//        OverlayOptions textOption = new TextOptions()
//                .bgColor(0xAAFFFF00)
//                .fontSize(32)
//                .fontColor(0xFFFF00FF)
//                .text(name)
//                .position(latLng);
//        mBaiduMap.addOverlay(textOption);
//
//    }

    /**
     * 添加标注覆盖物
     * @param
     *
     */
    private void addMarkerOverlay(LatLng l) {
        //定义Marker坐标点


            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);
            OverlayOptions options = new MarkerOptions()
                    .position(l)
                    .draggable(false)
                    .title("test")
                    .icon(bitmap);
            //在地图上添加marker并显示
            Marker marker = (Marker) mBaiduMap.addOverlay(options);



    }

    @Override
    protected void onStop() {
    //关闭图层地位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
       mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onStart() {
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
        {
            mLocationClient.start();
        }
        super.onStart();
    }

}
