package com.task.workmanager.modules;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.task.workmanager.MyBackgroundTask;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class WorkManagerModule {

    @Singleton
    @Provides
    public WorkManager getWorkManager(@ApplicationContext Context context){
        return WorkManager.getInstance(context);
    }

}
