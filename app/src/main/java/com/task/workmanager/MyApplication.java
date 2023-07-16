package com.task.workmanager;

import android.app.Application;

import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;
import androidx.work.WorkManager;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {


    @Inject
    HiltWorkerFactory hiltWorkerFactory;

    @Override
    public void onCreate() {
        super.onCreate();

        WorkManager.initialize(this, new Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build());
    }

}

/*
If we need to pass dependencies to Worker with hilt we need to change the default configuration of WorkManager using
setWorkerFactory and passing the HiltWorkerFactory and initialize the WorkManager.
Now the Worker will get dependencies from hilt.

Also use the Provider in AndroidManifest.xml
 */
