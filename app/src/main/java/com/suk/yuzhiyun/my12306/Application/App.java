package com.suk.yuzhiyun.my12306.Application;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yuzhiyun on 2016-09-08.
 */
public class App extends Application {

    public static RequestQueue requestQueue = null;

    //    static App app=null;
    @Override
    public void onCreate() {
        super.onCreate();
//        app=new App();
    }


    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

}
