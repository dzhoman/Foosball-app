package com.example.foosball.utils

import android.view.View

const val DEFAULT_THROTTLE_DURATION_IN_MILLIS = 300L

inline fun <V : View> V.onClick(
    throttleDuration: Long = DEFAULT_THROTTLE_DURATION_IN_MILLIS,
    crossinline listener: () -> Unit
): V {
    setOnClickListener(SafeClickListener(throttleDuration) { listener.invoke() })
    return this
}

class SafeClickListener(
    throttleDuration: Long,
    private val listener: View.OnClickListener,
) : View.OnClickListener {
    private val doubleClickPreventer = DoubleClickPreventer(throttleDuration)

    override fun onClick(v: View) {
        if (doubleClickPreventer.prevent()) {
            return
        }
        listener.onClick(v)
    }
}

private class DoubleClickPreventer(
    private val throttleDuration: Long
) {
    private var lastClickTime = 0L

    fun prevent(): Boolean {
        val currentTime = System.currentTimeMillis()
        val spentTime = currentTime - lastClickTime
        if (spentTime in 1 until throttleDuration) {
            return true
        }
        lastClickTime = currentTime
        return false
    }
}
