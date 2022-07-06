package com.example.topaz.Activities

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.topaz.R

class IndicatorLayout : LinearLayout {
    private var indicatorCount: Int = 0
    private var selectedPosition: Int = 0
    private var selectedColor: Int= 0
    private var unselectedColor: Int=0
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        initIndicators(context, attrs, 0)
    }
    constructor(
        context: Context, attrs: AttributeSet, defStyleAttr: Int
    ): super(context, attrs, defStyleAttr) {
        initIndicators(context, attrs, defStyleAttr)
    }
    private fun initIndicators(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.IndicatorLayout, defStyleAttr, 0)
        try {
            indicatorCount = typedArray.getInt(R.styleable.IndicatorLayout_indicatorCount, 0)
            selectedColor= typedArray.getResourceId(R.styleable.IndicatorLayout_selectedColor,R.drawable.indicator_selected)
            unselectedColor = typedArray.getResourceId(R.styleable.IndicatorLayout_unselectedColor, R.drawable.indicator_unselected)

        } finally {
            typedArray.recycle()
        }
        updateIndicators()
    }
    private fun px(dpValue: Float): Int {
        return (dpValue * context.resources.displayMetrics.density).toInt()
    }
    private fun updateIndicators() {
        try {
            removeAllViews()
            for (i in 0 until indicatorCount) {
                val indicator = View(context)
                // setting indicator layout margin
                val layoutParams = LayoutParams(px(10f), px(10f))
                layoutParams.setMargins(px(3f), px(3f), px(3f), px(3f))
                indicator.layoutParams = layoutParams
                indicator.setBackgroundResource(unselectedColor)
                // add the view to indicator layout
                addView(indicator)
            }
        }
        catch (e:Exception){
            e.toString()
        }
    }
    fun setIndicatorCount(count: Int) {
        indicatorCount = count
        updateIndicators()
    }
    fun selectCurrentPosition(position: Int) {
        try {
            if (position >= 0 && position <= indicatorCount) {
                selectedPosition = position
                for (index in 0 until indicatorCount) {
                    val indicator = getChildAt(index)
                    if (index == selectedPosition) {
                        indicator.setBackgroundResource(selectedColor)
                    } else {
                        indicator.setBackgroundResource(unselectedColor)
                    }
                }
            }
        }
        catch (e:Exception){
            e.toString()
        }
    }
}
