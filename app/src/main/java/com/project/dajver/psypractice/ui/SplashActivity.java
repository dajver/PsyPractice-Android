package com.project.dajver.psypractice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.project.dajver.psypractice.R;

import static com.project.dajver.psypractice.etc.Constants.SPLASH_TIME_OUT;

/**
 * Created by gleb on 11/9/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, TabsActivity.class));
            finish();
        }, SPLASH_TIME_OUT);
    }
}