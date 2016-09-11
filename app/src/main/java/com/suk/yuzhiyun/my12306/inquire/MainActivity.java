package com.suk.yuzhiyun.my12306.inquire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;
import com.suk.yuzhiyun.my12306.calendar.CalendarActivity;
import com.suk.yuzhiyun.my12306.calendar.DateUtil;
import com.suk.yuzhiyun.my12306.inquire.viewpager.Adapter;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.vPager)
    ViewPager vPager;

    int year;
    int month;
    int day;

    private static final int REQUEST_CODE = 1;
    @Bind(R.id.tvStartTime)
    TextView tvStartTime;

    @Bind(R.id.tvStartHour)
    TextView tvStartHour;

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
    }

    private void initViewPager() {
        vPager.setAdapter(new Adapter(getSupportFragmentManager()));
    }

    @OnClick(R.id.tvStartTime)
    public void setTvStartTime() {
        startActivityForResult(new Intent(context, CalendarActivity.class), REQUEST_CODE);
    }

    String[] hours = new String[]{
            "00:00 - 24:00",
            "00:00 - 06:00",
            "06:00 - 12:00",
            "12:00 - 18:00",
            "18:00 - 24:00",
    };

    @OnClick(R.id.tvStartHour)
    public void setTvStartHour() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.
        builder.setItems(hours, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvStartHour.setText(hours[which]);
            }
        });
        builder.create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
            if (resultCode == CalendarActivity.RESULT_OK) {
                year = data.getIntExtra("year", 0);
                month = data.getIntExtra("month", 0);
                day = data.getIntExtra("day", 0);
                tvStartTime.setText(year + " - " + month + " - " + day);
            }


    }
}
