package com.architecturestudy.common

import android.support.v4.app.Fragment
import android.widget.Toast

abstract class BaseFragment : Fragment() {
    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}