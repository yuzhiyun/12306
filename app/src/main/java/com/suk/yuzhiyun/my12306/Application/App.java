package com.suk.yuzhiyun.my12306.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.suk.yuzhiyun.my12306.order.model.entity.Order;
import com.suk.yuzhiyun.my12306.ticketList.model.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhiyun on 2016-09-08.
 */
public class App extends Application {

    public static SharedPreferences SPsavaData;

    public static App instance=null;
    public static  App getInstance(){
        if(instance==null)
            instance=new App();
        return  instance;
    }


    public  SharedPreferences getSharedPreferences(Context context){
        if(SPsavaData==null)
            SPsavaData=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        return SPsavaData;
    }
    public static List<Ticket> mTicketList=new ArrayList<Ticket>();
    /**
     * 已支付订单
     */
    public static List<Order> mPayedOrderList=new ArrayList<Order>();
    /**
     * 未支付订单
     */
    public static List<Order> mUnpayedOrderList=new ArrayList<Order>();
    public static String[] seats = new String[]{
            "硬座",
            "软座",
            "硬卧",
            "软卧",
            "一等座",
            "二等座"
    };
    public static String[] city=new String[]{"长沙","上海","北京","广州","杭州"};
    public static String ip = "maxiaolong.cn/my12306";
    public static String type[] = {
            "成人",
            "儿童",
            "学生",
            "伤残人士",
            "老年人"
    };

    public static String sex[] = {
            "男",
            "女"
    };

    //谔谔
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

    public static String getUserName(Context context) {
//        第一步，获得SharedPreferences对象,注意可能你用了很多个SharedPreferences，创建了不同的文件来存取信息，所以第一个参数别指定错了，它代表文件名
        SharedPreferences SPgetData = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
//        第二步，获取数据，这里和存储数据不一样，不需要editor对象，只需要SharedPreferences对象即可获取值啦！传入key,获取数据，第二个参数是默认返回值
        String username = SPgetData.getString("username", "");
        return username;
    }

    public static String getPassword(Context context) {
        //        第一步，获得SharedPreferences对象,注意可能你用了很多个SharedPreferences，创建了不同的文件来存取信息，所以第一个参数别指定错了，它代表文件名
        SharedPreferences SPgetData = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
//        第二步，获取数据，这里和存储数据不一样，不需要editor对象，只需要SharedPreferences对象即可获取值啦！传入key,获取数据，第二个参数是默认返回值
        String password = SPgetData.getString("password", "");
        return password;
    }

}
