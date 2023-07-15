package com.task.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyBackgroundTask extends Worker {

    private static final String TAG = "MyBackgroundTaskByWorkManager";

    public MyBackgroundTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
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
