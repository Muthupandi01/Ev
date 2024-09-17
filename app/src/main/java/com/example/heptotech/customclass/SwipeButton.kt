package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.heptotech.R
import kotlin.math.max
import kotlin.math.min

class SwipeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var thumbDrawable: Drawable = ContextCompat.getDrawable(context,
        R.drawable.combined_drawable_ev
    )!!
    private var thumbX: Float = 0f
    private var maxThumbX: Float = 0f
    private var isSwiped = false
    private val margin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp) // Margin between thumb and background
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.commontxtcolor)
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(com.intuit.sdp.R.dimen._10sdp),
            resources.displayMetrics
        )
        textAlign = Paint.Align.CENTER
        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium) // Replace with your font
    }

    init {
        // Calculate the maxThumbX value initially
        maxThumbX = width.toFloat() - thumbDrawable.intrinsicWidth - margin * 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = thumbDrawable.intrinsicHeight + margin * 2 // Account for margin in height
        setMeasuredDimension(width, height)
        maxThumbX = width - thumbDrawable.intrinsicWidth.toFloat() - margin * 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the thumb drawable at the current position with margin
        thumbDrawable.setBounds(
            thumbX.toInt() + margin,
            margin,
            (thumbX + thumbDrawable.intrinsicWidth).toInt() + margin,
            height - margin
        )
        thumbDrawable.draw(canvas)

        // Draw the text in the center of the button
        val textX = (width / 2).toFloat()
        val textY = (height / 2) - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText("Start Charging", textX, textY, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x >= thumbX && event.x <= thumbX + thumbDrawable.intrinsicWidth + margin) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val newX = event.x - thumbDrawable.intrinsicWidth / 2
                thumbX = min(max(newX, 0f), maxThumbX)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                if (thumbX < maxThumbX) {
                    thumbX = 0f
                    isSwiped = false
                } else {
                    isSwiped = true
                }
                invalidate()
            }
        }
        return true
    }

    fun reset() {
        thumbX = 0f
        invalidate()
    }

    fun isSwiped(): Boolean {
        return isSwiped
    }
}
