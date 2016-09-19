package com.suk.yuzhiyun.my12306.loginAndRegister.control;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.suk.yuzhiyun.my12306.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    String url = "http://192.168.0.115:8080/register.user";
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
    //     //性别
//    @Bind(R.id.etSex)
//    AppCompatEditText etSex;
    //身份证号
    @Bind(R.id.etCardId)
    AppCompatEditText etCardId;
    //手机
    @Bind(R.id.etPhone)
    AppCompatEditText etPhone;
    //乘客类型
    @Bind(R.id.etType)
    AppCompatEditText etType;


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
     */
    @OnClick(R.id.tvLogin)
    public void login() {
        startActivity(new Intent(context, LoginActivity.class));
    }

    /**
     * 注册
     */
    @OnClick(R.id.btnRegister)
    public void Register() {
        //判断两次输入密码一致性

        String pwd = etUserPwd.getText().toString().trim();
        String pwdAgain = etUserPwdAgain.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String id = etCardId.getText().toString().trim();

        if (pwd.equals(pwdAgain))
            if (verifyIDNumber(id))
                if (verifyPhone(phone))
                    StringRequestPost(url);
                else
                    tvSeverMsg.setText("电话格式不正确");
            else
                tvSeverMsg.setText("身份证格式不正确");
        else
            tvSeverMsg.setText("两次密码输入不一致");
        Log.i("json", json());
    }

    public static String json() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            JSONObject data = new JSONObject();
            data.put("name", "俞志云");
            data.put("pwd", "12345");
            array.put(data);
            jsonObject.put("register", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 验证电话
     *
     * @param phone
     * @return
     */
    public Boolean verifyPhone(String phone) {
        // 电话验证规则
        String regEx = "^1[3|4|5|8][0-9]\\d{8}$";
        return verify(regEx, phone);
    }

    /**
     * 验证身份证号码
     *
     * @param id
     * @return
     */
    public Boolean verifyIDNumber(String id) {
        // 身份证验证规则
        String regEx = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
        return verify(regEx, id);
    }

    /**
     * @param regEx  正则表达式
     * @param string 被验证字符串
     * @return
     */
    public Boolean verify(String regEx, String string) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(string);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;
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
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        Log.i("onResponse", s);

                        if (s.contains("success")) {
                            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, MainActivity.class));
                        } else {
                            tvSeverMsg.setText("用户名或密码错误");
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
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
                map.put("name", etRealName.getText().toString().trim());
//                map.put("sex", etSex.getText().toString().trim());
                map.put("idnumber", etCardId.getText().toString().trim());
                map.put("tel", etPhone.getText().toString().trim());
                //0-成人，1-学生，2-儿童，3-伤残军人
                map.put("type", etType.getText().toString().trim());
                return map;
            }
        };
        App.getRequestQueue(context).add(request);
    }
}
