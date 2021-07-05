package com.project.study.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.concurrent.thread

private const val TAG = "WorkerTask"

class WorkerTask(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        try {
            Timber.d("doWork: ")
            Thread.sleep(5000)
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }

    override fun onStopped() {
        super.onStopped()
        Timber.e("onStopped:")
    }
}