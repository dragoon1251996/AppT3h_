package com.t3h.whiyew.appt3h.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.activity.MainActivity;
import com.t3h.whiyew.appt3h.adapters.MyFragmentPagerAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by Whiyew on 27/03/2017.
 */
public class MyHome extends Fragment implements OnTabChangeListener,
        OnPageChangeListener {
    public static int indexGroup = 0;

    private TabHost tabHost;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    int i = 0;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tabs_viewpager_layout, container, false);

        // We put TabHostView Pager here:
        /*
         * part1*****************************************************************
		 * ********  DONE! !!!!! :))
		 */

        i++;
        // init tabhost
        this.initializeTabHost(savedInstanceState);

        // init ViewPager
        this.initializeViewPager();

		/*
		 * part1*****************************************************************
		 * ********
		 */

        return v;
    }

    // fake content for tabhost
    class FakeContent implements TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();

//		fragments.add(new Tab1Fragment());
//		fragments.add(new Tab2Fragment());
//		fragments.add(new Tab3Fragment());
//		fragments.add(new Tab1Fragment());
//		fragments.add(new Tab2Fragment());
//		fragments.add(new Tab3Fragment());
        for (int i = 0; i < MainActivity.numberOfGroup; i++) {

            fragments.add(new Tab1Fragment());
        }
        this.myViewPagerAdapter = new MyFragmentPagerAdapter(
                getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);

    }

    private void initializeTabHost(Bundle args) {
        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        for (int i = 0; i < MainActivity.numberOfGroup; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec("Group  " + i);
            tabSpec.setIndicator("Group " + i);
            tabSpec.setContent(new FakeContent(getActivity()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();
        // Toast.makeText(getActivity(),pos+"",Toast.LENGTH_LONG).show();
        indexGroup = pos;

        this.viewPager.setCurrentItem(pos);
        HorizontalScrollView hScrollView = (HorizontalScrollView) v
                .findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
        indexGroup = position;
//        Toast.makeText(getContext(),MainActivity.indexGroup+"",Toast.LENGTH_LONG).show();
    }
}
