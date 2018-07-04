package com.justdoit.graffiti

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 展示涂鸦礼物
 * Created by jinxin on 2018/7/4
 */
class GraffitiDisplayView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr) {

    private var toDrawGraffitiPositions: ArrayList<GraffitiPosition> = ArrayList()

    private val bitmapCache = HashMap<String, Bitmap>()

    fun display(originWidth: Int, originHeight: Int, path: List<String>, finishCallback: (() -> Unit)? = null) {
        visibility = View.VISIBLE
        val pointCount = path.size.toLong()
        Observable
                .intervalRange(0, pointCount + 1, 0, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it == pointCount) {
                        toDrawGraffitiPositions.clear()
                        finishCallback?.invoke()
                    } else {
                        val ratio = width / originWidth.toFloat()
                        val pointStr = path[it.toInt()]
                        val values = pointStr.split(",")
                        val x = values[0].toInt().times(ratio).toInt()
                        val y = values[1].toInt().times(ratio).toInt()
                        val picId = values[2]

                        var bitmap = bitmapCache[picId]
                        if (bitmap == null) {
                            bitmap = if (picId == "1") {
                                drawableToBitmap(context.resources.getDrawable(R.mipmap.icon_1))
                            }else{
                                drawableToBitmap(context.resources.getDrawable(R.mipmap.icon_2))
                            }
                            bitmapCache[picId] = bitmap
                        }
                        toDrawGraffitiPositions.add(GraffitiPosition(x, y, picId, bitmap))
                        postInvalidate()
                    }
                }
    }

    override fun onDraw(canvas: Canvas) {
        toDrawGraffitiPositions.apply {
            for (i in 0 until size) {
                this[i].apply {
                    canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), null)
                }
            }
        }
    }

    //清除缓存
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        toDrawGraffitiPositions.clear()
        bitmapCache.keys.forEach {
            bitmapCache[it]?.run {
                if (!isRecycled) {
                    recycle()
                }
            }
        }
        bitmapCache.clear()
    }
}