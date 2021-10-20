package com.example.myapplication.todaysmovie;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;
    public static AppExecutors getInstance(){
        if (instance==null)
        {
            instance = new AppExecutors();
        }
        return instance;
    }
    private final ScheduledExecutorService mNetworkIo = Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService networkIo(){
        return mNetworkIo;
    }

}
