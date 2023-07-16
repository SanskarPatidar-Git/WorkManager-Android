package com.task.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.Arrays;

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

    @Named("request_backup_policy")
    @Inject
    WorkRequest workRequestWithBackupPolicy;

    @Named("request_input_data")
    @Inject
    WorkRequest workRequestWithInputData;

    @Named("periodic_work_request")
    @Inject
    WorkRequest workRequestPeriodic;


     @Inject
     WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Executing task without constraints
        workManager.enqueue(workRequestWithoutConstraints);

        //Request With constraints
        workManager.enqueue(workRequestWithConstraints);

        //Request With Backup policy and backup delay
        workManager.enqueue(workRequestWithBackupPolicy);

        //Request With input data
        workManager.enqueue(workRequestWithInputData);


        System.out.println("=============== BEGIN WITH ================== ");

        /*
        Request Chaining-
        If u need that your work is scheduled one after one with some sequence we will use some methods of
        WorkManager which process work in a sequence which is called chain.
        beginWith() this method is used to declare that from which request does work start and
        then() is used for after its previous which request should be processed.

        In this module you can observe it by creating more WorkerRequests or comment previous lines
        where workManager enqueues the requests.
         */

                    //workManager.beginWith((OneTimeWorkRequest) workRequestWithInputData)
                            //.then((OneTimeWorkRequest) workRequestWithBackupPolicy)
                           //.then((OneTimeWorkRequest) workRequestWithoutConstraints).enqueue();



        /*
        If you want that some task can be performed parallel we use List of WorkRequest passing to beginWith()
        Here , inputData and Policy WorkRequest work parallel and then withoutConstraint will work.

        NOTE - If either one of previous request is failed to rest of the Request will be not executed.

        Comment above enqueues and unComment below
         */


                   //workManager.beginWith(Arrays.asList((OneTimeWorkRequest)workRequestWithInputData,(OneTimeWorkRequest)workRequestWithBackupPolicy))
                             //.then((OneTimeWorkRequest) workRequestWithoutConstraints).enqueue();



        System.out.println("================ PERIODIC WORK REQUEST =============== ");

        //Periodic work request
        workManager.enqueue(workRequestPeriodic);

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

1. One-time -> The task should be executed only on time when app opens or when any constraints met.
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