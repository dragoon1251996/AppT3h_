package com.t3h.whiyew.appt3h.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.FromGroup;
import com.t3h.whiyew.appt3h.models.Group;

import java.util.List;

/**
 * Created by Whiyew on 30/03/2017.
 */

public class AdapterNotificaion extends ArrayAdapter<FromGroup> {
    Context context;
    int resLayout;
    List<FromGroup> listNavItems;

    public AdapterNotificaion(Context context, int resLayout, List<FromGroup> listNavItems) {
        super(context, resLayout, listNavItems);

        this.context = context;
        this.resLayout = resLayout;
        this.listNavItems = listNavItems;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, resLayout, null);
        TextView access = (TextView) v.findViewById(R.id.access);
        TextView name = (TextView) v.findViewById(R.id.name);
        //  TextView language = (TextView) v.findViewById(R.id.laguage);
        FromGroup navItem = listNavItems.get(position);

        access.setText("Group: " + navItem.getNamegroup());
        name.setText("Name: " + navItem.getNamefrom().split(" ")[0]);


        Button yes = (Button) v.findViewById(R.id.yes);
        Button no = (Button) v.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                DatabaseReference myRef = database.getReference(android_id);
                myRef.child("Group").push().setValue(new Group(listNavItems.get(position).getNamegroup(), "english", "public"));
                DatabaseReference databaseReference = database.getReference("Groups");
                databaseReference.child(listNavItems.get(position).getNamegroup()).child("NameOfGroup").push().setValue(android_id);
                listNavItems.remove(position);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "You are join Group! ", Toast.LENGTH_LONG).show();
            }
        });
        //  language.setText(navItem.getLanguage());
        return v;
    }

}
