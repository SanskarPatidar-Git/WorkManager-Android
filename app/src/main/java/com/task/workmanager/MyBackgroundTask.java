package com.task.workmanager;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.ForegroundInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;


@HiltWorker
public class MyBackgroundTask extends Worker {

    private static final String TAG = "MyBackgroundTaskByWorkManager";

    private ForegroundInfo foregroundInfoForNotification; // to show notification for occuring event in background.

    @AssistedInject
    public MyBackgroundTask(@Assisted @NonNull Context context,@Assisted @NonNull WorkerParameters workerParams , ForegroundInfo foregroundInfoForNotification) {
        super(context, workerParams);
        this.foregroundInfoForNotification = foregroundInfoForNotification;
    }

    /*
    doWork()
    This method is executed in background. The specific task which we want to perform in background can be written
    in this method. ex like network request , upload data to server etc any type of task.

    In this method we are just simple performing log operation which will be executed in background also.
     */
    @NonNull
    @Override
    public Result doWork() {

       setForegroundAsync(foregroundInfoForNotification); //Display notification of task

        /* This is for only thread sleep for 5 sec for notification exist because doWork() will execute so fast
           which we cant see notification for some time.

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
         */

        String imageUriInput = getInputData().getString("Number"); //get the data which was passed to WorkRequest as shown in WorkActivityModule.
        Log.i(TAG, "doWork: Number as input data -> "+imageUriInput);

        Log.i(TAG, "doWork: "+" Executing........");
        return Result.success(); //According to your output you can return the result.
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "onStopped: Work manager is stopped");
    }
}
/*
Notification by Work Manager-
Notification are shown by workManager itself for notify the user about executing some event in background.
it is shown when task is started and automatically removed when task is over.

for show notification we need to call the method setForegroundAsync() in doWork() of Worker class which shows
notification in foreground mode. The method need ForegroundInfo object which takes notification id and notification.
we are getting notification object by hilt from ApplicationComponent look in WorkNotificationModule class.


@HiltWorker-
If we need to pass the dependency to Worker from DI like hilt we need to specify the class with @HiltWorker.
@AssistedInject and @Assisted are used to tell the hilt we need to pass this dependency at runtime.
and the extra dependency we need in this class can be defined in its constructor.

NOTE - HiltWorker will get dependency only from ApplicationComponent -> SingletonComponent
In our case we need notification object from hilt which is defined in WorkNotificationModule.

also we need to change the configuration of default work manager which is mentioned in MyApplication as
HiltWorkerFactory.
If we are using any dependency in worker through hilt we have to do it and also setup provider in Manifest file.
While we are using notification we need to define service also in Manifest.

We need to also define dependency in gradle for HiltWorker.
 */