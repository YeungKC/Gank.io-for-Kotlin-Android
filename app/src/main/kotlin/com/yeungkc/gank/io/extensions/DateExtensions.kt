package com.yeungkc.gank.io.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by YeungKC on 16/3/1.
 *
 * @项目名: kc
 * @包名: gank.io.kc.extensions
 * @作者: YeungKC
 *
 * @描述：TODO
 */
fun Date.year():String{
    return SimpleDateFormat("yyyy").format(this)
}
fun Date.month():String{
    return SimpleDateFormat("MM").format(this)
}
fun Date.day():String{
    return SimpleDateFormat("dd").format(this)
}

