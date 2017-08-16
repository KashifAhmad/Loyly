package com.ghosttech.myloyly.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ghosttech.myloyly.fragments.AufgussMainFragment;
import com.ghosttech.myloyly.fragments.ChartsFragment;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.fragments.EssesntialOilFragment;
import com.ghosttech.myloyly.fragments.RegistrationFragment;
import com.ghosttech.myloyly.utilities.Configuration;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.getStringExtra("frag_id").equals("oils")) {
            fragment = new EssesntialOilFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, fragment).commit();

        }else if (intent.getStringExtra("frag_id").equals("charts")){
            fragment = new ChartsFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, fragment).commit();
        }else if (intent.getStringExtra("frag_id").equals("aufguss")){
            fragment = new AufgussMainFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, fragment).commit();
        }
    }

}
