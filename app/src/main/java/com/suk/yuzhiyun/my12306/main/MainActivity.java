package com.suk.yuzhiyun.my12306.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.suk.yuzhiyun.my12306.Application.App;
import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;
import com.suk.yuzhiyun.my12306.calendar.CalendarActivity;
import com.suk.yuzhiyun.my12306.calendar.DateUtil;
import com.suk.yuzhiyun.my12306.order.control.activity.OrderActivity;
import com.suk.yuzhiyun.my12306.main.viewpager.Adapter;
import com.suk.yuzhiyun.my12306.main.viewpager.ViewPagerFragment;
import com.suk.yuzhiyun.my12306.personalInfor.InforActivity;
import com.suk.yuzhiyun.my12306.ticketList.control.TicketListActivity;
import com.suk.yuzhiyun.my12306.ticketList.model.entity.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    //{"inquire":[{"username":"maxiaolong",startstation":"北京","endstation":"长沙","date":"2016-9-14","seatType":0}]}

    String url = "http://" + App.ip + ":8080/check.ticket";

    int seatPosition = 0;
    @Bind(R.id.fabOrder)
    FloatingActionButton fabOrder;


    @Bind(R.id.fabInfor)
    FloatingActionButton fabInfor;

    @Bind(R.id.float_menu)
    FloatingActionsMenu float_menu;

    @Bind(R.id.btnSearch)
    Button btnSearch;

    @Bind(R.id.vPager)
    ViewPager vPager;

    int year;
    int month;
    int day;

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_START_CITY_CODE = 2;
    private static final int REQUEST_END_CITY_CODE = 3;
    @Bind(R.id.tvStartCity)
    TextView tvStartCity;
    @Bind(R.id.tvEndCity)
    TextView tvEndCity;

    String startCity = null;
    String endCity = null;


    @Bind(R.id.tvStartTime)
    TextView tvStartTime;

    @Bind(R.id.tvSeat)
    TextView tvSeat;

    //小圆点图片
    private ImageView[] imgVIndicator;
    //viewPager导航
    @Bind(R.id.linearIndicator)
    LinearLayout linearIndicator;
    /**
     * 处理viewpager自动轮播
     */
    int currentItem = 0;
    int AUTO_MESSAGE = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //设置当前item
            vPager.setCurrentItem((currentItem++) % ViewPagerFragment.picture.length, true);
//            继续轮播
            handler.sendEmptyMessageDelayed(AUTO_MESSAGE, 2000);
        }
    };

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initOther() {


        /**
         * 控件初始值
         * */
        tvStartCity.setText(App.city[0]);
        tvEndCity.setText(App.city[4]);
        tvSeat.setText(App.seats[0]);

        year = DateUtil.getYear();
        month = DateUtil.getMonth();
        day = DateUtil.getCurrentMonthDay();
        tvStartTime.setText(year + "-" + month + "-" + day);
        initViewPager();

        //viewpager开始轮播
        handler.sendEmptyMessageDelayed(AUTO_MESSAGE, 1000);

        setIndicator();

        //initFloatMenu();

    }

    /**
     * 处理阴影
     * FloatingActionsMenu展开时候添加阴影
     * FloatingActionsMenu关闭时去除阴影
     */
