package com.example.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

/**
 *
 * @author zhangquan
 */
class TwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        println("TwoActivity liveData.hasObservers=${viewModel.liveData.hasObservers()}")
        viewModel.liveData.observe(this, Observer<String> {
            println("TwoActivity observe data=$it")
        })
        println("TwoActivity act=$this")
        println("TwoActivity viewModel=$viewModel")

        //发送请求
        viewModel.doRequest()
    }
}