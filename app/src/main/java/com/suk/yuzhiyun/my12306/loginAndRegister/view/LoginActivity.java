package com.suk.yuzhiyun.my12306.loginAndRegister.view;

import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.suk.yuzhiyun.my12306.Application.App;
import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.tvSeverMsg)
    TextView tvSeverMsg;
    @Bind(R.id.tvRegister)
    TextView tvRegister;

    @Bind(R.id.etUserName)
    AppCompatEditText etUserName;
    @Bind(R.id.etUserPwd)
    AppCompatEditText etUserPwd;

    String url = "http://192.168.1.110:8080/login.user";

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initOther() {


    }

    /**
     * 点击事件
     */
    @OnClick(R.id.btnLogin)
    public void login() {
        StringRequestPost(url);
    }
    @OnClick(R.id.tvRegister)
    public void Register() {
       startActivity(new Intent(context,RegisterActivity.class));
    }

    private void StringRequestPost(String url) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("onResponse", s);
                        Toast.makeText(LoginActivity.this, "服务器" + s, Toast.LENGTH_SHORT).show();
                        tvSeverMsg.setText(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("onErrorResponse", volleyError.toString());
                        Toast.makeText(LoginActivity.this, "错误" + volleyError.toString(), Toast.LENGTH_SHORT).show();
                        tvSeverMsg.setText(volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("username", etUserName.getText().toString().trim());
                map.put("password", etUserPwd.getText().toString().trim());
                return map;
            }
        };

      App.getRequestQueue(context).add(request);
    }
}
