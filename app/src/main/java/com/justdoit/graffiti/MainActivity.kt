package com.justdoit.graffiti

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

/**
 * Created by jinxin on 2018/7/4
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxView.clicks(ivSelectIcon1).subscribe {
            graffitiView.setPicId("1")
        }

        RxView.clicks(ivSelectIcon2).subscribe {
            graffitiView.setPicId("2")
        }

        RxView.clicks(btnSend).subscribe {
            val graffitiDrawViewHeight = graffitiView.graffitiDrawViewHeight
            val points = graffitiView.positions
            val count = points.size

            val jsonArray = JSONArray()
            val positionMap = HashMap<String, List<GraffitiPosition>>()
            var list: ArrayList<GraffitiPosition>

            for (i in 0 until points.size) {
                val position = points[i]
                jsonArray.put("${position.x},${position.y},${position.picId}")
                if (positionMap.containsKey(position.picId)) {
                    list = positionMap[position.picId] as ArrayList<GraffitiPosition>
                    list.add(position)
                } else {
                    list = ArrayList()
                    list.add(position)
                    positionMap[position.picId] = list
                }
            }

            val graffitiPositions = toList<String>(jsonArray.toString())

            if (count >= 10) {
                if (graffitiPositions != null) {
                    graffitiDisplayView?.display(getScreenWidth(this), graffitiDrawViewHeight, graffitiPositions) {
                        graffitiDisplayView?.run {
                            postDelayed({
                                visibility = View.INVISIBLE
                                Toast.makeText(this@MainActivity, "展示完成~", Toast.LENGTH_SHORT).show()
                            }, 2000)
                        }
                    }
                }
                graffitiView.resetGraffitiView()
            } else {
                Toast.makeText(this, "至少需要10个涂鸦才能发送哟~", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
