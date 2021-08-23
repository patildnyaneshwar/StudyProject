package com.project.study.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.project.study.data.model.Urls

@BindingAdapter("visibleGone")
fun View.visibleGone(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["imageSrc", "placeholder"], requireAll = false)
fun ImageView.imageUrl(
    url: String?,
    placeholder: Drawable? = null
) {
    val photoUrl = url?.stringToObject<Urls>()?.regular
    Glide.with(context)
        .load(photoUrl)
        .placeholder(placeholder)
        .into(this)
}
