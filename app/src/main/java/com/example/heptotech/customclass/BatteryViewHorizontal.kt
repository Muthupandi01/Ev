package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.heptotech.R // Make sure this points to your app's R class

class BatteryViewHorizontal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    var batteryLevel: Float = 0.75f
        set(value) {
            field = value
            invalidate() // Redraw the view when the battery level is updated
        }

    // Green battery fill color
    private val fillPaint = Paint().apply {
        color = Color.parseColor("#02CD8D")
        style = Paint.Style.FILL
    }

    // Background color (inside the battery rectangle)
    private val backgroundPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    // Outer black stroke with a stroke width of 12f
    private val outlinePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 16f // Updated stroke width
    }

    // Set up text paint with Poppins Bold font
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 50f // Adjust text size as needed
        textAlign = Paint.Align.CENTER // Center the text horizontally
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium) // Load Poppins Bold font
    }

    // Increased corner radius for more rounded edges
    private val cornerRadius = 25f // Updated for more rounded corners

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate the half stroke width for offsetting the RectF
        val strokeWidth = outlinePaint.strokeWidth
        val halfStrokeWidth = strokeWidth / 2

        // Calculate dimensions considering padding and stroke width
        val paddingHorizontal = 20f
        val paddingVertical = 10f
        val margin = 8f // Margin for green battery fill progress

        val width = width - paddingHorizontal - strokeWidth // Subtract stroke width from total width
        val height = height - paddingVertical - strokeWidth // Subtract stroke width from total height

        val fillWidth = (width - margin * 2) * batteryLevel // Apply margin to the green fill width

        // Draw battery outline (outer black stroke), accounting for stroke width on all sides
        val outlineRect = RectF(
            halfStrokeWidth + paddingHorizontal / 2, // Left
            halfStrokeWidth + paddingVertical / 2,  // Top
            width + halfStrokeWidth + paddingHorizontal / 2,  // Right
            height + halfStrokeWidth + paddingVertical / 2  // Bottom
        )
        canvas.drawRoundRect(outlineRect, cornerRadius, cornerRadius, outlinePaint)

        // Draw battery background (white background inside the outline)
        canvas.drawRoundRect(outlineRect, cornerRadius, cornerRadius, backgroundPaint)

        // Draw battery fill (green progress) with margins, inside the background
        val fillRect = RectF(
            halfStrokeWidth + paddingHorizontal / 2 + margin, // Left
            halfStrokeWidth + paddingVertical / 2 + margin, // Top
            fillWidth + halfStrokeWidth + paddingHorizontal / 2 + margin, // Right
            height + halfStrokeWidth + paddingVertical / 2 - margin // Bottom
        )
        canvas.drawRoundRect(fillRect, cornerRadius, cornerRadius, fillPaint)

        // Draw battery percentage text in the center of the view
        val batteryPercentage = "${(batteryLevel * 100).toInt()}%"
        val textX = (width / 2f) + paddingHorizontal / 2 // Center horizontally
        val textY = (height / 2f) + (textPaint.textSize / 3) + paddingVertical / 2 // Center vertically
        canvas.drawText(batteryPercentage, textX, textY, textPaint)
    }
}
