package com.example.lenovo.inequalitysign.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lenovo.inequalitysign.R;
import com.example.lenovo.inequalitysign.Utils.Utils;
import com.example.lenovo.inequalitysign.adapter.MasonryAdapter;
import com.example.lenovo.inequalitysign.entity.Scene;
import com.example.lenovo.inequalitysign.http.Httpss;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SceneActivity extends AppCompatActivity {
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //调用照相机返回图片临时文件
    private File tempFile;

    private String u = Utils.SHOP_URL+"scene";
    private List<Scene> ls = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageButton btn_back;
    private ImageButton btn_menu;
    private LinearLayout mLlay;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View itemView = LayoutInflater.from(SceneActivity.this).inflate(R.layout.masonry_item, null);
            mLlay = (LinearLayout) itemView.findViewById(R.id.Llay);

            //固定瀑布流的图片宽度
            Resources resources = SceneActivity.this.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int screeWidth = dm.widthPixels;
            int width = (screeWidth - 5) / 2;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLlay.setLayoutParams(lp);

            MasonryAdapter adapter = new MasonryAdapter(ls);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            //设置adapter
            recyclerView.setAdapter(adapter);
            //设置item之间的间隔
            SpacesItemDecoration decoration=new SpacesItemDecoration(15);
            recyclerView.addItemDecoration(decoration);

        }
    };
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.S_back:
                    Intent i = new Intent();
                    Utils.flag = 2;
                    i.setClass(SceneActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case R.id.S_menu:
                    uploadScene();

                    break;
            }
        }
    };
    private Uri uri;

    private void uploadScene() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow,null);
        TextView mTvCamera = (TextView) view.findViewById(R.id.TvPopupCamera);
        TextView mTvPhoto = (TextView) view.findViewById(R.id.TvPopupPhoto);
        TextView mTvCancel = (TextView) view.findViewById(R.id.TvPopupCancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), android.R.color.transparent, null));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_scene, null);
        popupWindow.showAsDropDown(btn_menu);
        //popupWindow在弹窗的时候背景半透明
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                params.alpha = 1.0f;
//                getWindow().setAttributes(params);
//            }
//        });

        mTvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到调用系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, REQUEST_CAPTURE);
                popupWindow.dismiss();
            }
        });
        mTvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到调用系统图库
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
                popupWindow.dismiss();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        createCameraTempFile(savedInstanceState);
        findView();
        setOnClick();
        initData();
        recyclerView= (RecyclerView) findViewById(R.id.recylerview);

    }
    /**
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    uri = Uri.fromFile(tempFile);
                    savePic();
                    intent.setClass(SceneActivity.this, SceneAddActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    uri = intent.getData();
                    savePic();
                    intent.setClass(SceneActivity.this, SceneAddActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;




        }
    }

    private void savePic() {
        final String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.PNG, 100, os);//图片压缩，30代表压缩率，压缩了70%
        byte[] smallBytes = os.toByteArray();
        final String string = Base64.encodeToString(smallBytes, Base64.DEFAULT);
        SharedPreferences spf = getSharedPreferences("scene", MODE_APPEND);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("bitmap", string);
        editor.putString("path", cropImagePath);
        editor.commit();


    }

    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void setOnClick() {
        btn_back.setOnClickListener(mListener);
        btn_menu.setOnClickListener(mListener);
    }

    private void findView() {
        btn_back = (ImageButton)findViewById(R.id.S_back);
        btn_menu = (ImageButton)findViewById(R.id.S_menu);
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Httpss http = new Httpss();
                String s = http.setAndGet(u);
                Log.e("SCENE",s);
                ls = http.parserScene(s);
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        }).start();

    }

}
