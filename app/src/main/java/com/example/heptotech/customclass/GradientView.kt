package com.example.heptotech.customclass

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
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

    private var colorAnimator: ValueAnimator? = null

    init {
        background = gradientDrawable
    }

    // Start the gradient animation
    fun animateGradient(duration: Long) {
        stopAnimation() // Stop any previous animation before starting a new one

        colorAnimator = ValueAnimator.ofArgb(
            0xFF00FF00.toInt(),
            0xFF0000FF.toInt(),
            0xFFFF0000.toInt(),
            0xFFFFFF00.toInt()
        ).apply {
            this.duration = duration
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animator ->
                gradientDrawable.colors = intArrayOf(
                    animator.animatedValue as Int,
                    gradientDrawable.colors?.last() ?: 0xFF0000FF.toInt()
                )
                invalidate() // Refresh the view to apply changes
            }
            start()
        }
    }

    // Stop the gradient animation
    fun stopAnimation() {
        colorAnimator?.cancel() // Cancel the current animation if running
        colorAnimator = null
        // Reset to initial colors if desired
        gradientDrawable.colors = intArrayOf(0xFF00FF00.toInt(), 0xFF0000FF.toInt())
        invalidate()
    }
}
