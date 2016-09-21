package com.suk.yuzhiyun.my12306.loginAndRegister.control;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.suk.yuzhiyun.my12306.Application.App;
import com.suk.yuzhiyun.my12306.R;
import com.suk.yuzhiyun.my12306.base.BaseActivity;
import com.suk.yuzhiyun.my12306.main.MainActivity;
import com.suk.yuzhiyun.my12306.loginAndRegister.model.Code;

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
    //    用户名
    @Bind(R.id.etUserName)
    AppCompatEditText etUserName;
    //    密码
    @Bind(R.id.etUserPwd)
    AppCompatEditText etUserPwd;
    //    验证码输入框
    @Bind(R.id.etCode)
    AppCompatEditText etCode;
    //    验证码图片
    @Bind(R.id.imgCode)
    ImageView imgCode;

    String url = "http://" + App.ip + "/login.user";

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initOther() {
        imgCode.setImageBitmap(Code.getInstance().createBitmap());

    }

    /**
     * 点击事件
     */
    @OnClick(R.id.btnLogin)
    public void login() {
//Log.i("login","code: "+Code.getInstance().getCode().toLowerCase()+" 填写的验证码"+etCode.getText().toString().trim());
        if (Code.getInstance().getCode().toLowerCase().equals(etCode.getText().toString().trim()))
            StringRequestPost(url);
        else
            tvSeverMsg.setText("验证码不匹配");
    }


    /**
     * 更新验证码
     */
    @OnClick(R.id.imgCode)
    public void code() {
        imgCode.setImageBitmap(Code.getInstance().createBitmap());
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
                        Log.i("登录onResponse", s);
                        try {
                            String result = new JSONObject(s).getString("flag");
                            if ("success".equals(result)) {
                                saveInSharedPreference();
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

    /**
     * 登录成功的时候在本地存储用户名密码
     */
    private void saveInSharedPreference() {
//        //                      第一步，获得SharedPreferences对象，第一个参数指定存储数据的文件名称。第二个参数代表模式，一般默认Activity.MODE_PRIVATE
//        SharedPreferences SPsavaData = context.getSharedPreferences("user", Activity.MODE_PRIVATE);

        SharedPreferences SPsaveData=App.getInstance().getSharedPreferences(context);
        //                      第二步，获得editor对象
        SharedPreferences.Editor editor = SPsaveData.edit();
//                      第三步，存储数据
        editor.putString("username", etUserName.getText().toString().trim());
        editor.putString("password", etUserPwd.getText().toString().trim());
//                      第四步，提交操作，类似于数据库
        editor.commit();
    }
}
