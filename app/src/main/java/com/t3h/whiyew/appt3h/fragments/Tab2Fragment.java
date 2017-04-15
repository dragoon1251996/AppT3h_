package com.t3h.whiyew.appt3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t3h.whiyew.appt3h.R;

/**
 * Created by Whiyew on 27/03/2017.
 */
public class Tab2Fragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab2fragment, container, false);
		return v;
	}

}
