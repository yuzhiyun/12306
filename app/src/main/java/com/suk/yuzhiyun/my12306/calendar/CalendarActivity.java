package com.suk.yuzhiyun.my12306.calendar;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import android.support.v7.widget.Toolbar;
public class CalendarActivity extends BaseActivity {

    @Bind(R.id.myCalendar)
    MyCalendar myCalendar;

    @Bind(R.id.imgLeft)
    ImageView imgLeft;
    @Bind(R.id.imgRight)
    ImageView imgRight;

    @Bind(R.id.tvTime)
    TextView tvTime;

    int year;
    int month;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected void initOther() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        year=DateUtil.getYear();
        month=DateUtil.getMonth();
        tvTime.setText(year + "--" + month);

        myCalendar.setOnCalendarClickListener(new MyCalendar.OnCalendarClickListener() {
            @Override
            public void onCalendaeClick(int dateNum, CalendarConfig.CalendarState calendarState) {
                if(calendarState== CalendarConfig.CalendarState.NO_CURRENT_MONTH)
                    Toast.makeText(CalendarActivity.this, "请选择本月日期", Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(CalendarActivity.this, "几号："+dateNum, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("year",year);
                    intent.putExtra("month",month);
                    intent.putExtra("day",dateNum);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.imgLeft)
    public void lastMonth() {
        if (1 == month) {
            month = 12;
            year -= 1;
        } else
            month -= 1;
        update();



    }



    @OnClick(R.id.imgRight)
    public void nextMonth() {
        if (12 == month) {
            month = 1;
            year += 1;
        } else
            month += 1;

        update();
    }

    private void update() {
        setTimeToTextView();
        myCalendar.setYearMonth(year, month);

    }
    /**
     * 修改月份之后
     */
    private void setTimeToTextView() {
        if (month < 10)
            tvTime.setText(year + "--0" + month);
        else
            tvTime.setText(year + "--" + month);
    }
}
