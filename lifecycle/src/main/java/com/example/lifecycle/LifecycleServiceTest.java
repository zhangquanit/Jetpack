package com.example.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangquan
 */
public class LifecycleServiceTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lifecycle_service_test);
        findViewById(R.id.btn_startservice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(LifecycleServiceTest.this,MyService.class));
            }
        });

        findViewById(R.id.btn_stopservice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(LifecycleServiceTest.this,MyService.class));
            }
        });

    }
}
