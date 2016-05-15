package com.learn.expandablelistview;

import android.app.Application;
import android.util.Log;

/**
 * Created by yongc on 16/5/15.
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("MyApplication",ex.getMessage(),ex);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
