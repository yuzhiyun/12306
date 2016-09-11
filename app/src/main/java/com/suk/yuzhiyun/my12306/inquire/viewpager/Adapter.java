package com.suk.yuzhiyun.my12306.inquire.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yuzhiyun on 2016-09-11.
 */
public class Adapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();


    public Adapter(FragmentManager fm) {
        super(fm);
        for(int i=0;i<ViewPagerFragment.picture.length;i++){
            ViewPagerFragment viewPagerFragment = new ViewPagerFragment(i);
            fragmentArrayList.add(viewPagerFragment);
        }


    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
