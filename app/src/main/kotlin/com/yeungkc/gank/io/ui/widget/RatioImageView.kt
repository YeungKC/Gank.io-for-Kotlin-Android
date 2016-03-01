package com.yeungkc.gank.io.ui.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View

/**
 * 一个能保持比例的 ImageView
 * TODO: 暂时只支持维持宽度适应高度
 *
 * @author XiNGRZ
 */
class RatioImageView : AppCompatImageView {

    private var originalWidth: Int = 0
    private var originalHeight: Int = 0

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun setOriginalSize(originalWidth: Int, originalHeight: Int) {
        this.originalWidth = originalWidth
        this.originalHeight = originalHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (originalWidth > 0 && originalHeight > 0) {
            val ratio = originalWidth.toFloat() / originalHeight.toFloat()

            val width = MeasureSpec.getSize(widthMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)

            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (width.toFloat() / ratio).toInt()
            }

            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}
