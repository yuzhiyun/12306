package com.suk.yuzhiyun.my12306.reserve;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;
import com.suk.yuzhiyun.my12306.calendar.CalendarActivity;
import com.suk.yuzhiyun.my12306.calendar.DateUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    int year;
    int month;
    int day;

    private static final int REQUEST_CODE = 1;
    @Bind(R.id.tvStartTime)
    TextView tvStartTime;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initOther() {
        year = DateUtil.getYear();
        month = DateUtil.getMonth();
        day = DateUtil.getCurrentMonthDay();
        tvStartTime.setText(year + "-" + month + "-" + day);
    }

    @OnClick(R.id.tvStartTime)
    public void setTvStartTime() {
        startActivityForResult(new Intent(context, CalendarActivity.class), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
            if (resultCode == CalendarActivity.RESULT_OK) {
                year = data.getIntExtra("year", 0);
                month = data.getIntExtra("month", 0);
                day = data.getIntExtra("day", 0);
                tvStartTime.setText(year + "-" + month + "-" + day);
            }


    }
}
