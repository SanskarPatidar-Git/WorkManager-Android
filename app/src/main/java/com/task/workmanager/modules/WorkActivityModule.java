package com.task.workmanager.modules;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkRequest;

import com.task.workmanager.MyBackgroundTask;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class WorkActivityModule {


    @Named("request_without_constraints")
    @Provides
    public WorkRequest getOneTimeWorkRequest() {
        return new OneTimeWorkRequest.Builder(MyBackgroundTask.class)
                .build();
    }


    /*
    Using constraints that the work will be only executed only when network is connected.
     */
    @Provides
    public Constraints getConstraints() {
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }

    @Named("request_with_constraints")
    @Provides
    public WorkRequest getOneTimeWorkRequestWithConstraints(Constraints constraints) {
        return new OneTimeWorkRequest.Builder(MyBackgroundTask.class)
                .setConstraints(constraints)
                //.setInitialDelay(10, TimeUnit.SECONDS)  //-> start work with some delay.
                .addTag("Request1") // -> Tags are used to identify requests uniquely. like for cancel request or need request info.
                .build();
    }


    /*
    Request Input data
    when we want some extra data to do our work we have to use setInputData() to request later the
    WorkManager will provide that data to work.
    The data should be sent in the form of key-value pair.
    setData -> as shown below we are sending the string of number as i/p .
    getData -> in doWork() we need to get that data by using getInputData().getString("Number");
     */
    @Provides
    public Data getInputData() {
        return new Data.Builder().putString("Number", "9090909090").build();
    }

    @Named("request_input_data")
    @Provides
    public WorkRequest getOneTimeWorkRequestWithPassingInput(Data data) {
        return new OneTimeWorkRequest.Builder(MyBackgroundTask.class)
                .setInputData(data)
                .build();
    }


    /*
    Backup Policy and Backup delay
    when any task is retried( in doWork() -> Result.retry()) so the work will be again executed after some delay
    based on its backup delay
    Minimum backup delay is of 10 sec (OneTimeWorkRequest.MIN_BACKOFF_MILLIS).
    and how much delay it will take ro reattempt is measured by backup policy (LINEAR or EXPONENTIAL)
    by default it is Exponential which has delay of 30 sec.
    we can customize it using setBackOffCriteria() as shown below we are using LINEAR backup policy of min 10sec.
     */
    @Named("request_backup_policy")
    @Provides
    public WorkRequest getOneTimeWorkRequestWithBackupPolicy() {
        return new OneTimeWorkRequest.Builder(MyBackgroundTask.class)
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();
    }


    /*
    Periodic WorkRequest is another type of WorkRequest like OneTimeWorkRequest.
    It is used to work in background with some intervals like we need to perform a task of fetching a data
    from server in every 2 hours either app is opened or closed.
    OneTime is used for only performing task only a single time but for interval we need PeriodicWorkRequest.
    Minimum interval time of Periodic is 15 min before 15 min it doesn't work.

    Same other parameters like constraints,backup,input data etc are also applied to periodic like one-time.
     */
    @Named("periodic_work_request")
    @Provides
    public WorkRequest getPeriodicWorkRequest() {
        return new PeriodicWorkRequest.Builder(MyBackgroundTask.class, 15, TimeUnit.MINUTES).build();
    }

}

/*
setInitialDelay()
If we need to start work for first time after some delay for one-time and periodic request.
 */


/*
Tags - addTags();
for cancel the request -> workManager.cancelAllWorkByTag("Request1");
for getting info of request -> workManager.getWorkInfosByTag("Request1");
 */