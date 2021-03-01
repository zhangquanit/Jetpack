package com.example.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_twoactivity.setOnClickListener {
            startActivity(Intent(this, TwoActivity::class.java))
        }

        //activity横竖屏切换，viewModel实例不变
        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        println("MainActivity liveData.hasObservers=${viewModel.liveData.hasObservers()}")
        viewModel.liveData.removeObservers(this) //先注销之前添加的observer
        viewModel.liveData.observe(this, Observer<String> {
            println("MainActivity observe data=$it")
        })
        println("MainActivity act=$this")
        println("MainActivity viewModel=$viewModel")

        //发送请求
        viewModel.doRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MainActivity onDestroy,act=$this")
    }

    override fun finish() {
        super.finish()
        println("MainActivity finish,act=$this")
    }
}