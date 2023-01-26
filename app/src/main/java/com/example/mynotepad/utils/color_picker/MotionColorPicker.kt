package com.example.mynotepad.utils

import android.view.MotionEvent
import android.view.View

class MotionColorPicker : View.OnTouchListener {
    var delta = 0
    var xDelta = 0.0f
    var yDelta = 0.0f

    override fun onTouch(viewCoordinat: View, motion: MotionEvent?): Boolean {
        when (motion?.action) {

            MotionEvent.ACTION_MOVE -> {
                viewCoordinat.x = xDelta + motion.rawX
                viewCoordinat.y = yDelta + motion.rawY
            }
            MotionEvent.ACTION_DOWN -> {
                xDelta = viewCoordinat.x - motion.rawX
                yDelta = viewCoordinat.y - motion.rawY
            }

        }
        return true
    }
}
