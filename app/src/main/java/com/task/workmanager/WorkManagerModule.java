package com.task.workmanager;

import android.content.Context;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

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
    @Provides
    public WorkRequest getOneTimeWorkRequest(){
        return new OneTimeWorkRequest.Builder(MyBackgroundTask.class).build();
    }
}
