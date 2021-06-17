package com.project.study.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

val _networkReturn: MutableLiveData<Boolean> by lazy { MutableLiveData() }
val networkBuilder = NetworkRequest.Builder()
val Context.networkCallback: () -> LiveData<Boolean>
    @RequiresApi(Build.VERSION_CODES.N)
    get() = {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerNetworkCallback(networkBuilder.build(), object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkReturn.postValue(true)
            }

            override fun onLost(network: Network) {
                _networkReturn.postValue(false)
            }
        })
        _networkReturn
    }

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

