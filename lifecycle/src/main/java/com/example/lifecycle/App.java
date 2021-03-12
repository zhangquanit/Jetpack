package com.example.lifecycle;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * @author zhangquan
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppObserver());
    }
}
