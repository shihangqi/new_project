package com.example.lenovo.inequalitysign.adapter;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.ui.OrderInformationActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by ff on 2016/11/29.
 */
public class MyApplication extends Application {

    public static MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("mytoken:",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("myerror:","123456");
            }
        });
        String device_token = mPushAgent.getRegistrationId();
        Utils.push_id = device_token;
        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public Notification getNotification(Context var1, UMessage var2) {
                Utils.now = var2.text.substring(var2.text.indexOf("到")+1,var2.text.indexOf("号"));
                SharedPreferences spf1 =getSharedPreferences("Count",Context.MODE_APPEND);
                SharedPreferences.Editor editor = spf1.edit();
                editor.putString("Now",Utils.now);
                editor.commit();
                Log.e("String",var2.text.toString());
                if(var2.text.toString().equals("您所预定的号码已被叫号！请迅速赶往前台办理业务")){

                }else{
                    Utils.now = var2.text.substring(var2.text.indexOf("到")+1,var2.text.indexOf("号"));
                    SharedPreferences spf =getSharedPreferences("Count",Context.MODE_APPEND);
                    String mine= spf.getString("mine","");
                    Log.e("mine",mine);
                    Log.e("now",Utils.now);
                    if(Utils.num==""){

                    }else{
                        Log.e("now",Utils.num+"num");
                    }

                    if(Integer.parseInt(mine)-Integer.parseInt(Utils.now)==Integer.parseInt(Utils.num)){
                        Toast.makeText(getApplicationContext(),"闹钟",Toast.LENGTH_SHORT).show();
                        Log.e("now","闹钟");

                        //闹钟提醒
                        Intent intent= new Intent(getApplicationContext(), OrderInformationActivity.class);
                        intent.putExtra("Context", "222");
                        startActivity(intent);

                        startMedia();
                    }
                }

                return null;
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

    }

    /**
     * 开始播放铃声
     */
    private void startMedia() {
        try {
            mp = new MediaPlayer();
            mp.setDataSource(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            mp.prepare();
            mp.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
         // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

    }
}
