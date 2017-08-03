package com.ghosttech.myloyly.activities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.fragments.LanguageFragment;
import com.ghosttech.myloyly.fragments.MainFragment;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();
        if (intent.getIntExtra("frag_id", 0) == 1) {
            fragment = new MainFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            fragment = new LanguageFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        }


    }

}
