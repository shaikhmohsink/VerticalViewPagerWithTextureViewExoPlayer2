package com.mohsin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30-07-2016.
 */
public class PopularCategoriesFragmentAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;

    // List of fragments which are going to set in the view pager widget
    List<Fragment> fragments;

    /**
     * Constructor
     *
     * @param fm
     *            interface for interacting with Fragment objects inside of an
     *            Activity
     */
    public PopularCategoriesFragmentAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        this.mNumOfTabs = mNumOfTabs;
    }

    /**
     * Add a new fragment in the list.
     *
     * @param fragment
     *            a new fragment
     */
    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }

    @Override
    /*public int getCount() {
        return this.fragments.size();
    }*/
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //do nothing, this will save the instance and not let it destroy it
    }
}