package com.example.swtrial2.spells

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.swtrial2.R

class Siphon_life : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siphon_life)
    }
    fun returntomain(view: View){
        finish()
    }
}