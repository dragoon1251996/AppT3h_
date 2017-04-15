package com.t3h.whiyew.appt3h.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.Group;

import java.util.List;

/**
 * Created by Whiyew on 27/03/2017.
 */

public class LvGroup extends ArrayAdapter<Group> {
    Context context;
    int i=0;
    int resLayout;
    List<Group> listNavItems;

    public LvGroup(Context context, int resLayout, List<Group> listNavItems) {
        super(context, resLayout, listNavItems);

        this.context = context;
        this.resLayout = resLayout;
        this.listNavItems = listNavItems;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, resLayout, null);
        TextView access = (TextView) v.findViewById(R.id.access);
        TextView name = (TextView) v.findViewById(R.id.name);
        //  TextView language = (TextView) v.findViewById(R.id.laguage);
        Group navItem = listNavItems.get(position);

        access.setText(navItem.getAccess());
        name.setText(navItem.getName());

        //  language.setText(navItem.getLanguage());
        return v;
    }

}

