package com.suk.yuzhiyun.my12306.loginAndRegister.view;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import com.suk.yuzhiyun.my12306.reserve.MainActivity;
import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseActivity {

    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnRegister)
    Button btnRegister;

    @Bind(R.id.image)
    ImageView image;

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
    @OnClick(R.id.image)
    public void  image(){
        startActivity(new Intent(context,MainActivity.class));
    }
}
