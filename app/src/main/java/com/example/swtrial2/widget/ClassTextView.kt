package com.example.swtrial2.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.swtrial2.R

class ClassTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ClassTextView)
    private val view: View = View.inflate(context, R.layout.class_textview, this)

    init {
        view.findViewById<TextView>(R.id.headertext).text = attributes.getText(R.styleable.ClassTextView_title)
        view.findViewById<TextView>(R.id.contenttext).text = attributes.getText(R.styleable.ClassTextView_text)
        attributes.recycle()
    }
}