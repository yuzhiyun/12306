package com.suk.yuzhiyun.my12306.inquire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;
import com.suk.yuzhiyun.my12306.calendar.CalendarActivity;
import com.suk.yuzhiyun.my12306.calendar.DateUtil;
import com.suk.yuzhiyun.my12306.inquire.viewpager.Adapter;
import com.suk.yuzhiyun.my12306.inquire.viewpager.ViewPagerFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


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
        year = DateUtil.getYear();
        month = DateUtil.getMonth();
        day = DateUtil.getCurrentMonthDay();
        tvStartTime.setText(year + " - " + month + " - " + day);
        initViewPager();

        //viewpager开始轮播
        handler.sendEmptyMessageDelayed(AUTO_MESSAGE, 1000);

        setIndicator();

    }

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

    String[] seats = new String[]{
            "硬    座",
            "软    座",
            "硬    卧",
            "软    卧",
            "特   等  座",
            "商   务  座"
    };

    @OnClick(R.id.tvSeat)
    public void setSeat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.
        builder.setItems(seats, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvSeat.setText(seats[which]);
            }
        });
        builder.create().show();

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
                tvStartTime.setText(year + " - " + month + " - " + day);
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
