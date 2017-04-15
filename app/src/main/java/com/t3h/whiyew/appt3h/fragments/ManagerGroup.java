package com.t3h.whiyew.appt3h.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.adapters.LvGroup;
import com.t3h.whiyew.appt3h.models.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiyew on 26/03/2017.
 */

public class ManagerGroup extends Fragment implements AdapterView.OnItemClickListener {
    LvGroup lvGroup;
    ListView listView;
    List<Group> arrayList;
    ArrayList<String> arrayNameGroup;
    public static ArrayList<String> arrayId;
    public static String nameOfGroupClick;
    public static boolean checker = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.f_group, container, false);
        listView = (ListView) v.findViewById(R.id.lv_group);
        arrayList = new ArrayList<>();
        arrayNameGroup = new ArrayList<>();
        ManagerGroup.arrayId = new ArrayList<>();

        lvGroup = new LvGroup(getActivity(), R.layout.item_group, arrayList);

        listView.setAdapter(lvGroup);
        listView.setOnItemClickListener(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference myRef = database.getReference(android_id);
        myRef.child("Group").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    Group group = dataSnapshot.getValue(Group.class);
                    arrayNameGroup.add(group.getName());
                    String[] split = group.getName().split(" ");
                    arrayList.add(new Group(split[0], group.getLanguage(), group.getAccess()));
                    lvGroup.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ManagerGroup.nameOfGroupClick = arrayNameGroup.get(position);
//        new My(getActivity()).execute();
        int tem=position;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle("Add member");

        // set dialog message
        alertDialogBuilder
                .setMessage("What do you want? ")
                .setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new My(getActivity()).execute();

                    }
                })
                .setNegativeButton("Seen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        new My1(getActivity()).execute();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }





    class My extends AsyncTask<Void, Integer, Void> {
        Activity mContex;
        boolean temp = true;

        public My(Activity contex) {
            this.mContex = contex;
        }

        @Override
        protected Void doInBackground(Void... params) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            DatabaseReference myRef = database.getReference("Groups");
            myRef.child(nameOfGroupClick).child("NameOfGroup").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    arrayId.add(dataSnapshot.getValue().toString());
                    checker = false;
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            int i = 0;
            while (temp == true) {
                try {
                    Thread.sleep(500);
                    if (ManagerGroup.checker == false) {
                        publishProgress(1);
                        break;
                    }
                } catch (InterruptedException e) {

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (checker == false) {
                ManagerGroup.checker = true;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, new FragmentGroup());
                fragmentTransaction.commit();
                String[] title = ManagerGroup.nameOfGroupClick.split(" ");
                getActivity().setTitle(title[0]);


            }
        }
    }
    class My1 extends AsyncTask<Void, Integer, Void> {
        Activity mContex;
        boolean temp = true;

        public My1(Activity contex) {
            this.mContex = contex;
        }

        @Override
        protected Void doInBackground(Void... params) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            DatabaseReference myRef = database.getReference("Groups");
            myRef.child(nameOfGroupClick).child("NameOfGroup").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    arrayId.add(dataSnapshot.getValue().toString());
                    checker = false;
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            int i = 0;
            while (temp == true) {
                try {
                    Thread.sleep(500);
                    if (ManagerGroup.checker == false) {
                        publishProgress(1);
                        break;
                    }
                } catch (InterruptedException e) {

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (checker == false) {
                ManagerGroup.checker = true;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, new GroupChose());
                fragmentTransaction.commit();
                String[] title = ManagerGroup.nameOfGroupClick.split(" ");
                getActivity().setTitle(title[0]);


            }
        }
    }
}
