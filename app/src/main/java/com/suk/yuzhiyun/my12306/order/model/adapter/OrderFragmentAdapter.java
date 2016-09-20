package com.suk.yuzhiyun.my12306.order.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suk.yuzhiyun.my12306.order.control.fragment.PayedOrderFragment;
import com.suk.yuzhiyun.my12306.order.control.fragment.UnpayedOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhiyun on 2016-09-20.
 */
public class OrderFragmentAdapter extends FragmentPagerAdapter{

    List<Fragment> fragmentList=new ArrayList<Fragment>();
    public OrderFragmentAdapter(FragmentManager fm) {
        super(fm); 
        fragmentList.add(new PayedOrderFragment());
        fragmentList.add(new UnpayedOrderFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
