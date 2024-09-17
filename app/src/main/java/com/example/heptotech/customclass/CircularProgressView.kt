package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.heptotech.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0f
    private val segmentCount = 40

    // Use sdp for segmentWidth, segmentGap, and borderWidth
    private val segmentWidth = resources.getDimension(com.intuit.sdp.R.dimen._5sdp)
    private val segmentGap = resources.getDimension(com.intuit.sdp.R.dimen._3sdp)
    private val borderWidth = resources.getDimension(com.intuit.sdp.R.dimen._2sdp)

    private val progressColorGreen = Color.parseColor("#02CD8D")
    private val progressColorBlue = Color.parseColor("#0897FF")
    private val circleColor = Color.parseColor("#F0F0F0")
    private val textColorGreen = Color.parseColor("#02CD8D")
    private val textColorBlue = Color.parseColor("#0897FF")
    private val textColorDefault = Color.parseColor("#F0F0F0")

    private val paddingBetweenCircles = resources.getDimension(com.intuit.sdp.R.dimen._20sdp)

    // Paint for the outer circle (grey)
    private val paintCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = borderWidth
        color = circleColor
    }

    // Paint for the progress segments
    private val paintProgress = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = borderWidth
    }

    // Paint for the progress text
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = resources.getDimension(com.intuit.ssp.R.dimen._14ssp)
        textAlign = Paint.Align.CENTER
        // Use the bold version of Poppins font
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    // Paint for the inner circle shadow (light grey with shadow for elevation)
    private val paintInnerCircleShadow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = borderWidth
        color = circleColor
        setShadowLayer(0.2f, 0f, 0f, Color.parseColor("#40000000"))
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, paintInnerCircleShadow)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val diameter = min(width, height).toFloat()

        // Calculate radii with additional gaps
        val outerRadius = diameter / 2 - borderWidth - paddingBetweenCircles / 2
        val innerRadius = outerRadius - segmentWidth - paddingBetweenCircles

        // Draw outer circle (grey)
        canvas.drawCircle(width / 2f, height / 2f, outerRadius, paintCircle)

        // Determine the color of the progress segments based on the progress value
        val currentProgressColor = if (progress >= 100f) progressColorBlue else progressColorGreen
        paintProgress.color = if (progress.toInt() == 0) circleColor else currentProgressColor

        // Calculate the length of the segment to ensure it doesn't touch the circles
        val segmentLength = (outerRadius - innerRadius) / 2f - segmentGap / 2f

        // Draw progress segments
        for (i in 0 until segmentCount) {
            val angle = 360f / segmentCount * i - 90f
            val isProgress = i < segmentCount * progress / 100

            // Calculate start and end points for the segment
            val startX = width / 2 + cos(Math.toRadians(angle.toDouble())).toFloat() * (innerRadius + segmentWidth + segmentGap / 2)
            val startY = height / 2 + sin(Math.toRadians(angle.toDouble())).toFloat() * (innerRadius + segmentWidth + segmentGap / 2)
            val endX = width / 2 + cos(Math.toRadians(angle.toDouble())).toFloat() * (innerRadius + segmentWidth + segmentGap / 2 + segmentLength)
            val endY = height / 2 + sin(Math.toRadians(angle.toDouble())).toFloat() * (innerRadius + segmentWidth + segmentGap / 2 + segmentLength)

            canvas.drawLine(startX, startY, endX, endY, if (isProgress) paintProgress else paintCircle)
        }

        // Draw inner circle with subtle shadow (elevation)
        canvas.drawCircle(width / 2f, height / 2f, innerRadius, paintInnerCircleShadow)

        // Draw progress text
        paintText.color = when {
            progress >= 100f -> textColorBlue
            progress.toInt() == 0 -> textColorDefault
            else -> textColorGreen
        }
        val textX = width / 2f
        val textY = (height / 2f) - ((paintText.descent() + paintText.ascent()) / 2)
        canvas.drawText("${progress.toInt()}%", textX, textY, paintText)
    }

    fun setProgress(value: Float) {
        progress = value
        invalidate()
    }
}
