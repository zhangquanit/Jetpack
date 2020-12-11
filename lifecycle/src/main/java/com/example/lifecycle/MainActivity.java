package com.example.lifecycle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/**
 * <pre>
 * --Activity
 *   --androidx.core.app.ComponentActivity implements LifecycleOwner
 *      --androidx.activity.ComponentActivity implements LifecycleOwner,ViewModelStoreOwner
 *          --FragmentActivity
 *             --AppComponentActivity
 *                 --MainActivity
 * </pre>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 生命周期监听，实现LifecycleObserver 通过注解回调
         */
        getLifecycle().addObserver(new MyLifecycleObserver());

        /**
         * 生命周期监听 LifecycleEventObserver
         */
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source,
                                       @NonNull Lifecycle.Event event) {
                System.out.println("[LifecycleEventObserver]  event=" + event);
            }
        });

        backPressedDispatchTest();
    }


    //--------------------------------------------------------
    /**
     * 返回按键处理
     * <pre>
     *     Activity onKeyDown ->KEYCODE_BACK
     *        --Activity onBackPressed
     *           --OnBackPressedCallback handleOnBackPressed
     *               --super.onBackPressed
     * </pre>
     */
    private void backPressedDispatchTest() {
        //注册返回事件处理，onDestory自动注销. 拿到Activity对象就可以注册返回事件，避免重写Activity的onBackPressed或onKeyDown。
        getOnBackPressedDispatcher().addCallback(this,new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                System.out.println("OnBackPressedCallback handleOnBackPressed");
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.out.println("Activity onBackPressed");
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("Activity onKeyDown ->KEYCODE_BACK");
        }
        return super.onKeyDown(keyCode, event);
    }
}