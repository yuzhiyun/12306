package com.suk.yuzhiyun.my12306.ticketList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suk.yuzhiyun.my12306.R;

/**
 * Created by yuzhiyun on 2016-09-12.
 */
public class TicketListAdapter extends BaseAdapter {
    Context context;

    TicketListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 优化listView
//        ViewHolder viewHolder;
//        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.ticket_list_item, null);
//        }
        return convertView;
    }
}
    class ViewHolder {

        ImageView imageView;
        TextView title;
        TextView duration;
        TextView artist;

        public ViewHolder(ImageView pImageView, TextView pTitle,
                          TextView pDuration, TextView pArtist) {
            imageView = pImageView;
            title = pTitle;
            duration = pDuration;
            artist = pArtist;
        }

    }
