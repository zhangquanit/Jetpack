package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 *
 * @author zhangquan
 */
class MyViewModel(app: Application) : AndroidViewModel(app) {
    var liveData = MutableLiveData<String>()

    fun doRequest() {
        liveData.value = "你好"
    }

    /**
     * 当activity is finished，会调用onCleard函数,以便释放资源
     */
    override fun onCleared() {
        super.onCleared()
        Thread.dumpStack()
        println("onCleared")
    }
}