package com.example.foosball.utils

import androidx.annotation.StringRes

/**
 * Can be used for getting resources
 */
interface ResourceProvider {
    fun string(@StringRes id: Int): String
    fun string(@StringRes id: Int, vararg params : Any): String
}
