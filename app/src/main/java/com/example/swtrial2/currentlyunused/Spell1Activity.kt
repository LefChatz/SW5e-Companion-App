package com.example.swtrial2.currentlyunused

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.swtrial2.ForceCastingActivity
import com.example.swtrial2.R
import com.example.swtrial2.spells.*

class Spell1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spell1)
    }
    fun returntomain(view: View){
        startActivity(Intent(this@Spell1Activity, ForceCastingActivity::class.java))
    }
    fun necrotic_charge(view: View){startActivity(Intent(this@Spell1Activity, Necrotic_charge::class.java))}

}