package com.example.heptotech.customclass

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class PillView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var pageCount: Int = 0
        set(value) {
            field = value
            updateNumberOfPages(value)
        }

    var currentPage: Int = 0
        set(value) {
            field = value
            setTints(value)
        }

    private val contentViews = mutableListOf<View>()

    init {
        orientation = HORIZONTAL
        background = createRoundedDrawable(Color.LTGRAY) // Apply curved edges to the entire PillView
    }

    private fun updateNumberOfPages(count: Int) {
        contentViews.forEach { removeView(it) }
        contentViews.clear()

        for (i in 0 until count) {
            val view = View(context).apply {
                layoutParams = LayoutParams(0, dpToPx(10)).apply {
                    weight = 1f
                    marginEnd = if (i < count - 1) dpToPx(0) else 0
                }
                setBackgroundColor(Color.TRANSPARENT) // Make inner views transparent
                this@PillView.addView(this)
            }
            contentViews.add(view)
        }

        setTints(currentPage)
    }

    private fun setTints(idx: Int) {
        contentViews.forEachIndexed { index, view ->
            view.setBackgroundColor(
                if (index == idx) Color.BLACK else Color.TRANSPARENT
            )
        }
    }

    private fun createRoundedDrawable(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpToPx(5).toFloat()
            setColor(color)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
