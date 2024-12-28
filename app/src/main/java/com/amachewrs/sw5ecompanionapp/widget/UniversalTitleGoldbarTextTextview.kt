package com.amachewrs.sw5ecompanionapp.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.amachewrs.sw5ecompanionapp.R

class UniversalTitleGoldbarTextTextview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.UniversalTitleGoldbarTextTextview)
    private val view: View = View.inflate(context, R.layout.universal_title_goldbar_text_textview, this)
    private var textview: TextView
    init {
        view.findViewById<TextView>(R.id.headertext).text = attributes.getText(R.styleable.UniversalTitleGoldbarTextTextview_title)
        textview=view.findViewById(R.id.contenttext)
        textview.text = attributes.getText(R.styleable.UniversalTitleGoldbarTextTextview_text)
        if (attributes.getBoolean(R.styleable.UniversalTitleGoldbarTextTextview_makeStarjedi,false))textview.typeface=resources.getFont(R.font.starjedi)
        attributes.recycle()
    }
}