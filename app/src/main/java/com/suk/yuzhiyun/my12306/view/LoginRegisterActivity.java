package com.suk.yuzhiyun.my12306.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseActivity {

    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnRegister)
    Button btnRegister;


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login_register);
    }

    @Override
    protected void initOther() {

    }

    @OnClick(R.id.btnLogin)
    public void  login(){
        startActivity(new Intent(context,LoginActivity.class));
    }
    @OnClick(R.id.btnRegister)
    public void  register(){
        startActivity(new Intent(context,RegisterActivity.class));
    }
}
