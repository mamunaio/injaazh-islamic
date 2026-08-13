package com.injaazh.islamic.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToTime(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(this)
}
