package com.task.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

     @Named("request_without_constraints")
     @Inject
     WorkRequest workRequestWithoutConstraints;

     @Named("request_with_constraints")
     @Inject
     WorkRequest workRequestWithConstraints;

     @Inject
     WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Executing task without constraints
        workManager.enqueue(workRequestWithoutConstraints);

        //With constraints
        workManager.enqueue(workRequestWithConstraints);
    }
}

/*
Worker-
A task which we want to execute in background is to be written in doWork() method of Worker class.
So we need to extend worker class to override doWork().
In our app MyBackgroundTask class extend Worker and override doWork() and the task written in doWork() will
execute according to our WorkerRequest.
 */


/*
WorkRequest-
We need to tell the WorkManager that which task is to be performed in background and how.
Like our request is one-time or periodic , is task has any constraint etc.

1. One-time -> The task should be executed only on time when app opens.
2. Periodic -> The task should be executed in intervals like a task which should be executed in every 1 hour
               either the app is opened or exit.
WorkManager will execute according to WorkRequest.
 */


/*
WorkManager-
All this tasks are handled by WorkManager. It takes the request and according to its parameter like one-time
or any constraints etc according to it performs action.
WorkManager -> WorkRequest -> Worker
 */


/*
Constraints-
Constraints are conditions which can be applicable for performing tasks with some conditions.
Ex - we want to run background task only when network is available so we want to use constraints for network
     setRequiredNetworkType(NetworkType.CONNECTED) in constraint object and then use this constraint object
     in WorkerRequest.
     Now the task will be executed only when network is available
Same other constraints are also for low battery , low storage etc.
We are creating constraints object in WorkActivityModule are passing this Constraint to WorkerRequest.
 */