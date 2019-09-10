package com.ciscowebex.androidsdk.kitchensink;

import android.os.Bundle;
import android.view.MenuItem;

import android.support.design.widget.BottomNavigationView;

import com.ciscowebex.androidsdk.kitchensink.R;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.Activity;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.ciscowebex.androidsdk.kitchensink.ui.BaseFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity
            implements BottomNavigationView.OnNavigationItemSelectedListener {

        private int loggedIn = 0;

        @Override
         protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            getFragmentManager().popBackStackImmediate();
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            BottomNavigationView navView = findViewById(R.id.nav_view);

            navView.setOnNavigationItemSelectedListener(this);
            if (getIntent().getExtras()!=null){
                if(getIntent().getExtras().getString("frgToLoad").equals("settingsFragment")){
                    loggedIn = 1;
                    Fragment frag = new SettingsFragment();
                    Bundle args = new Bundle();
                    args.putInt("LoggedIn", loggedIn);
                    frag.setArguments(args);
                    loadFragment(frag);
                }else{
                    Fragment frag = new DetailsFragment();
                    Bundle args = new Bundle();
                    args.putInt("LoggedIn", 1);
                    frag.setArguments(args);
                    loadFragment(frag);
                }
            }else {
                loadFragment(new HomeFragment());
            }
        }


        private boolean loadFragment(Fragment fragment){
            if(fragment!=null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;
        }

        public void replace(BaseFragment fragment) {
            fragment.replace(this, R.id.fragment_container);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item){
            Bundle args = new Bundle();

            Fragment fragment = null;
            switch(item.getItemId()){
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_details:
                    fragment = new DetailsFragment();
                    args.putInt("LoggedIn", loggedIn);
                    args.putString("Callee", getIntent().getExtras().getString("Callee"));
                    fragment.setArguments(args);
                    break;
                case R.id.navigation_settings:
                    fragment = new SettingsFragment();
                    args.putInt("LoggedIn", loggedIn);
                    fragment.setArguments(args);
                    break;
            }
            return loadFragment(fragment);
        }
}
