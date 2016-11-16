package com.yeungkc.gank.io.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class RatioFrameLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: FrameLayout(context, attrs, defStyleAttr) {

    private var originalWidth: Int = 0
    private var originalHeight: Int = 0

    fun setOriginalSize(originalWidth: Int, originalHeight: Int) {
        this.originalWidth = originalWidth
        this.originalHeight = originalHeight
    }

    @Suppress("NAME_SHADOWING")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec

        if (originalWidth > 0 && originalHeight > 0) {
            val ratio = originalWidth.toFloat() / originalHeight.toFloat()

            val width = MeasureSpec.getSize(widthMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)

            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (width.toFloat() / ratio).toInt()
            }

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}

