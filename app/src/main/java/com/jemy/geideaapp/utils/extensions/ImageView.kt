package com.jemy.geideaapp.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jemy.geideaapp.R

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}