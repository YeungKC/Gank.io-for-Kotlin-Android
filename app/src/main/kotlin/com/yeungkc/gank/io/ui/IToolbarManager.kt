package com.yeungkc.gank.io.ui

import android.animation.Animator
import android.os.Bundle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.yeungkc.gank.io.utils.AnimUtils


interface IToolbarManager {
    var lastTime: Long

    fun enableDoubleClickToEvent(event: () -> Unit) {
        getToolBar().setOnClickListener {
            val nowTime = System.currentTimeMillis()
            if ((nowTime - lastTime) < 2000) event()
            lastTime = nowTime
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        getToolBar().navigationIcon = createUpDrawable()
        getToolBar().setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = DrawerArrowDrawable(getToolBar().context).apply { progress = 1f }

    fun showToolBar() {
        startToolBarAnimate(0f)
    }

    fun hideToolBar() {
        val height = getToolBar().height
        startToolBarAnimate((-height).toFloat())
    }

    private fun startToolBarAnimate(fl: Float) {
        getToolBar()
                .animate()
                .translationY(fl)
                .start()
    }

    fun getToolBar(): Toolbar

    fun getToolbarTitle(): TextView? {
        val childCount = getToolBar().childCount
        (0..childCount - 1).forEach { i ->
            val child = getToolBar().getChildAt(i)
            if (child is TextView) {
                return child
            }
        }

        return null
    }

    val duration: Long
        get() = 200L

    fun animateTitleChange(title: CharSequence?) {
        if (getToolBar().height == 0 || isToolBarHide()) {
            getToolBar().title = title
            return
        }

        var toolbarTitle = getToolbarTitle()
        if (toolbarTitle != null) {
            toolbarTitle
                    .run {
                        animate()
                                .alpha(0f)
                                .setDuration(duration)
                                .setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(context))
                                .setListener(object : AnimatorListener(this) {
                                    override fun onAnimationEnd(p0: Animator?) {
                                        alpha = 0f

                                        getToolBar().title = title
                                        if (!TextUtils.isEmpty(title)) {
                                            animate()
                                                    .alpha(1f)
                                                    .setDuration(duration)
                                                    .setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(context))
                                                    .setListener(object : AnimatorListener(this@run) {})
                                                    .start()
                                        }
                                    }

                                    override fun onAnimationCancel(p0: Animator?) {
                                        super.onAnimationCancel(p0)
                                        getToolBar().title = title
                                    }
                                })
                                .start()
                    }
        } else {
            getToolBar().title = title
            toolbarTitle = getToolbarTitle()

            if (toolbarTitle != null) {
                toolbarTitle.run {
                    alpha = 0f

                    animate()
                            .alpha(1f)
                            .setDuration(duration)
                            .setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(context))
                            .setListener(object : AnimatorListener(this) {})
                            .start()
                }
            }
        }
    }

    private fun isToolBarHide() = getToolBar().y < 0

    abstract class AnimatorListener(val view: View) : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {

        }

        override fun onAnimationCancel(p0: Animator?) {
            view.alpha = 1f
        }

        override fun onAnimationStart(p0: Animator?) {

        }
    }

    companion object {
        private const val TOOLBAR_Y_KEY: String = "TOOLBAR_Y_KEY"
    }

    fun onToolBarCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            val y = it.getFloat(TOOLBAR_Y_KEY, -1f)
            if (y == -1f) return

            getToolBar().y = y
        }
    }

    fun onSaveToolbarInstanceState(outState: Bundle) {
        outState.putFloat(TOOLBAR_Y_KEY, getToolBar().y)
    }
}