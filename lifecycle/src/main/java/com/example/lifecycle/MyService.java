package com.example.lifecycle;

import androidx.lifecycle.LifecycleService;

/**
 * @author zhangquan
 */
public class MyService extends LifecycleService {
    public MyService() {
        getLifecycle().addObserver(new MyServiceObserver());
    }

}
