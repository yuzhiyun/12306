package com.suk.yuzhiyun.my12306.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.suk.yuzhiyun.my12306.R;
import butterknife.ButterKnife;

/**
 * Created by yuzhiyun on 2016-09-07.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        //设置layout
        setLayoutView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initOther();

    }
    protected abstract void setLayoutView();
    protected abstract void initOther();

}
