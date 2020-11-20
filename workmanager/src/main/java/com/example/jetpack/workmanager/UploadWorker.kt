package com.example.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    var parameters = workerParams
    override fun doWork(): Result {
        println("MyWorker....doWork..thread=" + Thread.currentThread().name)

        val inputData = parameters.inputData
        println("MyWorker..inputData=$inputData")


        /**
         * Result.success()：工作成功完成。
         * Result.failure()：工作失败。
         * Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
         */
        return Result.success()
    }
}