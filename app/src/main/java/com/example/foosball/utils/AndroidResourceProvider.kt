package com.example.foosball.utils

import android.content.Context
import javax.inject.Inject

class AndroidResourceProvider @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun string(id: Int): String = context.getString(id)
    override fun string(id: Int, vararg params: Any): String =
        context.getString(id, params)

}
