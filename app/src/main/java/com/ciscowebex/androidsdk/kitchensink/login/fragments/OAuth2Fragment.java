/*
 * Copyright 2016-2017 Cisco Systems Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.ciscowebex.androidsdk.kitchensink.login.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.ciscowebex.androidsdk.kitchensink.R;
import com.ciscowebex.androidsdk.kitchensink.actions.commands.WebexIdLoginAction;
import com.ciscowebex.androidsdk.kitchensink.ui.BaseFragment;
import com.ciscowebex.androidsdk.kitchensink.launcher.LauncherActivity;
import com.ciscowebex.androidsdk.kitchensink.actions.events.LoginEvent;
import com.ciscowebex.androidsdk.kitchensink.MainActivity;
import com.ciscowebex.androidsdk.kitchensink.SettingsFragment;

import com.github.benoitdion.ln.Ln;
import com.ciscowebex.androidsdk.kitchensink.login.LoginActivity;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OAuth2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OAuth2Fragment extends BaseFragment {

    @BindView(R.id.OAuthWebView)
    public WebView webView;

    public OAuth2Fragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OAuth2Fragment.
     */


    public static OAuth2Fragment newInstance(String id) {
        OAuth2Fragment fragment = new OAuth2Fragment();
        Bundle args = new Bundle();
        args.putString("Callee", id);
        args.putInt(LAYOUT, R.layout.fragment_oauth2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        authorize();
    }

    private void authorize() {
        new WebexIdLoginAction(webView).execute();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        if (event.isSuccessful()) {
            toast("OAuth2 logged in.");
            Bundle extras = new Bundle();
            extras.putString("Callee", getArguments().getString("Callee"));
            extras.putString("frgToLoad", "settingsFragment");
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.putExtras(extras);
            startActivity(i);
        } else {
            toast("OAuth2 logged failed.");
            Ln.e(event.getError().toString());
            authorize();
        }
    }
}
