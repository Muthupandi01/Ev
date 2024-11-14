package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import com.example.heptotech.R
import kotlin.math.max
import kotlin.math.min

class FixedRangeSeekBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rangeMin = 0
    private var rangeMax = 6
    private var selectedMin = 0 // Start at "3KW" (index 0 in labels list)
    private val fixedMax = 6 // Fixed end position for end thumb (350KW)

    private val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)

    private var onRangeChangedListener: ((Int, Int) -> Unit)? = null
    private val greenColor = ContextCompat.getColor(context, R.color.greentxt)

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
        strokeWidth = 10f
    }
    private val selectedTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = greenColor
        strokeWidth = 10f
    }
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = greenColor
    }

    private var thumbRadius = 30f
    val labels = listOf("3KW", "7KW", "11KW", "22KW", "50KW", "100KW", "350KW")
    private val horizontalMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._25sdp)

    init {
        // Load saved range values from SharedPreferences
        val savedMin = sharedPreferences.getString("min", null)?.toIntOrNull() ?: 0
        val savedMax = sharedPreferences.getString("max", null)?.toIntOrNull() ?: fixedMax
        selectedMin = savedMin
        // Optional: update display if needed
        onRangeChangedListener?.invoke(selectedMin, savedMax)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val trackStartX = paddingStart + horizontalMargin.toFloat()
        val trackEndX = width - paddingEnd - horizontalMargin.toFloat()
        val trackY = thumbRadius // Place the track near the top

        // Draw full track
        canvas.drawLine(trackStartX, trackY, trackEndX, trackY, trackPaint)

        // Draw selected track
        val minX = getThumbCenterX(selectedMin)
        val maxX = getThumbCenterX(fixedMax)
        canvas.drawLine(minX, trackY, maxX, trackY, selectedTrackPaint)

        // Draw thumbs
        drawThumb(canvas, minX)
        drawThumb(canvas, maxX)

        // Draw labels under each tick
        labels.forEachIndexed { index, label ->
            val x = getThumbCenterX(index)
            drawLabel(canvas, label, x, trackY + thumbRadius * 2)
        }
    }

    private fun getThumbCenterX(position: Int): Float {
        val interval = (width - paddingStart - paddingEnd - 2 * horizontalMargin) / (rangeMax - rangeMin).toFloat()
        return paddingStart + horizontalMargin + interval * position
    }

    private fun drawThumb(canvas: Canvas, x: Float) {
        canvas.drawCircle(x, thumbRadius, thumbRadius, thumbPaint) // Align thumbs with track
    }

    private fun drawLabel(canvas: Canvas, text: String, x: Float, y: Float) {
        val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 32f
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(text, x, y + labelPaint.textSize, labelPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val position = ((x - paddingStart - horizontalMargin) / (width - paddingStart - paddingEnd - 2 * horizontalMargin) * (rangeMax - rangeMin)).toInt()
                selectedMin = min(max(rangeMin, position), fixedMax - 1)
                onRangeChangedListener?.invoke(selectedMin, fixedMax)

                // Save new min and max values to SharedPreferences
                sharedPreferences.edit().apply {
                    putString("min", labels[selectedMin])
                    putString("max", labels[fixedMax])
                    apply()
                }

                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setOnRangeChangedListener(listener: (Int, Int) -> Unit) {
        onRangeChangedListener = listener
    }

    fun setSelectedMin(position: Int) {
        selectedMin = min(max(rangeMin, position), fixedMax - 1)
        invalidate()
    }

    fun getSelectedRange(): Pair<String, String> {
        return labels[selectedMin] to labels[fixedMax]
    }
}
