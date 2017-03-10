package com.dominic.networkimageviewdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private NetworkImageView mImageView;
    private String imgUrl = "https://a-ssl.duitang.com/uploads/item/201608/02/20160802212747_Lx5Yd.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mImageView = (NetworkImageView) findViewById(R.id.net_image);
    }

    public void loadImage(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageLoader.ImageCache imageCache = new MyImageCache();
        ImageLoader imageLoader = new ImageLoader(queue, imageCache);
        mImageView.setImageUrl(imgUrl, imageLoader);
    //    Toast.makeText(getApplicationContext(), "被电击了", Toast.LENGTH_SHORT).show();
    }


    class MyImageCache implements ImageLoader.ImageCache{
        //设置最大缓存大小
        private int maxSize = 6 *1024*1024;

        LruCache<String,Bitmap> mLruCache = new LruCache<String,Bitmap>(maxSize){
            //计算每张图片大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        //从缓存中获取
        @Override
        public Bitmap getBitmap(String s) {
            return mLruCache.get(s);
        }

        //储存到缓存中
        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mLruCache.put(s,bitmap);
        }
    }


}
