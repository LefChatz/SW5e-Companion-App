package com.example.swtrial2.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swtrial2.R

class SpellButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var texty: CharSequence
    var valuey: CharSequence
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SpellButton)
    private val view: View = View.inflate(context, R.layout.spell_button, this)

    init {
        texty=attributes.getText(R.styleable.SpellButton_text)
        valuey=attributes.getText(R.styleable.SpellButton_value)
        view.findViewById<TextView>(R.id.table_spelltext).text = texty
        view.findViewById<TextView>(R.id.table_spelltext2).text = valuey
        attributes.recycle()
    }
    fun setText(newtext: CharSequence){
        texty=newtext
        invalidate()
        requestLayout()
    }
    fun setValue(newvalue: CharSequence){
        valuey=newvalue
        invalidate()
        requestLayout()
    }
}


