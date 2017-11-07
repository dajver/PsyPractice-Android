package com.project.dajver.psypractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);
        onCreateView(savedInstanceState);
    }

    public abstract int getViewId();
    public abstract void onCreateView(Bundle savedInstanceState);
}
