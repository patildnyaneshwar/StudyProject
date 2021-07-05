package com.project.study.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.study.R
import com.project.study.databinding.ActivityBaseBinding
import com.project.study.utils.networkCallback
import com.project.study.utils.showToast
import timber.log.Timber
import timber.log.Timber.DebugTree


private const val TAG = "BaseActivity"

open class BaseActivity : AppCompatActivity() {

    lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        Timber.plant(DebugTree())

        networkCallback().observe(this, {
            Log.d(TAG, "onCreate:networkCallback $it")
            if (it) {
                showToast(getString(R.string.back_online))
            } else {
                showToast(getString(R.string.no_internet))
            }
        })

    }


}