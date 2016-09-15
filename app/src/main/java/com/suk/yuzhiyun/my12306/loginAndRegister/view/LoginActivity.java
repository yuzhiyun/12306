package com.suk.yuzhiyun.my12306.loginAndRegister.view;

import android.app.ProgressDialog;
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
import com.suk.yuzhiyun.my12306.inquire.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

    String url = "http://"+App.ip+":8080/login.user";

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
        startActivity(new Intent(context, RegisterActivity.class));
    }

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

                        try {
                            String result = new JSONObject(s).getString("flag");
                            if ("success".equals(result)) {
                                startActivity(new Intent(context, MainActivity.class));
                            } else {
                                tvSeverMsg.setText("用户名或密码错误");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        tvSeverMsg.setText("连接不到服务器");
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
