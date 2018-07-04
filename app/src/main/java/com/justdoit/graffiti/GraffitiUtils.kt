package com.justdoit.graffiti

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by jinxin on 2018/7/4
 */

fun drawableToBitmap(drawable: Drawable): Bitmap {
    // 取 drawable 的长宽
    val w = drawable.intrinsicWidth
    val h = drawable.intrinsicHeight

    // 取 drawable 的颜色格式
    val config = if (drawable.opacity != PixelFormat.OPAQUE)
        Bitmap.Config.ARGB_8888
    else
        Bitmap.Config.RGB_565
    // 建立对应 bitmap
    val bitmap = Bitmap.createBitmap(w, h, config)
    // 建立对应 bitmap 的画布
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, w, h)
    // 把 drawable 内容画到画布中
    drawable.draw(canvas)
    return bitmap
}

fun <T> toList(JSONArrayStr: String?): List<T>? {
    if (TextUtils.isEmpty(JSONArrayStr)) {
        return null
    }
    return try {
        val type = object : TypeToken<List<T>>() {}.type
        Gson().fromJson<Any>(JSONArrayStr, type) as List<T>?
    } catch (e: Exception) {
        null
    }
}

fun getScreenWidth(context: Context): Int {
    val metric = DisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(metric)
    return metric.widthPixels
}
