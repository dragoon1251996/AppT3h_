package com.t3h.whiyew.appt3h.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.fragments.FragmentGroup;
import com.t3h.whiyew.appt3h.fragments.ManagerGroup;
import com.t3h.whiyew.appt3h.models.FromGroup;
import com.t3h.whiyew.appt3h.models.Member;

import java.util.List;

/**
 * Created by Whiyew on 30/03/2017.
 */

public class LvMemberAdd extends ArrayAdapter<Member> {

    Context context;
    int resLayout;
    List<Member> listNavItems;

    public LvMemberAdd(Context context, int resLayout, List<Member> listNavItems) {
        super(context, resLayout, listNavItems);

        this.context = context;
        this.resLayout = resLayout;
        this.listNavItems = listNavItems;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, resLayout, null);

        TextView tvTitle = (TextView) v.findViewById(R.id.name);
        TextView tvSubTitle = (TextView) v.findViewById(R.id.access);
        ImageView navIcon = (ImageView) v.findViewById(R.id.add);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Add member");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want add this member? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String android_id = Settings.Secure.getString(context.getContentResolver(),
                                        Settings.Secure.ANDROID_ID);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference(FragmentGroup.ID.get(position));
                                myRef.child("Messenger").setValue("true");
                                listNavItems.remove(position);
                                notifyDataSetChanged();
                                myRef.child("Notification").push().setValue(new FromGroup(android_id, ManagerGroup.nameOfGroupClick));
                                Toast.makeText(context,"Wait for Feedback!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        Member navItem = listNavItems.get(position);

        tvTitle.setText(navItem.getName());
        tvSubTitle.setText(navItem.getGmail());
        //    navIcon.setImageResource(navItem.getResIcon());

        return v;
    }


}


