package com.mindsync.library.animator

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

object AnimationUtils {
    const val ANIMATION_DURATION = 300L
    const val START_PROCESS = 0f
    const val END_PROCESS = 1f

    fun createObjectAnimator(
        start: Float,
        end: Float,
        duration: Long = ANIMATION_DURATION,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        onUpdate: (Float) -> Unit
    ) = ObjectAnimator.ofFloat(start, end).apply {
        this.duration = duration
        this.interpolator = interpolator
        addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            onUpdate(animatedValue)
        }
    }

    fun createPropertyValuesHolderAnimator(
        vararg holderValue: PropertyValuesHolder,
        duration: Long = ANIMATION_DURATION,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        onUpdate: (scale: Float, currentX: Float, currentY: Float) -> Unit
    ) = ValueAnimator.ofPropertyValuesHolder(*holderValue)
        .apply {
            this.duration = duration
            this.interpolator = interpolator
            addUpdateListener { animation ->
                val scale = animation.getAnimatedValue("scaleFactor") as Float
                val x = animation.getAnimatedValue("currentPointX") as Float
                val y = animation.getAnimatedValue("currentPointY") as Float
                onUpdate(scale, x, y)
            }
        }
}