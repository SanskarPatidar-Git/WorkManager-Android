package com.task.workmanager.modules;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkRequest;

import com.task.workmanager.MyBackgroundTask;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class WorkActivityModule {

    @Provides
    public Constraints getConstraints(){
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }

    @Named("request_with_constraints")
    @Provides
    public WorkRequest getOneTimeWorkRequestWithConstraints(Constraints constraints){
        return new OneTimeWorkRequest.Builder(MyBackgroundTask.class)
                .setConstraints(constraints).build();
    }
}
