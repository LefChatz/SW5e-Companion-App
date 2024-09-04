package com.example.sw5ecompanionapp.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sw5ecompanionapp.R

class BackButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.BackButton)
    private val view: View = View.inflate(context, R.layout.back_button, this)

    init {
        attributes.recycle()
    }
}