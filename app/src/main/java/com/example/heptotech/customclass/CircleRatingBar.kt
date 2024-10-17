package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.heptotech.R

class CircleRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var rating = 0f // Rating can be a float (e.g., 4.5)
    private val totalCircles = 5 // Set total circles to 5
    private val circleRadius = 25f // Circle radius
    private val circleSpacing = 20f // Spacing between circles

    // Get the green color from resources
    private val greenColor = ContextCompat.getColor(context, R.color.greentxt)

    // Paint for full circles (filled)
    private val fullCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = greenColor
        style = Paint.Style.FILL
    }

    // Paint for empty circles (unfilled or outer boundary for half-filled)
    private val emptyCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = greenColor
        style = Paint.Style.STROKE
        strokeWidth = 0.5f
    }

    // Set the rating value
    fun setRating(rating: Float) {
        this.rating = rating
        invalidate() // Redraw the view when the rating changes
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Loop through the total number of circles
        for (i in 0 until totalCircles) {
            val cx = (i * (circleRadius * 2 + circleSpacing)) + circleRadius
            val cy = height / 2f

            // Draw full circle if rating is greater than or equal to this circle index
            if (rating >= i + 1) {
                canvas.drawCircle(cx, cy, circleRadius, fullCirclePaint)
            }
            // Draw half-filled circle if the rating is between the current index and next
            else if (rating > i && rating < i + 1) {
                // Draw empty circle outline first
                canvas.drawCircle(cx, cy, circleRadius, emptyCirclePaint)

                // Create a rectangle for the half-filled circle
                val rectF = RectF(cx - circleRadius, cy - circleRadius, cx + circleRadius, cy + circleRadius)
                // Draw left vertical half-filled arc (0 degrees at the top, 90 degrees to the left)
                canvas.drawArc(rectF, 90f, 180f, true, fullCirclePaint) // Left half arc
            }
            // Draw empty circle for remaining circles
            else {
                canvas.drawCircle(cx, cy, circleRadius, emptyCirclePaint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = (totalCircles * (circleRadius * 2 + circleSpacing)).toInt()
        val desiredHeight = (circleRadius * 2).toInt()

        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }
}
