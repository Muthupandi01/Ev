package com.example.heptotech.customclass

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.heptotech.R

class BatteryViewHorizontal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    var batteryLevel: Float = 0.0f // Initial battery level
        set(value) {
            field = value.coerceIn(0f, 1f) // Clamp between 0 and 1
            invalidate() // Redraw the view when the battery level is updated
        }

    // Paint properties
    private val fillPaint = Paint().apply {
        color = Color.parseColor("#02CD8D")
        style = Paint.Style.FILL
    }
    private val backgroundPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    private val outlinePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 16f
    }
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 50f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
    }

    private val cornerRadius = 25f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val strokeWidth = outlinePaint.strokeWidth
        val halfStrokeWidth = strokeWidth / 2
        val paddingHorizontal = 20f
        val paddingVertical = 10f
        val margin = 8f

        val width = width - paddingHorizontal - strokeWidth
        val height = height - paddingVertical - strokeWidth
        val fillWidth = (width - margin * 2) * batteryLevel

        // Battery outline
        val outlineRect = RectF(
            halfStrokeWidth + paddingHorizontal / 2,
            halfStrokeWidth + paddingVertical / 2,
            width + halfStrokeWidth + paddingHorizontal / 2,
            height + halfStrokeWidth + paddingVertical / 2
        )
        canvas.drawRoundRect(outlineRect, cornerRadius, cornerRadius, outlinePaint)

        // Battery background
        canvas.drawRoundRect(outlineRect, cornerRadius, cornerRadius, backgroundPaint)

        // Battery fill
        val fillRect = RectF(
            halfStrokeWidth + paddingHorizontal / 2 + margin,
            halfStrokeWidth + paddingVertical / 2 + margin,
            fillWidth + halfStrokeWidth + paddingHorizontal / 2 + margin,
            height + halfStrokeWidth + paddingVertical / 2 - margin
        )
        canvas.drawRoundRect(fillRect, cornerRadius, cornerRadius, fillPaint)

        // Battery percentage text
        val batteryPercentage = "${(batteryLevel * 100).toInt()}%"
        val textX = (width / 2f) + paddingHorizontal / 2
        val textY = (height / 2f) + (textPaint.textSize / 3) + paddingVertical / 2
        canvas.drawText(batteryPercentage, textX, textY, textPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_BUTTON_PRESS, MotionEvent.ACTION_MOVE -> {
                // Set battery level based on touch position as a percentage of the view width
                batteryLevel = (event.x / width).coerceIn(0f, 1f)
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Optionally, finalize battery level here or trigger any additional actions
                return true
            }
            else -> {
                return super.onTouchEvent(event)
            }
        }
    }
}
