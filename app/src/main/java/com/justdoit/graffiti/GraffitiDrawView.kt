package com.justdoit.graffiti

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

/**
 * 绘制涂鸦礼物
 * Created by jinxin on 2018/7/4
 */
class GraffitiDrawView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null

    private var lastX: Int = 0
    private var lastY: Int = 0

    private var cleared: Boolean = false

    private var positions = ArrayList<GraffitiPosition>()

    private var currentPic: String = ""

    private val maxCount: Int = 100

    var onPointsChanged: ((ArrayList<GraffitiPosition>) -> Unit)? = null

    fun clear() {
        cleared = true
        positions.clear()
        invalidate()
        onPointsChanged?.invoke(positions)
    }

    fun setPicId(pic: String) {
        currentPic = pic
        bitmap = if (pic == "1") {
            drawableToBitmap(context.resources.getDrawable(R.mipmap.icon_1))
        } else {
            drawableToBitmap(context.resources.getDrawable(R.mipmap.icon_2))
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (cleared) {
            val paint = Paint().apply {
                color = Color.TRANSPARENT
                xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            }
            canvas.apply {
                val width = width.toFloat()
                val height = height.toFloat()
                val layerId = saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG)
                drawRect(0f, 0f, width, height, paint)
                restoreToCount(layerId)
            }
            cleared = false
        } else {
            if (positions.size >= maxCount) {
                Toast.makeText(context, "涂鸦最多发送${maxCount}个，超出最大限制", Toast.LENGTH_SHORT).show()
            }

            positions.forEach {
                canvas.drawBitmap(it.bitmap, it.x.toFloat(), it.y.toFloat(), null)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x.toInt()
                val moveY = event.y.toInt()
                bitmap?.let {
                    val space = if (it.width < it.height) it.width else it.height
                    if (Math.sqrt(((moveX - lastX) * (moveX - lastX) + (moveY - lastY) * (moveY - lastY)).toDouble()) >= space) {
                        if (positions.size < maxCount) {

                            positions.add(GraffitiPosition(moveX, moveY, currentPic, it))

                            invalidate()

                            onPointsChanged?.invoke(positions)

                            lastX = moveX
                            lastY = moveY
                        }
                    }
                }
            }
        }
        return true
    }
}