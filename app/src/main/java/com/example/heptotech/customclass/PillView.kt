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
        background = createRoundedDrawable(Color.LTGRAY) // Apply curved edges to the entire PillView background
    }

    // Dynamically add pills based on the number of pages
    private fun updateNumberOfPages(count: Int) {
        contentViews.forEach { removeView(it) }
        contentViews.clear()

        for (i in 0 until count) {
            val view = View(context).apply {
                layoutParams = LayoutParams(0, dpToPx(10)).apply {
                    weight = 1f
                    marginEnd = if (i < count - 1) dpToPx(8) else 0 // Spacing between pills
                }
                background = createRoundedDrawable(Color.TRANSPARENT) // Set initial background with curved edges
                this@PillView.addView(this)
            }
            contentViews.add(view)
        }

        setTints(currentPage) // Initially set the tint based on current page
    }

    // Set the black tint based on the current page index
    private fun setTints(idx: Int) {
        contentViews.forEachIndexed { index, view ->
            val isSelected = index == idx
            view.background = createRoundedDrawable(
                if (isSelected) Color.BLACK else Color.TRANSPARENT // Black for selected pill, transparent for others
            )
        }
    }

    // Create a drawable with curved edges for each pill view
    private fun createRoundedDrawable(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpToPx(5).toFloat() // Curved edges for the pill
            setColor(color)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
