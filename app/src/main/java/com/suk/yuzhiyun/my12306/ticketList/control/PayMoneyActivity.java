package com.suk.yuzhiyun.my12306.ticketList.control;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.suk.yuzhiyun.my12306.ticketList.model.entity.Ticket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class PayMoneyActivity extends BaseActivity {
    @Bind(R.id.tvStartCity)
    TextView tvStartCity;

    @Bind(R.id.tvTrainNum)
    TextView tvTrainNum;

    @Bind(R.id.tvEndCity)
    TextView tvEndCity;

    @Bind(R.id.tvEndTime)
    TextView tvEndTime;

    @Bind(R.id.tvDuration)
    TextView tvDuration;

    @Bind(R.id.tvStartTime)
    TextView tvStartTime;

    @Bind(R.id.tvUsername)
    TextView tvUsername;

    @Bind(R.id.tvUserType)
    TextView tvUserType;

    @Bind(R.id.tvCarriage)
    TextView tvCarriage;

    @Bind(R.id.tvSeatNum)
    TextView tvSeatNum;

    @Bind(R.id.tvPrice)
    TextView tvPrice;

@Bind(R.id.tvDate)
    TextView tvDate;


    String url = "http://" + App.ip + ":8080/pay.ticket";

    @Bind(R.id.btnPay)
    Button btnPay;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_pay_moneyctivity);
    }

    @Override
    protected void initOther() {


        getSupportActionBar().setTitle("支付");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i("idusernamePosition", getIntent().getStringExtra("id") + "");
        Log.i("idusernamePosition", App.getUserName(context) + "");
        Log.i("idusernamePosition", getIntent().getIntExtra("position", -1) + "");
        int pos = getIntent().getIntExtra("position", -1);
        if (pos != -1) {
//            日期
            tvDate.setText(getIntent().getStringExtra("date"));

            tvStartCity.setText(App.mTicketList.get(pos).getmStartStation());
            tvEndCity.setText(App.mTicketList.get(pos).getmEndStation());
            tvDuration.setText(App.mTicketList.get(pos).getmDuration());
            tvTrainNum.setText(App.mTicketList.get(pos).getmTrainNum());
            tvStartTime.setText(App.mTicketList.get(pos).getmStartTime());
            tvEndTime.setText(App.mTicketList.get(pos).getmEndTime());

            tvUsername.setText(App.getUserName(context));

            tvUserType.setText(App.type[getIntent().getIntExtra("utype",-1)]);
            tvCarriage.setText("车厢号:"+getIntent().getIntExtra("gnumber",-1)+"");
            tvPrice.setText("￥"+getIntent().getIntExtra("price",-1)+"");
            tvSeatNum.setText("座位号:"+getIntent().getIntExtra("seatnumber",-1)+"");



        }

    }

    @OnClick(R.id.btnPay)
    public void btnPay() {
        StringRequestPost(url);
    }

    /**
     * 支付，需要传递username 和票的id
     *
     * @param url
     */
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
                        Log.i("onResponse", "" + s);
                        try {
                            JSONObject object = new JSONObject(s);
                            if ("success".equals(object.getString("flag"))) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("恭喜您，支付成功！！")
                                        .setCancelable(false)
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                                startActivity(new Intent(context, MainActivity.class));
                                            }
                                        });
                                builder.create().show();
                            } else
                                Toast.makeText(context, "支付失败" + s, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "错误" + volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("pay", json());
                return map;
            }
        };

        App.getRequestQueue(context).add(request);
    }

    public String json() {
        JSONObject data = new JSONObject();

        try {
            data.put("username", App.getUserName(context));
            data.put("id", getIntent().getStringExtra("id"));
//            data.put("date", ticket.getmDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

}
