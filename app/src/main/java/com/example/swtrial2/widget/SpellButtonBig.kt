package com.example.swtrial2.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swtrial2.R

class SpellButtonBig @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SpellButton)
    private val view: View = View.inflate(context, R.layout.spell_button_big, this)

    init {
        view.findViewById<TextView>(R.id.table_spell_name).text = attributes.getText(R.styleable.SpellButton_text)
        view.findViewById<TextView>(R.id.table_spell_casting_time).text = attributes.getText(R.styleable.SpellButton_value)
    }

}