package com.suk.yuzhiyun.my12306.loginAndRegister.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.suk.yuzhiyun.my12306.Application.App;
import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    String url = "http://192.168.1.110:8080/register.user";
    //用户名
    @Bind(R.id.etUserName)
    AppCompatEditText etUserName;
    //密码
    @Bind(R.id.etUserPwd)
    AppCompatEditText etUserPwd;
    //确认密码
    @Bind(R.id.etUserPwdAgain)
    AppCompatEditText etUserPwdAgain;
    //真实姓名
    @Bind(R.id.etRealName)
    AppCompatEditText etRealName;
    //身份证号
    @Bind(R.id.etCardId)
    AppCompatEditText etCardId;
    //手机
    @Bind(R.id.etPhone)
    AppCompatEditText etPhone;
    //注册
    @Bind(R.id.btnRegister)
    Button btnRegister;
    //登录
    @Bind(R.id.tvLogin)
    TextView tvLogin;
    //服务器信息
    @Bind(R.id.tvSeverMsg)
    TextView tvSeverMsg;


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initOther() {

    }
    /**
     * 跳转到登录
     * */
    @OnClick(R.id.tvLogin)
    public void login(){
        startActivity(new Intent(context,LoginActivity.class));
    }
    /**
     * 注册
     * */
    @OnClick(R.id.btnRegister)
    public void Register(){
        //判断两次输入密码一致性
        if(etUserPwd.getText().toString().trim().equals(etUserPwdAgain.getText().toString().trim()))
            tvSeverMsg.setText("两次输入密码不一致");

        StringRequestPost(url);
    }

    private void StringRequestPost(String url) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("onResponse", s);
                        Toast.makeText(context, "服务器" + s, Toast.LENGTH_SHORT).show();
                        tvSeverMsg.setText(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("onErrorResponse", volleyError.toString());
                        Toast.makeText(context, "错误" + volleyError.toString(), Toast.LENGTH_SHORT).show();
                        tvSeverMsg.setText(volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("username", etUserName.getText().toString().trim());
                map.put("password", etUserPwd.getText().toString().trim());
                map.put("name", etUserPwd.getText().toString().trim());
                map.put("sex", etUserPwd.getText().toString().trim());
                map.put("idnumber", etUserPwd.getText().toString().trim());
                map.put("tel", etUserPwd.getText().toString().trim());
                //0-成人，1-学生，2-儿童，3-伤残军人
                map.put("type", etUserPwd.getText().toString().trim());
                return map;
            }
        };
        App.getRequestQueue(context).add(request);
    }
}
