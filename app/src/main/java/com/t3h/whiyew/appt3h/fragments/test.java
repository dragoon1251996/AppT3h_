package com.t3h.whiyew.appt3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t3h.whiyew.appt3h.R;

/**
 * Created by Whiyew on 28/03/2017.
 */

public class test extends Fragment {
    private TextView adress,age,name,gmail,language;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infomation, container, false);
        return v;
    }


}
