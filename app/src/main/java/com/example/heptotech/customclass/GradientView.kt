package com.example.heptotech.customclass

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class GradientView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var gradient: LinearGradient? = null

    var colors: IntArray = intArrayOf(
        ContextCompat.getColor(context, android.R.color.holo_red_dark),
        ContextCompat.getColor(context, android.R.color.holo_orange_dark)
    ) // Default colors

    var gradientCornerRadius: Float = 10f // Default corner radius

    init {
        // Set up initial gradient
        setupGradient()
    }

    private fun setupGradient() {
        gradient = LinearGradient(
            -width.toFloat(), 0f, 0f, 0f,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = gradient
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(
            0f, 0f, width.toFloat(), height.toFloat(),
            gradientCornerRadius, gradientCornerRadius, paint
        )
    }

    fun animateGradient(duration: Long) {
        val animator = ValueAnimator.ofFloat(0f, 2f)
        animator.duration = duration
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            gradient = LinearGradient(
                -width.toFloat() + (width * animatedValue), 0f, (width * animatedValue), 0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            paint.shader = gradient
            invalidate() // Redraw the view
        }
        animator.start()
    }
}
