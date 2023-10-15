package com.example.swtrial2.spells.adapterstuff

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.swtrial2.R

class SpellDetailsActivity : AppCompatActivity() {
    companion object {
        const val Spell_Name = "Spell_Name"
    }

    lateinit var name: String
    lateinit var details: Array<CharSequence>
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = intent.getStringExtra(Spell_Name).toString()
        setContentView(R.layout.activity_spell_details)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        details=resources.getTextArray(resources.getIdentifier(name,"array",packageName))

        val spTitle:TextView=findViewById(R.id.SpellDetailsTitle)
        spTitle.text=details[0]

        val spCoord:CoordinatorLayout=findViewById(R.id.SpellDetailsCoord)
        when(details[3].toString()){
            "Dark Side"->{spCoord.background=AppCompatResources.getDrawable(this,R.drawable.darkbg1)}
            "Light Side"->{spCoord.background=AppCompatResources.getDrawable(this,R.drawable.lightbg1)}
            "Universal"->{}
            else->{spCoord.background=AppCompatResources.getDrawable(this,R.drawable.error404)}
        }

        val spText:TextView=findViewById(R.id.SpellDetailsText)
        spText.text=details[5]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_spell_details, menu)
        toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain(view: View) {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}