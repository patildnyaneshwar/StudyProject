package com.project.study.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.*
import com.project.study.R
import com.project.study.databinding.FragmentJobsBinding
import com.project.study.utils.WorkerTask
import timber.log.Timber
import java.util.concurrent.TimeUnit

class JobsFragment : Fragment() {

    private lateinit var binding: FragmentJobsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!

        /*
        * When we cancel task manually(by code) onStopped() not called & Observer will trigger CANCELLED state

        KEEP:
        * If task is in RUNNING/ENQUEUED state & Again calling enqueueUniqueWork ignores new task

        REPLACE:
        * If task as returned result & in ENQUEUED state then it will replace the new task with old task
        * If task is still RUNNING state & we call enqueueUniqueWork then running task will be stopped (onStopped() called) & reinitialize task again

        * Retry will try executing doWork() on every 30 sec, 1min, 2min, etc(prev * 2) in time frame
        * */
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val workRequest = PeriodicWorkRequest.Builder(WorkerTask::class.java, 15, TimeUnit.MINUTES)
            .addTag("worker_task")
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(requireContext()).getWorkInfosByTagLiveData("worker_task")
            .observe(viewLifecycleOwner, {
                for (i in it.indices) {
                    Timber.d("worker_state: %s", it[i].state)
                }
            })

        binding.btnSyncWorker.setOnClickListener {
            WorkManager.getInstance(requireContext())
                .enqueueUniquePeriodicWork(
                    "worker_task",
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest
                )
        }

        binding.btnCancelWorker.setOnClickListener {
            WorkManager.getInstance(requireContext()).cancelAllWork()
        }
    }

}