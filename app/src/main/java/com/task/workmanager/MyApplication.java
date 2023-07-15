package com.task.workmanager;

import android.app.Application;

import androidx.work.WorkManager;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

    }

}
