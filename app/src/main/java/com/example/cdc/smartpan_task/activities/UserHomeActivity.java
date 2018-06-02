package com.example.cdc.smartpan_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Fragments.ContactsFragment;
import com.example.cdc.smartpan_task.activities.Fragments.MapFragment;
import com.example.cdc.smartpan_task.activities.Fragments.SearchFragment;
import com.example.cdc.smartpan_task.activities.Pref.Preference;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    ViewPager homeViewPager;
    TabLayout homeTabLayout;
    Toolbar homeToolbar;
    Preference pref;
    String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        pref = new Preference(this);
        pref.checkLogin();
        userName = pref.getUserName();

        initialize();

        setSupportActionBar(homeToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(userName);
        }

        setUpViewPager(homeViewPager);
        homeTabLayout.setupWithViewPager(homeViewPager);
    }

    private void initialize(){
        homeTabLayout = (TabLayout) findViewById(R.id.home_tabLayout);
        homeViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        homeToolbar = (Toolbar) findViewById(R.id.user_home_toolbar);
    }

    /**
    * Handling back event
    * */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    /**
    * Create logout menu
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                pref.logoutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
    * Set up each tab with its fragment
    * */
    private void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MapFragment(), "Map");
        adapter.addFrag(new SearchFragment(), "Search");
        adapter.addFrag(new ContactsFragment(), "Contacts");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
