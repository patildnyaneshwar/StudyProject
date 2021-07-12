package com.project.study.ui

import android.os.Bundle
import com.project.study.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
    }
}