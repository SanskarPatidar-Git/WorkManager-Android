package com.task.workmanager.modules;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.work.ForegroundInfo;

import com.task.workmanager.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class WorkNotificationModule {

    @Singleton
    @Provides
    public NotificationManager getNotificationManager(@ApplicationContext Context context){
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    public Notification getNotification(@ApplicationContext Context context, NotificationManager notificationManager){
        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notification = new Notification.Builder(context, "work")
                    .setSmallIcon(R.drawable.ic_send)
                    .setContentTitle("Work Manager")
                    .setContentText("My Background Task")
                    .build();

            notificationManager.createNotificationChannel(new NotificationChannel("work", "WorkManager", NotificationManager.IMPORTANCE_HIGH));

        } else {
            notification = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_send)
                    .setContentTitle("Work Manager")
                    .setContentText("My Background Task")
                    .build();
        }
        return notification;
    }

    @Provides
    public ForegroundInfo getForegroundInfoNotification(Notification notification){
        return new ForegroundInfo(10,notification);
    }

}
/*
Module for providing dependencies of Notification at Application Level component to Worker.
If Worker needs dependency we have to use hilt worker which takes only dependencies provided by Application component.

for using the notification to Work Manager we need to define service in AndroidManifest.xml

 */