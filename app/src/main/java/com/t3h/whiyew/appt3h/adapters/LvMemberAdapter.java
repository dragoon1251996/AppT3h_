package com.t3h.whiyew.appt3h.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.Member;

import java.util.List;

/**
 * Created by Whiyew on 26/03/2017.
 */

public class LvMemberAdapter  extends ArrayAdapter<Member> {

        Context context;
        int resLayout;
        List<Member> listNavItems;

public LvMemberAdapter(Context context, int resLayout, List<Member> listNavItems) {
        super(context, resLayout, listNavItems);

        this.context = context;
        this.resLayout = resLayout;
        this.listNavItems = listNavItems;
        }

@SuppressLint("ViewHolder") @Override
public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, resLayout, null);

        TextView tvTitle  = (TextView) v.findViewById(R.id.name);
        TextView tvSubTitle = (TextView) v.findViewById(R.id.access);
      //  ImageView navIcon =  (ImageView) v.findViewById(R.id.nav_icon);

        Member navItem = listNavItems.get(position);

        tvTitle.setText(navItem.getName());
        tvSubTitle.setText(navItem.getGmail());
    //    navIcon.setImageResource(navItem.getResIcon());

        return v;
        }


        }