//    private void initFloatMenu() {
//        float_menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
//            @Override
//            public void onMenuExpanded() {
//                WindowManager.LayoutParams params=MainActivity.this.getWindow().getAttributes();
//                params.alpha=0.3f;
//                MainActivity.this.getWindow().setAttributes(params);
//            }
//
//            @Override
//            public void onMenuCollapsed() {
//                WindowManager.LayoutParams params=MainActivity.this.getWindow().getAttributes();
//                params.alpha=1f;
//                MainActivity.this.getWindow().setAttributes(params);
//            }
//        });
//
//    }

    /**
     * viewpager导航图片设置
     */
    private void setIndicator() {
        vPager.setOnPageChangeListener(this);

        int size = ViewPagerFragment.picture.length;
        imgVIndicator = new ImageView[size];
        for (int i = 0; i < size; i++) {

            final ImageView imageView = new ImageView(this);

            imgVIndicator[i] = imageView;

            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //默认选择第一张图片
            if (0 == i)
                imgVIndicator[i].setImageResource(R.drawable.selected_circle);
            else
                imgVIndicator[i].setImageResource(R.drawable.unselected_circle);
            LayoutParams lp = new LayoutParams(15, 15);
            lp.setMargins(15, 0, 0, 0);
            linearIndicator.addView(imgVIndicator[i], lp);
        }
    }

    private void initViewPager() {
        vPager.setAdapter(new Adapter(getSupportFragmentManager()));
    }

    /**
     * 选择起始站
     */
    @OnClick(R.id.tvStartCity)
    public void setTvStartCity() {
        startActivityForResult(new Intent(context, ChooseCityActivity.class), REQUEST_START_CITY_CODE);
    }

    /**
     * 选择终点站
     */
    @OnClick(R.id.tvEndCity)
    public void setTvEndCity() {
        startActivityForResult(new Intent(context, ChooseCityActivity.class), REQUEST_END_CITY_CODE);
    }

    @OnClick(R.id.tvStartTime)
    public void setTvStartTime() {
        startActivityForResult(new Intent(context, CalendarActivity.class), REQUEST_CODE);
    }

    /**
     * 查询满足条件的车票
     * 条件封装为json字符串，传递给服务器
     */
    @OnClick(R.id.btnSearch)
    public void search() {

        StringRequestPost(url);

    }


    /**
     * 传递购票条件到服务器
     *
     * @param url
     */
    private void StringRequestPost(String url) {
        //        进度对话框
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在连接...");
        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        Log.i("onResponse", s);
                        //[
                        // [{"date":"2016-09-23",
                        // "endstation":"杭州",
                        // "endtime":"10:28:00",
                        // "gnumber":0,
                        // "id":0,
                        // "price":359.76569675,
                        // "revicetime":0,
                        // "saletime":"",
                        // "saletype":0,
                        // "seatnumber":0,
                        // "startstation":"长沙",
                        // "starttime":"09:28:00",
                        // "status":0,
                        // "tid":"K809",
                        // "uidnumber":"",
                        // "uname":"",
                        // "utype":0
                        // }],

                        // [[0,0,0,10,0,0]],
                        // [[359,504,374,478,212,212]]

                        // ]
                        App.mTicketList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            JSONArray subJSONArray1 = jsonArray.getJSONArray(0);
                            JSONArray subJSONArray2 = jsonArray.getJSONArray(1);
                            JSONArray subJSONArray3 = jsonArray.getJSONArray(2);
                            /**
                             * 解析所有ticket,添加到App.mTicketList
                             */

                            for (int i = 0; i < subJSONArray1.length(); i++) {

                                String date = subJSONArray1.getJSONObject(i).getString("date");
                                String mStartStation = subJSONArray1.getJSONObject(i).getString("startstation");
                                String mEndStation = subJSONArray1.getJSONObject(i).getString("endstation");
                                String mTrainNum = subJSONArray1.getJSONObject(i).getString("tid");
                                String mStartTime = subJSONArray1.getJSONObject(i).getString("starttime");
                                String mEndTime = subJSONArray1.getJSONObject(i).getString("endtime");
//                                String mDuration=subJSONArray1.getJSONObject(i).getString("mDuration");
//                                需要计算时长，我暂时用一个特定值代替吧，以后再改
                                String mDuration = "5小时8分42";
                                Double price=Double.parseDouble(subJSONArray1.getJSONObject(i).getString("price"));
                                String mPrice = price.intValue()+"";

                                Ticket ticket = new Ticket(date,mStartStation, mEndStation, mTrainNum, mStartTime, mEndTime, mDuration, mPrice);

//


                                App.mTicketList.add(ticket);
                                Log.i("json", "第" + i + "张车票" + App.mTicketList.size()
                                        + " " + ticket.getmStartStation()
                                        + " " + ticket.getmEndStation()
                                        + " " + ticket.getmTrainNum()
                                        + " " + ticket.getmStartTime()
                                        + " " + ticket.getmEndTime()
                                        + " " + ticket.getmDuration()
                                        + " " + ticket.getmPrice()
                                );
                            }
                            /**
                             * 解析ticket的各类座位对应余票数量
                             */
                            for (int i = 0; i < subJSONArray2.length(); i++) {
                                JSONArray jsonArray1 = subJSONArray2.getJSONArray(i);
                                int num=0;
                                for (int j = 0; j < 6; j++) {
                                   num=jsonArray1.getInt(j);
                                    App.mTicketList.get(i).mTicketNum[j]=num;
                                    Log.i("json", "第" + i + "张车票-第"+j+"种座位还有 " + num+" 张");
                                }

                            }
                            /**
                             * 解析ticket的各类座位对应价格
                             */
                            for (int i = 0; i < subJSONArray3.length(); i++) {
                                JSONArray jsonArray2 = subJSONArray3.getJSONArray(i);
                                for (int j = 0; j < 6; j++) {
                                    Double  price=jsonArray2.getDouble(j);
                                    App.mTicketList.get(i).mTicketPrice[j]=price.intValue();

                                    Log.i("json", "第" + i + "种车票"+"第"+j+"种座位价格是  " + price.intValue());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "服务器" + s, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, TicketListActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "错误" + volleyError.toString(), Toast.LENGTH_SHORT).show();
//
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("msg", json());
                return map;
            }
        };

        App.getRequestQueue(context).add(request);
    }

    public String json() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            JSONObject data = new JSONObject();
            data.put("username", App.getUserName(context));
            data.put("startstation", tvStartCity.getText().toString().trim());
            data.put("endstation", tvEndCity.getText().toString().trim());
            data.put("date", tvStartTime.getText().toString().trim());
            data.put("seatType", seatPosition);
            array.put(data);
            jsonObject.put("inquire", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    /**
     * 查看我的订单
     */
    @OnClick(R.id.fabOrder)
    public void fabOrder() {
        startActivity(new Intent(context, OrderActivity.class));
    }

    /**
     * 修改个人资料
     */
    @OnClick(R.id.fabInfor)
    public void fabInfor() {
        startActivity(new Intent(context, InforActivity.class));
    }


    @OnClick(R.id.tvSeat)
    public void setSeat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(App.seats, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                seatPosition = which;
                tvSeat.setText(App.seats[which]);
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        alertDialog.getWindow().setWindowAnimations(R.style.dialog_anim);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择日期
        if (requestCode == REQUEST_CODE)
            if (resultCode == CalendarActivity.RESULT_OK) {
                year = data.getIntExtra("year", 0);
                month = data.getIntExtra("month", 0);
                day = data.getIntExtra("day", 0);
                tvStartTime.setText(year + "-" + month + "-" + day);
            }

        //选择车站
        if (requestCode == REQUEST_START_CITY_CODE)
            if (resultCode == ChooseCityActivity.RESULT_CHOOSE_CITY) {
                startCity = data.getStringExtra("city");
                tvStartCity.setText(startCity);
            }
        //选择终点车站
        if (requestCode == REQUEST_END_CITY_CODE)
            if (resultCode == ChooseCityActivity.RESULT_CHOOSE_CITY) {
                endCity = data.getStringExtra("city");
                tvEndCity.setText(endCity);
            }
    }

    /**
     * 以下三个方法是继承接口ViewPager.OnPageChangeListener后
     * 需要实现的方法
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int size = ViewPagerFragment.picture.length;
        for (int i = 0; i < size; i++) {

            if (position == i)
                imgVIndicator[i].setImageResource(R.drawable.selected_circle);
            else
                imgVIndicator[i].setImageResource(R.drawable.unselected_circle);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
