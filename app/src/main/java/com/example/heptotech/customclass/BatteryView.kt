package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.heptotech.R

class BatteryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val fillPaint = Paint().apply {
        color = Color.parseColor("#02CD8D")
        style = Paint.Style.FILL
    }

    private val outlinePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 7.5f // Outline thickness
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 50f // Adjust text size as needed
        textAlign = Paint.Align.CENTER
        // Set Poppins font
        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
    }

    var batteryLevel: Float = 0.75f
        set(value) {
            field = value
            invalidate() // Request a redraw
        }

    private val margin = 10f // Margin inside the battery fill

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate dimensions
        val batteryWidth = width - 20
        val batteryHeight = height - 20
        val fillMargin = margin * 2 // Margin for the fill
        val adjustedWidth = batteryWidth - fillMargin
        val adjustedHeight = batteryHeight - fillMargin
        val fillHeight = adjustedHeight * batteryLevel
        val fillTop = 10 + batteryHeight - fillHeight - margin

        // Draw battery outline
        canvas.drawRoundRect(
            10f,
            10f,
            (width - 10).toFloat(),
            (height - 10).toFloat(),
            20f,
            20f,
            outlinePaint
        )

        // Draw battery fill with margin
        canvas.drawRoundRect(
            10 + margin,
            fillTop.toFloat() + margin,
            (width - 10 - margin).toFloat(),
            (fillTop + fillHeight).toFloat() - margin,
            20f,
            20f,
            fillPaint
        )

        // Draw battery percentage text
        val percentageText = "${(batteryLevel * 100).toInt()}%"
        canvas.drawText(
            percentageText,
            (width / 2).toFloat(),
            (height / 2 + textPaint.textSize / 2).toFloat(),
            textPaint
        )
    }
}