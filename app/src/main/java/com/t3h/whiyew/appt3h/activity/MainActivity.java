package com.t3h.whiyew.appt3h.activity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.adapters.NavListAdapter;
import com.t3h.whiyew.appt3h.broadcast.Erro;
import com.t3h.whiyew.appt3h.broadcast.GetMessenger;
import com.t3h.whiyew.appt3h.broadcast.ReService;
import com.t3h.whiyew.appt3h.fragments.CreatGroup;
import com.t3h.whiyew.appt3h.fragments.GroupChose;
import com.t3h.whiyew.appt3h.fragments.Load;
import com.t3h.whiyew.appt3h.fragments.ManagerGroup;
import com.t3h.whiyew.appt3h.fragments.Map;
import com.t3h.whiyew.appt3h.fragments.MyAbout;
import com.t3h.whiyew.appt3h.fragments.MyHome;
import com.t3h.whiyew.appt3h.fragments.Notifi;
import com.t3h.whiyew.appt3h.models.NavItem;
import com.t3h.whiyew.appt3h.service.UpdateMarker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int i=0;
    public static int numberOfGroup=0;
    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;
    private static final String TAG = "MainActivity";
    List<NavItem> listNavItems;
    List<Fragment> listFragments;

    ActionBarDrawerToggle actionBarDrawerToggle;

    private PendingIntent mAlarmIntent,mAlarmIntent1;
private PendingIntent mAlarmIntent0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        String android_id = Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        DatabaseReference myRef = database.getReference(android_id);
//        myRef.child("Group").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                MainActivity.numberOfGroup++;
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);

        listNavItems = new ArrayList<NavItem>();
        listNavItems.add(new NavItem("Home", "MyHome page",
                R.drawable.ic_action_home));
        listNavItems.add(new NavItem("Map", "Google map",
                R.drawable.googlemap));
        listNavItems.add(new NavItem("About", "Author's information",
                R.drawable.about));
        listNavItems.add(new NavItem("Creat Group", "No thing",
                R.drawable.addgroup));
        listNavItems.add(new NavItem("Group", "All group of you",
                R.drawable.group));
        listNavItems.add(new NavItem("Notification", "All notification of you",
                R.drawable.notif));
        NavListAdapter navListAdapter = new NavListAdapter(
                getApplicationContext(), R.layout.item_nav_list, listNavItems);

        lvNav.setAdapter(navListAdapter);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new MyHome());
        listFragments.add(new Map());
        listFragments.add(new MyAbout());
        listFragments.add(new CreatGroup());
        listFragments.add(new ManagerGroup());
        listFragments.add(new Notifi());
        listFragments.add(new Load());
        listFragments.add(new GroupChose());
        if( numberOfGroup==0){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, listFragments.get(6)).commit();

        setTitle(listNavItems.get(4).getTitle());
        lvNav.setItemChecked(4, true);}
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, listFragments.get(4)).commit();

            setTitle(listNavItems.get(4).getTitle());
            lvNav.setItemChecked(4, true);
        }
        drawerLayout.closeDrawer(drawerPane);

        lvNav.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, listFragments.get(position))
                        .commit();

                setTitle(listNavItems.get(position).getTitle());
                lvNav.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);

            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);



//background withBroadcast

        Intent lauchIntent0=new Intent(this, GetMessenger.class);
        Intent launchIntent = new Intent(this, ReService.class);
        Intent launchIntent1 = new Intent(this, Erro.class);
        mAlarmIntent = PendingIntent.getBroadcast(this, 0, launchIntent, 0);
        mAlarmIntent0=PendingIntent.getBroadcast(this, 0, lauchIntent0, 0);
        mAlarmIntent1=PendingIntent.getBroadcast(this, 0, launchIntent1, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 1000;
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval, interval,
                mAlarmIntent);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval, 10000,
                mAlarmIntent0);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval, 10000,
                mAlarmIntent1);
//        background with broadcast and service
//        Intent alarm = new Intent(this, ReService.class);
//        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
//        if (alarmRunning == false) {
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 500, pendingIntent);
//
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private boolean isMyServiceRunning(Class<UpdateMarker> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
    }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
