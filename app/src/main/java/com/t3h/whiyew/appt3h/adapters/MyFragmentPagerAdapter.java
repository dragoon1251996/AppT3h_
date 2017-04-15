package com.t3h.whiyew.appt3h.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * Created by Whiyew on 27/03/2017.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments;

	public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		
		return fragments.size();
	}

}
