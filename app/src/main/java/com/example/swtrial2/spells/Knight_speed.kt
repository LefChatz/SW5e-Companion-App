package com.example.swtrial2.spells

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.swtrial2.R

class Knight_speed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knight_speed)
    }
    fun returntomain(view: View){
        finish()
    }
}