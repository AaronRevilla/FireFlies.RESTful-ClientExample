package com.example.aaron.restful_clientexample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Oscar Aaron Revilla Escalona on 10/8/2016.
 */
public class VolleyQueueSingleton {

    private static VolleyQueueSingleton volleyInstance;
    private RequestQueue volleyQueue;
    private ImageLoader volleyImageLoader;
    private static Context mContext;

    private VolleyQueueSingleton(Context context) {
        mContext = context;
        volleyQueue = getRequestQueue();

        volleyImageLoader =  new ImageLoader(volleyQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

    }

    public RequestQueue getRequestQueue() {
        if (volleyQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            volleyQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return volleyQueue;
    }

    public static synchronized VolleyQueueSingleton getInstance(Context context){
        if (volleyInstance == null) {
            volleyInstance = new VolleyQueueSingleton(context);
        }
        return volleyInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {

        return volleyImageLoader;
    }
}
