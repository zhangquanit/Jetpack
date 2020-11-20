package com.example.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var liveData= MutableLiveData<DataEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveData.observe(this, Observer {
            println("11 收到通知: ${it.str}")
            Thread.dumpStack()
        })

        btn_send.setOnClickListener {
            //发送通知
            liveData.value=DataEntity("hellow")
        }
        btn_regist.setOnClickListener {
            //注册observer，同一个LifecycleOwner
            liveData.observe(MainActivity@this, Observer {
                println("22 收到通知: ${it.str}")
            })
        }
    }
}
