package com.suk.yuzhiyun.my12306.ticketList.model.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.suk.yuzhiyun.my12306.ticketList.control.TicketListActivity;
import com.suk.yuzhiyun.my12306.ticketList.model.entity.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuzhiyun on 2016-09-12.
 */
public class TicketListAdapter extends BaseAdapter {

    String url = "http://" + App.ip + ":8080/purchase.ticket";
    Context context;

    public TicketListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return App.mTicketList.size();
    }

    @Override
    public Ticket getItem(int position) {
        return App.mTicketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 优化listView
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.ticket_list_item, null);
            TextView tvStartCity = (TextView) convertView.findViewById(R.id.tvStartCity);
            TextView tvStartTime = (TextView) convertView.findViewById(R.id.tvStartTime);
            TextView tvTrainNum = (TextView) convertView.findViewById(R.id.tvTrainNum);
            TextView tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
            TextView tvEndCity = (TextView) convertView.findViewById(R.id.tvEndCity);
            TextView tvEndTime = (TextView) convertView.findViewById(R.id.tvEndTime);
            Button btnPrice = (Button) convertView.findViewById(R.id.btnPrice);
            CardView cardView = (CardView) convertView.findViewById(R.id.cardView);

            viewHolder = new ViewHolder(cardView, btnPrice, tvStartCity, tvStartTime, tvTrainNum, tvDuration, tvEndCity, tvEndTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.btnPrice.setText("￥" + (getItem(position)).getmPrice());
        viewHolder.tvStartCity.setText((getItem(position)).getmStartStation());
        viewHolder.tvStartTime.setText((getItem(position)).getmStartTime());
        viewHolder.tvEndCity.setText((getItem(position)).getmEndStation());
        viewHolder.tvEndTime.setText((getItem(position)).getmEndTime());
        viewHolder.tvDuration.setText((getItem(position)).getmDuration());
        viewHolder.tvTrainNum.setText((getItem(position)).getmTrainNum());



        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick","onClick");
                View dialogView = LayoutInflater.from(context).inflate(R.layout.ticket_list_item_in_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView);

                /**
                 *
                 * 弹出框点击事件
                 *
                 * */
                TextView tvStartCity=(TextView)dialogView.findViewById(R.id.tvStartCity);
                TextView tvStartTime = (TextView) dialogView.findViewById(R.id.tvStartTime);
                TextView tvTrainNum = (TextView) dialogView.findViewById(R.id.tvTrainNum);
                TextView tvEndCity = (TextView) dialogView.findViewById(R.id.tvEndCity);
                TextView tvEndTime = (TextView) dialogView.findViewById(R.id.tvEndTime);
                TextView tvDuration = (TextView) dialogView.findViewById(R.id.tvDuration);

                TextView tvSeatType0 = (TextView) dialogView.findViewById(R.id.tvSeatType0);
                TextView tvSeatType1 = (TextView) dialogView.findViewById(R.id.tvSeatType1);
                TextView tvSeatType2 = (TextView) dialogView.findViewById(R.id.tvSeatType2);
                TextView tvSeatType3 = (TextView) dialogView.findViewById(R.id.tvSeatType3);
                TextView tvSeatType4 = (TextView) dialogView.findViewById(R.id.tvSeatType4);
                TextView tvSeatType5 = (TextView) dialogView.findViewById(R.id.tvSeatType5);

                TextView tvPrice0 = (TextView) dialogView.findViewById(R.id.tvPrice0);
                TextView tvPrice1 = (TextView) dialogView.findViewById(R.id.tvPrice1);
                TextView tvPrice2 = (TextView) dialogView.findViewById(R.id.tvPrice2);
                TextView tvPrice3 = (TextView) dialogView.findViewById(R.id.tvPrice3);
                TextView tvPrice4 = (TextView) dialogView.findViewById(R.id.tvPrice4);
                TextView tvPrice5 = (TextView) dialogView.findViewById(R.id.tvPrice5);

                Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
                /**
                 * 向服务器发送购票请求
                 * @param v
                 */
                btnOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        StringRequestPost(url);
                    }
                });

                tvStartCity.setText((getItem(position)).getmStartStation());
                tvStartTime.setText((getItem(position)).getmStartTime());
                tvTrainNum.setText((getItem(position)).getmTrainNum());
                tvEndCity.setText((getItem(position)).getmEndStation());
                tvEndTime.setText((getItem(position)).getmEndTime());
                tvDuration.setText((getItem(position)).getmDuration());

                tvSeatType0.setText((getItem(position)).mTicketNum[0]+"张");
                tvSeatType1.setText((getItem(position)).mTicketNum[1]+"张");
                tvSeatType2.setText((getItem(position)).mTicketNum[2]+"张");
                tvSeatType3.setText((getItem(position)).mTicketNum[3]+"张");
                tvSeatType4.setText((getItem(position)).mTicketNum[4]+"张");
                tvSeatType5.setText((getItem(position)).mTicketNum[5]+"张");

                tvPrice0.setText("￥"+(getItem(position)).mTicketPrice[0]);
                tvPrice1.setText("￥"+(getItem(position)).mTicketPrice[1]);
                tvPrice2.setText("￥"+(getItem(position)).mTicketPrice[2]);
                tvPrice3.setText("￥"+(getItem(position)).mTicketPrice[3]);
                tvPrice4.setText("￥"+(getItem(position)).mTicketPrice[4]);
                tvPrice5.setText("￥"+(getItem(position)).mTicketPrice[5]);


//                builder.setTitle(App.mTicketList.get(0).getmDate());
                builder.create().show();

            }
        });

        return convertView;
    }

    /**
     * 传递购票条件到服务器
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
                        Log.i("onResponse", s);
                        Toast.makeText(context, "错误" + s, Toast.LENGTH_SHORT).show();
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
                map.put("ticket", json());
                return map;
            }
        };

        App.getRequestQueue(context).add(request);
    }

    public String json() {
            JSONObject data = new JSONObject();
        Ticket ticket=App.mTicketList.get(0);
        try {
            data.put("username", App.getUserName(context));
            data.put("date",  ticket.getmDate());
            data.put("startstation",  ticket.getmStartStation());
            data.put( "endstation", ticket.getmEndStation());
            data.put("tid", ticket.getmTrainNum());
            data.put("starttime", ticket.getmStartTime());
            data.put("endtime", ticket.getmEndTime());
            data.put("price",ticket.getmPrice());
            data.put("seattype",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

}

class ViewHolder {

    CardView cardView;

    Button btnPrice;

    TextView tvStartCity;
    TextView tvStartTime;
    TextView tvTrainNum;
    TextView tvDuration;
    TextView tvEndCity;
    TextView tvEndTime;


    public ViewHolder(CardView cardView, Button btnPrice, TextView tvStartCity, TextView tvStartTime, TextView tvTrainNum,
                      TextView tvDuration, TextView tvEndCity, TextView tvEndTime) {
        this.cardView = cardView;
        this.btnPrice = btnPrice;
        this.tvStartCity = tvStartCity;
        this.tvStartTime = tvStartTime;
        this.tvTrainNum = tvTrainNum;
        this.tvDuration = tvDuration;
        this.tvEndCity = tvEndCity;
        this.tvEndTime = tvEndTime;
    }
}
