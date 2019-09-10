package com.ciscowebex.androidsdk.kitchensink;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import com.ciscowebex.androidsdk.kitchensink.actions.commands.LogoutAction;
import com.ciscowebex.androidsdk.kitchensink.actions.events.LogoutEvent;
import com.ciscowebex.androidsdk.kitchensink.launcher.LauncherActivity;
import com.ciscowebex.androidsdk.kitchensink.launcher.fragments.CallFragment;
import com.ciscowebex.androidsdk.kitchensink.launcher.fragments.FeedbackFragment;
import com.ciscowebex.androidsdk.kitchensink.launcher.fragments.MessageFragment;
import com.ciscowebex.androidsdk.kitchensink.launcher.fragments.SetupFragment;
import com.ciscowebex.androidsdk.kitchensink.ui.BaseFragment;
import com.ciscowebex.androidsdk.kitchensink.MainActivity;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ciscowebex.androidsdk.kitchensink.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;



import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class SettingsFragment extends Fragment {

    View view;

    private BaseFragment setupFragment;
    private BaseFragment feedbackFragment;
    private BaseFragment messageFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, null);
        ButterKnife.bind(this, view);
        EditText et = (EditText) view.findViewById(R.id.inputCallee);
        //String callee = et.getText().toString();
        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.buttonSignIn);
        if(isLoggedIn()==1){
            toggle.setChecked(true);
        }
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String callee = et.getText().toString();
                    //login
                    if(callee.isEmpty()){
                        toggle.setChecked(false);
                        toast("Please enter valid SIP Address below before logging in");

                    }else{
                        Intent intent = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
                        intent.putExtra("Callee", callee);
                        startActivity(intent);
                    }
                } else {
                    //logout
                    new LogoutAction().execute();
                }
            }
        });

        return view;
    }

    private int isLoggedIn(){
        Bundle args = getArguments();
        return args.getInt("LoggedIn");
    }

    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public SettingsFragment() {
        // Required empty public constructor
        feedbackFragment = new FeedbackFragment();
    }


   /* @OnClick(R.id.buttonSignIn)
    public void login(){
        Intent intent = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
        startActivity(intent);
    }*/


    @OnClick(R.id.buttonFeedback)
    public void sendFeedback() {
        ((MainActivity) getActivity()).replace(feedbackFragment);
    }


    @SuppressWarnings("unused")
    @Subscribe
    public void onEventMainThread(LogoutEvent event) {
        Toast.makeText(getActivity(), "Logout success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        getActivity().finish();
    }
}


