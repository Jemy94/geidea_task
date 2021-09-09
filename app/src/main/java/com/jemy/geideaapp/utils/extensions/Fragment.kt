package com.jemy.geideaapp.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String?) {
    msg?.let {
        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toastLong(msg: String?) {
    msg?.let {
        Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
    }
}