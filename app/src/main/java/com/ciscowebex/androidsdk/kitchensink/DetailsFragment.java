package com.ciscowebex.androidsdk.kitchensink;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;
import android.view.View;
import android.widget.Toast;

import com.ciscowebex.androidsdk.kitchensink.MainActivity;
import com.ciscowebex.androidsdk.kitchensink.launcher.LauncherActivity;
import com.ciscowebex.androidsdk.kitchensink.launcher.fragments.MessageFragment;

public class DetailsFragment extends Fragment {
    View view;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, null);
        ButterKnife.bind(this, view);
        //setupFragment = new SetupFragment();
        //feedbackFragment = new FeedbackFragment();
        return view;
    }


    private int isLoggedIn(){

        return getArguments().getInt("LoggedIn");
    }

    private String SIPAddress(){
        return getArguments().getString("Callee");
    }

    @OnClick({R.id.messageIcon})
    public void message(){
        if (isLoggedIn() == 1) {
            Intent i = new Intent(getActivity(), LauncherActivity.class);
            Bundle extras = new Bundle();
            extras.putString("frgToLoad", "MessageFragment");
            extras.putString("Callee", SIPAddress());
            i.putExtras(extras);
            startActivity(i);
            getActivity().finish();
        }else{
            toast("Login in Settings to use messaging!");
        }
    }

    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.callIcon})
    public void call(){
        if(isLoggedIn()==1) {
            Intent i = new Intent(getActivity(), LauncherActivity.class);
            Bundle extras = new Bundle();
            extras.putString("frgToLoad", "CallFragment");
            extras.putString("Callee", SIPAddress());
            i.putExtras(extras);
            startActivity(i);
            getActivity().finish();
        }else{
            toast("Login in Settings to place a call!");
        }
    }
}
