package com.yeungkc.gank.io.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val YEAR_PATTERN = "yyyy"
private const val MONTH_PATTERN = "MM"
private const val DAY_PATTERN = "dd"

val yearDateFormat = SimpleDateFormat(YEAR_PATTERN, Locale.getDefault())
val monthDateFormat = SimpleDateFormat(MONTH_PATTERN, Locale.getDefault())
val dayDateFormat = SimpleDateFormat(DAY_PATTERN, Locale.getDefault())

fun Date.year():String{
    return yearDateFormat.format(this)
}
fun Date.month():String{
    return monthDateFormat.format(this)
}
fun Date.day():String{
    return dayDateFormat.format(this)
}