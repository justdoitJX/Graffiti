package com.justdoit.graffiti

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.view_graffiti_gift.view.*

/**
 * 涂鸦绘制界面
 * Created by jinxin on 2018/7/4
 */
class GraffitiView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attributeSet, defStyleAttr) {

    var positions = ArrayList<GraffitiPosition>()
    private set

    val graffitiDrawViewHeight: Int
        get() = graffitiDrawView.height

    init {
        isClickable = true

        View.inflate(context, R.layout.view_graffiti_gift, this)

        RxView.clicks(tvClear).subscribe { resetGraffitiView() }

        (graffitiDrawView as GraffitiDrawView).onPointsChanged = {
            positions = it
            val size = it.size
            if (size > 0) {
                ivIcon.visibility = View.GONE
                tvTip.text = "共计${size}个涂鸦"
            }
        }
    }

    fun resetGraffitiView() {
        (graffitiDrawView as GraffitiDrawView).clear()
        ivIcon.visibility = View.VISIBLE
        tvTip.text = "至少需要涂鸦10个才能发送哟~"
    }

    fun setPicId(selectPic: String) {
        (graffitiDrawView as GraffitiDrawView).setPicId(selectPic)
    }
}