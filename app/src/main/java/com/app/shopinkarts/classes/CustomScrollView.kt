package com.app.shopinkarts.classes

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView


/*
class CustomScrollView : ScrollView {
    var isEnableScrolling = true

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (isEnableScrolling) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isEnableScrolling) {
            super.onTouchEvent(ev)
        } else {
            false
        }
    }
}*/


class CustomScrollView : ScrollView {
    // Getters & Setters
    var onBottomReachedListener: OnBottomReachedListener? = null

    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val view: View = getChildAt(childCount - 1)
        val diff: Int = view.getBottom() - (height + scrollY) - view.getPaddingBottom()
        if (diff <= 0 && onBottomReachedListener != null) {
            onBottomReachedListener!!.onBottomReached()
        }
        super.onScrollChanged(l, t, oldl, oldt)
    }

    //Event listener.
    interface OnBottomReachedListener {
        fun onBottomReached()
    }
}