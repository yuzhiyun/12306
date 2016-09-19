package com.suk.yuzhiyun.my12306.personalInfor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class InforActivity extends BaseActivity {

    @Bind(R.id.tvUsername)
    TextView tvUsername;

    @Bind(R.id.tvRealName)
    TextView tvRealName;

    @Bind(R.id.tvSex)
    TextView tvSex;

    @Bind(R.id.tvID)
    TextView tvID;

    @Bind(R.id.tvPhone)
    TextView tvPhone;

    @Bind(R.id.tvPassengerType)
    TextView tvPassengerType;

    String url = "http://" + App.ip + ":8080/getUserInfo.user";

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_infor);
    }

    @Override
    protected void initOther() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人资料");
        //用户名密码是保存在本地的
        tvUsername.setText(App.getUserName(context));

        StringRequestPost(url);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_infor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                startActivity(new Intent(this, EditInforActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void StringRequestPost(String url) {
        //        进度对话框
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在获取...");
        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    public void onResponse(String s) {
                        progressDialog.dismiss();

                        Toast.makeText(InforActivity.this, "服务器"+s, Toast.LENGTH_SHORT).show();
//                        try {
//                            String result = new JSONObject(s).getString("flag");
//                            if ("success".equals(result)) {
//                                startActivity(new Intent(context, MainActivity.class));
//                            } else {
//                                tvSeverMsg.setText("用户名或密码错误");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        Log.i("onResponse",s);
//                        {"getUserInfo":{"id":4,"idnumber":"123","name":"maxiaolong","password":"e10adc3949ba59abbe56e057f20f883e","sex":1,"tel":"294","type":2,"username":"maxiaolong"}}
                        try {

                            JSONObject msg=new JSONObject(s).getJSONObject("getUserInfo");

                            tvRealName.setText(msg.getString("name"));
                            tvSex.setText(App.sex[Integer.parseInt(msg.getString("sex"))]);
                            tvID.setText(msg.getString("idnumber"));
                            tvPhone.setText(msg.getString("tel"));
                            tvPassengerType.setText(App.type[Integer.parseInt(msg.getString("type"))]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
//                        tvSeverMsg.setText("连接不到服务器");
                        Toast.makeText(InforActivity.this, "错误"+volleyError.toString(), Toast.LENGTH_SHORT).show();
//
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("username", App.getUserName(context));
                map.put("password", App.getPassword(context));
                return map;
            }
        };

        App.getRequestQueue(context).add(request);
    }


}
