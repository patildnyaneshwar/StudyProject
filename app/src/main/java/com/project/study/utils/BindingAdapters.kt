package com.project.study.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.project.study.R
import com.project.study.data.model.Urls

@BindingAdapter("visibleGone")
fun View.visibleGone(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("imageSrc")
fun ImageView.imageUrl(url: String?) {
    val photoUrl = url?.stringToObject<Urls>()?.regular
    Glide.with(context)
        .load(photoUrl)
        .placeholder(R.drawable.loading_anim)
        .into(this)
}
