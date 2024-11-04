package com.example.heptotech.customclass

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator
import android.widget.FrameLayout

class GradientView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val gradientDrawable: GradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(0xFF00FF00.toInt(), 0xFF0000FF.toInt()) // Start and end colors
    ).apply {
        cornerRadius = 20f // Adjust corner radius as needed
        setStroke(1, 0xFF000000.toInt()) // Set border width and color (black in this case)
    }

    init {
        background = gradientDrawable
    }

    fun animateGradient(duration: Long) {
        val colorAnimator = ValueAnimator.ofArgb(
            0xFF00FF00.toInt(),
            0xFF0000FF.toInt(),
            0xFFFF0000.toInt(),
            0xFFFFFF00.toInt()
        )

        colorAnimator.duration = duration
        colorAnimator.repeatCount = ValueAnimator.INFINITE
        colorAnimator.addUpdateListener { animator ->
            gradientDrawable.colors = intArrayOf(
                animator.animatedValue as Int,
                gradientDrawable.colors?.last() ?: 0xFF0000FF.toInt()
            )
        }
        colorAnimator.start()
    }
}
