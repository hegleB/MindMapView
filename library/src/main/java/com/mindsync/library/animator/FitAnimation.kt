package com.mindsync.library.animator

import android.animation.PropertyValuesHolder
import com.mindsync.library.animator.AnimationUtils.START_PROCESS

class FitAnimation(
    private val startScaleFactor: Float,
    private val endScaleFactor: Float,
    private val endX: Float,
    private val endY: Float,
    private val update: (Float, Float, Float) -> Unit
) : AnimationStrategy {
    override fun animate() {
        val scaleXHolder =
            PropertyValuesHolder.ofFloat("scaleFactor", startScaleFactor, endScaleFactor)
        val translateXHolder = PropertyValuesHolder.ofFloat("currentPointX", endX, START_PROCESS)
        val translateYHolder = PropertyValuesHolder.ofFloat("currentPointY", endY, START_PROCESS)

        AnimationUtils.createPropertyValuesHolderAnimator(
            scaleXHolder, translateXHolder, translateYHolder
        ) { scale, currentX, currentY ->
            update(scale, currentX, currentY)
        }.start()
    }
}