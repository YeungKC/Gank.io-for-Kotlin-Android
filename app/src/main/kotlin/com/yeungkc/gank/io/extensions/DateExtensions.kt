package com.yeungkc.gank.io.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.year():String{
    return SimpleDateFormat("yyyy").format(this)
}
fun Date.month():String{
    return SimpleDateFormat("MM").format(this)
}
fun Date.day():String{
    return SimpleDateFormat("dd").format(this)
}