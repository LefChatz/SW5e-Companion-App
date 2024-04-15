package com.example.sw5ecompanionapp.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sw5ecompanionapp.R

class EquipmentButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.EquipmentButton)
    private val view: View = View.inflate(context, R.layout.equipment_portal_button, this)

    init {
        view.findViewById<TextView>(R.id.class_button_textview).text = attributes.getText(R.styleable.EquipmentButton_text)
        view.findViewById<ImageView>(R.id.class_button_image).background = attributes.getDrawable(R.styleable.EquipmentButton_background)
        attributes.recycle()
    }
}