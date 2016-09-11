package com.suk.yuzhiyun.my12306.inquire.viewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.suk.yuzhiyun.my12306.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    int pictureIndex;
    public static int[] picture = new int[]{
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,};

    ImageView image;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    /**
     * @param position 图片下标
     */
    public ViewPagerFragment(int index) {
        pictureIndex = index;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_pager, container, false);

         image= (ImageView) view.findViewById(R.id.image);
        image.setImageDrawable(getResources().getDrawable(picture[pictureIndex]));
        return view;
    }

}
