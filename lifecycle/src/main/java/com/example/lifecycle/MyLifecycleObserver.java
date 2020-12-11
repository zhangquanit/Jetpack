package com.example.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 生命周期监听
 * 实现LifecycleObserver  注解回调
 * @author zhangquan
 */
public class MyLifecycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        System.out.println("LifecycleObserver onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        System.out.println("LifecycleObserver onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        System.out.println("LifecycleObserver onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        System.out.println("LifecycleObserver onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        System.out.println("LifecycleObserver onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        System.out.println("LifecycleObserver onDestroy");
    }

    //任意回调都会调用它，比如调用完onCreate()后会回调这里的onCreate(),然后会回调onAny();
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny() {
//        System.out.println("LifecycleObserver onAny");

    }
}
