package com.example.swtrial2.spells.adapterstuff

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.swtrial2.R

class SpellDetailsActivity : AppCompatActivity() {

    lateinit var spell: Spell
    lateinit var details: Array<CharSequence>
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spell = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Spell", Spell::class.java).toSpell()
        } else {
            intent.getParcelableExtra<Spell>("Spell").toSpell()
        }
        setContentView(R.layout.activity_spell_details)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //title and text assignment
        val spTitle:TextView=findViewById(R.id.SpellDetailsTitle)
        spTitle.text=spell.printedname


        val spText:TextView=findViewById(R.id.SpellDetailsText)
        spText.text=spell.detailsText

        //Background
        val spCoord:CoordinatorLayout=findViewById(R.id.SpellDetailsCoord)
        with(spell.side.toString()){
            when{
                this.contains("Dark",true)->{spCoord.background=AppCompatResources.getDrawable(this@SpellDetailsActivity,R.drawable.darkbg1)}
                this.contains("Light",true)->{spCoord.background=AppCompatResources.getDrawable(this@SpellDetailsActivity,R.drawable.lightbg1)}
                this.contains("Universal",true)->{}
                else->{spCoord.background=AppCompatResources.getDrawable(this@SpellDetailsActivity,R.drawable.error404)}
            }

        }

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