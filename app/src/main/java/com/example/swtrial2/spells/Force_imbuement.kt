package com.example.swtrial2.spells

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.swtrial2.R

class Force_imbuement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_force_imbuement)
    }
    fun returntomain(view: View){
        finish()
    }
}