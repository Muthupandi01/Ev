package com.example.heptotech.customclass

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.Log
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

class CustomBarRenderer(
    chart: BarChart,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler,
    private val barWidthPx: Float // Pass the bar width in pixels
) : BarChartRenderer(chart, animator, viewPortHandler) {

    private val cornerRadius = 10f // Adjust corner radius as needed

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        val barDataSet = dataSet as BarDataSet
        val barPaint = Paint().apply {
            color = barDataSet.color
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = barDataSet.barBorderColor
            style = Paint.Style.STROKE
            strokeWidth = barDataSet.barBorderWidth
        }

        // Ensure the barBuffer is properly sized and initialized
        val barBuffer = mBarBuffers[index]
        barBuffer.setPhases(mAnimator.phaseX, mAnimator.phaseY)
        barBuffer.setBarWidth(mChart.barData.barWidth)
        barBuffer.feed(dataSet)

        val buffer = barBuffer.buffer
        val bufferSize = buffer.size

        // Make sure we don't access out of bounds
        if (bufferSize >= 4) {
            for (j in 0 until bufferSize step 4) {
                val left = buffer[j]
                val top = buffer[j + 1]
                val right = buffer[j + 2]
                val bottom = buffer[j + 3]

                val rect = RectF(left, top, right, bottom)
                val path = Path().apply {
                    addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
                }

                // Draw the rounded bar
                c.drawPath(path, barPaint)
                if (barDataSet.barBorderWidth > 0) {
                    c.drawPath(path, borderPaint)
                }
            }
        } else {
            // Log or handle the case where the buffer size is smaller than expected
            Log.e("CustomBarRenderer", "Buffer size is too small: $bufferSize")
        }
    }

}
